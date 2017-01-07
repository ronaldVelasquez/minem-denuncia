package denuncias.minem.gob.pe.denuncias;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MapActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private Location location;
    private GoogleMap googleMap;
    private LatLng myLatlng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMyLocationEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (googleApiClient == null || !googleApiClient.isConnected()) {
            buildGoogleApiClient();
            googleApiClient.connect();
        }
        if (googleMap == null) {
            SupportMapFragment mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_map, mapFragment).commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (googleApiClient != null) {
            LocationServices
                    .FusedLocationApi
                    .removeLocationUpdates(googleApiClient, this);
        }
    }

    private synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onLocationChanged(Location location) {
        myLatlng = new LatLng(location.getLatitude(),
                location.getLongitude());
        googleApiClient.disconnect();
        googleApiClient = null;
        googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(myLatlng, 17.0f));
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void savePosition(View view) {
        CameraPosition cameraPosition = googleMap.getCameraPosition();
        myLatlng = cameraPosition.target;
        Intent data = new Intent();
        data.putExtra("latitud", myLatlng.latitude);
        data.putExtra("longitud", myLatlng.longitude);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
