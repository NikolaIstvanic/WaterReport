package com.example.nikola.waterreport.controllers;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.nikola.waterreport.R;
import com.example.nikola.waterreport.model.QualityReport;
import com.example.nikola.waterreport.model.Singleton;
import com.example.nikola.waterreport.model.WaterReport;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.view.View;
import android.widget.TextView;

import java.util.Set;

public class GoogleMapActivity extends FragmentActivity implements OnMapReadyCallback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Called when the map is ready.
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     *
     * @param mMap the given GoogleMap object
     */
    @Override
    public void onMapReady(GoogleMap mMap) {
        Set<WaterReport> reportList = Singleton.waterreports;
        for (WaterReport wr : reportList) {
            LatLng loc = new LatLng(wr.getLat(), wr.getLng());
            mMap.addMarker(new MarkerOptions().position(loc).title(wr.getUserName()).snippet(
                    "Condition: " + wr.getCondition() + "\n " + "Location: " + wr.getLocation()
                            + "\nSource: " + wr.getSource()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        }
        Set<QualityReport> qualitylist = Singleton.qualityreports;
        for (QualityReport qr : qualitylist) {
            LatLng loc = new LatLng(qr.getLat(), qr.getLng());
            mMap.addMarker(new MarkerOptions().position(loc).title(qr.getUserName()).snippet(
                    "Condition: " + qr.getCondition() + "\n " + "Location: " + qr.getLocation()
                            + "\nVirus PPM: " + qr.getVirusPPM()
                            + "\nContaminant PPM: " + qr.getContaminantPPM()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        }
        mMap.setInfoWindowAdapter(new GoogleMapActivity.CustomInfoWindowAdapter());
    }

    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        private final View myContentsView;

        CustomInfoWindowAdapter() {
            myContentsView = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoContents(Marker marker) {
            TextView tvTitle = ((TextView) myContentsView.findViewById(R.id.title));
            tvTitle.setText(marker.getTitle());
            TextView tvSnippet = ((TextView) myContentsView.findViewById(R.id.snippet));
            tvSnippet.setText(marker.getSnippet());
            return myContentsView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
