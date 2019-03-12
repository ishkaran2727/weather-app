package com.example.clima20;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent1 = getIntent();
        //String cityname_1 = intent.getStringExtra(MainActivity.CITY_NAME);

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
        String cityname_1 = intent1.getStringExtra(Activity2.CITY_NAME1);
        String lat = intent1.getStringExtra(Activity2.LAT);
        String lon = intent1.getStringExtra(Activity2.LON);


        Double temp_lon= Double.parseDouble(lon);
        Double temp_lat= Double.parseDouble(lat);

        // Add a marker in Sydney and move the camera
        LatLng CITY = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(CITY).title(cityname_1);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(CITY));
    }
}
