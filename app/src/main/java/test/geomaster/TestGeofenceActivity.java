package test.geomaster;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.testapplication.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import geo.master.GeofenceTrasitionService;

//import com.example.testapplication.Manifest;

public class TestGeofenceActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<Status>,
        LocationListener {
    private static final String TAG = TestGeofenceActivity.class.getSimpleName();
    private static final int REQ_PERMISSION = 999;
    private static final String GEOFENCE_REQ_ID = "faian";
    private static final long GEO_DURATION = 15335153;
 //   private static final int GEOFENCE_REQ_CODE = 6546;
    private final int UPDATE_INTERVAL = 1000;

    private final int FASTEST_INTERVAL = 1000;
    private MapFragment mapFragment;
    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private LocationRequest locationRequest;
    private GoogleMap googleMap;
    private Marker locationMarker;
    private Marker geofenceMarker;
    private LatLng latLng;
    private static final float GEOFENCE_RADIUS = 20.0f; // in meters


    public static final String JSON_STRING = "{\"employee\":{\"lat\":\"28.583909\",\"lng\":77.314126},{\"lat\":\"28.583909\",\"lng\":77.314126}}";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_geofence);
        initGmaps();
        createGoogleApi();

        //  askPermission();
    }

    private void createGoogleApi() {
        Log.d(TAG, "createGoogleApi()");
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();

        }

    }

    private void initGmaps() {
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    protected void onStart() {
        super.onStart();
        googleApiClient.connect();


    }

    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();

    }

    //@Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady()");

        this.googleMap = googleMap;
        addGeofenceMarker();
        //startGeofence();
    }

    private void addGeofenceMarker() {
        try {
            JSONObject ltln = (new JSONObject(JSON_STRING)).getJSONObject("employee");
            Double lat = ltln.getDouble("lat");
            Double lng = ltln.getDouble("lng");
            MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title("lss");
            geofenceMarker = googleMap.addMarker(markerOptions);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void startGeofence() {
        Log.i(TAG, "startGeofence");
        if (geofenceMarker != null) {
            Geofence geofence = createGeofence(geofenceMarker.getPosition(), GEOFENCE_RADIUS);
            GeofencingRequest geofencingRequest = createGeofenceRequest(geofence);
            addGeofence(geofencingRequest);
        }

    }

    private void addGeofence(GeofencingRequest request) {
        Log.d(TAG, "addGeofence");
        if(checkPermission())
            LocationServices.GeofencingApi.addGeofences(googleApiClient,request,createGeofencePendingIntent()).setResultCallback(this);
    }

    private PendingIntent geoFencePendingIntent;
    private final int GEOFENCE_REQ_CODE = 0;

    private PendingIntent createGeofencePendingIntent() {
        Log.d(TAG, "createGeofencePendingIntent");
     if(geoFencePendingIntent != null)
         return geoFencePendingIntent ;

        Intent intent = new Intent(this, GeofenceTrasitionService.class);
        return PendingIntent.getService(this,GEOFENCE_REQ_CODE,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }


    private GeofencingRequest createGeofenceRequest(Geofence geofence) {
        Log.d(TAG, "createGeofenceRequest");

        return new GeofencingRequest.Builder().setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER).addGeofence(geofence).build();

    }

    private Geofence createGeofence(LatLng latLng, float radius) {
        Log.d(TAG, "createGeofence");
        return new Geofence.Builder().setRequestId(GEOFENCE_REQ_ID).setCircularRegion(latLng.latitude, latLng.longitude,radius)
                .setExpirationDuration(GEO_DURATION)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT).build();

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "onCooneccted");
        if (checkPermission()) {

            getLastKnownLocation();
            startGeofence();
        }
        //getLastKnownLocation();
    }

    private boolean checkPermission() {

        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
//        return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);

    }

    private void askPermission() {
        Log.d(TAG, "askPermission()");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_PERMISSION);

    }

    private void getLastKnownLocation() {
        Log.d(TAG, "getLastLocation()");
        if (checkPermission()) {
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (lastLocation != null) {
                Log.i(TAG, "last location." + lastLocation.getLatitude() + lastLocation.getLongitude());
                startLocationUpdates();
            } else Log.w(TAG, "No locatin retrieved yet");
            startLocationUpdates();
            //lastLocation = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setFastestInterval(UPDATE_INTERVAL);

        } else
            askPermission();

    }

    private void startLocationUpdates() {
        Log.i(TAG, "start location changed");
        locationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(UPDATE_INTERVAL).setFastestInterval(FASTEST_INTERVAL);

        if (checkPermission())
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onResult(@NonNull Status status) {
        Log.i(TAG, "onResult: " + status);
        if(status.isSuccess() )
        {
            drawGeofence();

        }

    }
private Circle geoFenceLimits;
    private void drawGeofence() {

        if(geoFenceLimits != null)

            geoFenceLimits.remove();
        CircleOptions circleOptions = new CircleOptions().center(geofenceMarker.getPosition()).strokeColor(Color.argb(50,70,70,70)).fillColor(Color.argb(100,150,150,150)).radius(GEOFENCE_RADIUS);
   geoFenceLimits = googleMap.addCircle(circleOptions);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged [" + location + "]");
        lastLocation = location;
        writeActualLocation(location);
    }

    private void writeActualLocation(Location location) {
        markerLocation(new LatLng(location.getLatitude(), location.getLongitude()));

    }

    private void markerLocation(LatLng latLng) {
        Log.i(TAG, "markerLocation(" + latLng + ")");
        String title = latLng.latitude + ", " + latLng.longitude;
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(title);
        if (googleMap != null) {
            if (locationMarker != null)
                locationMarker.remove();
            locationMarker = googleMap.addMarker(markerOptions);
            float zoom = 14f;
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
            googleMap.animateCamera(cameraUpdate);
        }
    }

}
