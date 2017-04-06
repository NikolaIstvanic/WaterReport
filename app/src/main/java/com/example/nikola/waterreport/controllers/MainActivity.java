package com.example.nikola.waterreport.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nikola.waterreport.R;
import com.example.nikola.waterreport.model.Manager;
import com.example.nikola.waterreport.model.QualityReport;
import com.example.nikola.waterreport.model.Singleton;
import com.example.nikola.waterreport.model.User;
import com.example.nikola.waterreport.model.WaterReport;
import com.example.nikola.waterreport.model.Worker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Set;

/**
 * @author Nikola Istvanic
 */
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(getBaseContext(), WelcomeActivity.class);
                startActivity(i);
            }
        });
        Button profile = (Button) findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                i.putExtra(Intent.EXTRA_USER, getIntent().getExtras().getString(Intent.EXTRA_USER));
                startActivityForResult(i, 0);
            }
        });
        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_name = getIntent().getExtras().getString(Intent.EXTRA_USER);
                // search for type of user
                if (Singleton.mappings.get(user_name) instanceof Worker) {
                    Intent i = new Intent(MainActivity.this, WorkerSelectReportActivity.class);
                    i.putExtra(Intent.EXTRA_USER, getIntent().getExtras().getString(Intent.EXTRA_USER));
                    startActivityForResult(i, 0);
                } else {
                    Intent i = new Intent(MainActivity.this, ReportActivity.class);
                    i.putExtra(Intent.EXTRA_USER, getIntent().getExtras().getString(Intent.EXTRA_USER));
                    startActivityForResult(i, 0);
                }
            }
        });
        Button view = (Button) findViewById(R.id.viewAll);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ViewAllActivity.class);
                User currentUser = Singleton.mappings.get(getIntent().getExtras().getString(Intent.EXTRA_USER));
                if( currentUser instanceof Manager) {
                    startActivityForResult(i, 0);
                }
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        displayToMap();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        displayToMap();
    }

    /**
     * Method which takes all WaterReports in the pseudo database and adds them to the GoogleMap.
     * Called after the end of each Activity which is not Main (acts like a refresher).
     */
    public void displayToMap() {
        Set<WaterReport> reportList = Singleton.waterreports;
        for (WaterReport wr : reportList) {
            if (wr != null) {
                LatLng loc = new LatLng(wr.getmLat(), wr.getmLng());
                mMap.addMarker(new MarkerOptions().position(loc).title(wr.getmUserName()).snippet(
                        "Condition: " + wr.getmCondition() + "\n " + "Location: " + wr.getmLocation()
                                + "\nSource: " + wr.getmSource()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
            }
        }
        Set<QualityReport> qualitylist = Singleton.qualityreports;
        for (QualityReport qr : qualitylist) {
            if (qr != null) {
                LatLng loc = new LatLng(qr.getmLat(), qr.getmLng());
                mMap.addMarker(new MarkerOptions().position(loc).title(qr.getmUserName()).snippet(
                        "Condition: " + qr.getmCondition() + "\n " + "Location: " + qr.getmLocation()
                                + "\nVirus PPM: " + qr.getmVirusPPM()
                                + "\nContaminant PPM: " + qr.getmContaminantPPM()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
            }
        }
        mMap.setInfoWindowAdapter(new MainActivity.CustomInfoWindowAdapter());
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
