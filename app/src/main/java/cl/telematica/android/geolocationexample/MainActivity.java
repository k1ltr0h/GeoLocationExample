package cl.telematica.android.geolocationexample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import cl.telematica.android.geolocationexample.presenters.LocationPresenterImpl;
import cl.telematica.android.geolocationexample.presenters.contract.LocationPresenter;

public class MainActivity extends AppCompatActivity implements MainView {

    private TextView mLatitude;
    private TextView mLongitude;

    private LocationPresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLatitude = (TextView) findViewById(R.id.latitude);
        mLongitude = (TextView) findViewById(R.id.longitude);

        mPresenter = new LocationPresenterImpl(this, this);
        mPresenter.startUpdates();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.stopUpdates();
    }

    @Override
    public void onLatitudObtenida(double latitud) {
        mLatitude.setText("Latitud: " + latitud);
    }

    @Override
    public void onLongitudObtenida(double longitud) {
        mLongitude.setText("Longitud: " + longitud);
    }
}
