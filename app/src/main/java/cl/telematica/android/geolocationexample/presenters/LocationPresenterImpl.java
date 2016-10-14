package cl.telematica.android.geolocationexample.presenters;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import cl.telematica.android.geolocationexample.MainView;
import cl.telematica.android.geolocationexample.model.Persona;
import cl.telematica.android.geolocationexample.presenters.contract.LocationPresenter;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by franciscocabezas on 10/14/16.
 */

public class LocationPresenterImpl implements LocationPresenter, LocationListener {

    private LocationManager locationManager;

    private static final long MIN_TIME = 10000;
    private static final float MIN_DISTANCE = 2;

    private Activity activity;
    private MainView mainView;

    public LocationPresenterImpl(Activity activity, MainView mainView) {
        locationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);
        this.activity = activity;
        this.mainView = mainView;
    }

    @Override
    public void startUpdates() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        String bestProvider = locationManager
                .getBestProvider(criteria, true);

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            //Entro acá cuando el usuario no acepta los permisos, por ende, yo debería mostrar algún diálogo, etc
            return;
        }

        locationManager.requestLocationUpdates(bestProvider,
                MIN_TIME, MIN_DISTANCE, this);
    }

    @Override
    public void stopUpdates() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        mainView.onLatitudObtenida(location.getLatitude());
        mainView.onLongitudObtenida(location.getLongitude());

        Persona persona = new Persona();
        persona.setLatitud(location.getLatitude());
        persona.setLongitud(location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
