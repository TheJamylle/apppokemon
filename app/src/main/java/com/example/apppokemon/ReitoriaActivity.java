package com.example.apppokemon;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ReitoriaActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final CameraPosition REITORIA_CAMERA = new CameraPosition.Builder()
            .target(new LatLng(-10.1952319, -48.3327248)).zoom(10.0f).bearing(0).tilt(0).build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reitoria);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng reitoria = new LatLng(-10.1952319, -48.3327248);
        mMap.addMarker(new MarkerOptions().position(reitoria).title("Reitoria do IFTO"));
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(REITORIA_CAMERA));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(reitoria));
    }
}