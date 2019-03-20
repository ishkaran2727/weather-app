package com.example.clima20;

import android.location.*;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.content.Intent;
import android.widget.Toast;
import java.util.*;

public class Map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        intent1 = getIntent();
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
        String cityname = intent1.getStringExtra(MainActivity.CITY_NAME1);
        Double temp_lon=0.0000;
        Double temp_lat=0.0000;

        if(Geocoder.isPresent()){
            try
            {
                String location = cityname;
                Geocoder gc = new Geocoder(this);
                List<Address> addresses= gc.getFromLocationName(location, 1); // get the found Address Objects
                List<LatLng> ll = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
                for(Address a : addresses){
                    if(a.hasLatitude() && a.hasLongitude()){
                        ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
                        temp_lon = a.getLongitude();
                        temp_lat = a.getLatitude();
                    }
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Map not available.", Toast.LENGTH_LONG).show();
            }
        }
        Toast.makeText(getApplicationContext(), "Lat: "+temp_lat+" Lon:"+temp_lon, Toast.LENGTH_LONG).show();
        LatLng city = new LatLng(temp_lat, temp_lon);
        mMap.addMarker(new MarkerOptions().position(city).title(cityname));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(city));
        mMap.setMinZoomPreference(15.0f);
    }
}
