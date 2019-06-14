package com.bignerdranch.android.listitup;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = LocationActivity.class.getSimpleName();

    private GoogleMap mMap;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private boolean mLocationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    // Set default values - location, zoom
    private final LatLng mDefaultLocation = new LatLng(55.659750, 12.590958); // ITU
    private static final int DEFAULT_ZOOM = 15;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve the content view that renders the map
        setContentView(R.layout.activity_maps);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Build the map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Prompt the user for permission.
        getLocationPermission();
        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        String currentShop = getIntent().getStringExtra("name of shop");

        if(currentShop.equals("Lidl")) {
            addLidlPositions();
        }
        else if(currentShop.equals("Aldi")) {
            addAldiPositions();
        }
        else if(currentShop.equals("Bilka")) {
            addBilkaPositions();
        }
        else if(currentShop.equals("Rema1000")) {
            addRemaPositions();
        }
        else if(currentShop.equals("Netto")) {
            addNettoPositions();
        }
    }

    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            // Set the camera to the default position
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    //shop locations hardcoded
    private void addLidlPositions() {
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.658368, 12.525257))
                .title("Lidl"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.667870, 12.516661))
                .title("Lidl"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.663466, 12.562769))
                .title("Lidl"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.675280, 12.559186))
                .title("Lidl"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.685373, 12.504587))
                .title("Lidl"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.674914, 12.484150))
                .title("Lidl"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.660475, 12.606732))
                .title("Lidl"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.638968, 12.619059))
                .title("Lidl"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.663077, 12.631121))
                .title("Lidl"));
    }

    private void addAldiPositions() {
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.674607, 12.511609))
                .title("Aldi"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.688073, 12.550588))
                .title("Aldi"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.670740, 12.542175))
                .title("Aldi"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.668416, 12.560031))
                .title("Aldi"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.684487, 12.582535))
                .title("Aldi"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.662879, 12.619600))
                .title("Aldi"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.657361, 12.616329))
                .title("Aldi"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.637788, 12.636545))
                .title("Aldi"));
    }

    private void addBilkaPositions() {
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.655003, 12.547864))
                .title("Bilka"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.638679, 12.580449))
                .title("Bilka"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.619047, 12.353050))
                .title("Bilka"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.602246, 12.325048))
                .title("Bilka"));
    }

    private void addRemaPositions() {
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.642438, 12.576720))
                .title("Rema1000"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.684280, 12.561047))
                .title("Rema1000"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.718364, 12.559766))
                .title("Rema1000"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.629712, 12.419579))
                .title("Rema1000"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.652955, 12.455938))
                .title("Rema1000"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.672317, 12.478601))
                .title("Rema1000"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.680065, 12.459372))
                .title("Rema1000"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.726116, 12.358278))
                .title("Rema1000"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.671907, 12.377647))
                .title("Rema1000"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.648902, 12.397970))
                .title("Rema1000"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.628808, 12.389955))
                .title("Rema1000"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.614739, 12.360599))
                .title("Rema1000"));
    }

    private void addNettoPositions() {
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.658218, 12.589339))
                .title("Netto"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.662102, 12.593154))
                .title("Netto"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.661816, 12.572901))
                .title("Netto"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.685533, 12.586308))
                .title("Netto"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.683308, 12.572396))
                .title("Netto"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.678662, 12.569132))
                .title("Netto"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.675661, 12.562091))
                .title("Netto"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.657751, 12.547158))
                .title("Netto"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.671692, 12.547670))
                .title("Netto"));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(55.665882, 12.537201))
                .title("Netto"));
    }
}


