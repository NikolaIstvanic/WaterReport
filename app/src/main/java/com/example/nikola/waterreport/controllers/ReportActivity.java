package com.example.nikola.waterreport.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikola.waterreport.R;
import com.example.nikola.waterreport.model.Singleton;
import com.example.nikola.waterreport.model.WaterReport;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ReportActivity extends AppCompatActivity {
    private Spinner source;
    private Spinner condition;
    private EditText mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        mLocation = (EditText) findViewById(R.id.location);
        source = (Spinner) findViewById(R.id.spinner);
        condition = (Spinner) findViewById(R.id.condition_spinner);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        ((TextView) findViewById(R.id.text_time)).setText("Date: " + sdf.format(new Date()));
        ((TextView) findViewById(R.id.user_name)).setText("Username: " + getIntent().getExtras().getString(Intent.EXTRA_USER));
        ((TextView) findViewById(R.id.report_number)).setText("Report ID: " + (Singleton.id_num + 1));
        final Button submitReport = (Button) findViewById(R.id.submit_report);
        submitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    submitReport();
                } catch (IOException e) {

                }
            }
        });
        /* setup spinner values */
        Resources res = getResources();
        List<String> legalLocations = Arrays.asList(res.getStringArray(R.array.type_spinner_values));
        ArrayAdapter<String> adap = new ArrayAdapter(this,android.R.layout.simple_spinner_item, legalLocations);
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        source.setAdapter(adap);

        List<String> legalTypes = Arrays.asList(res.getStringArray(R.array.condition_spinner_values));
        ArrayAdapter<String> a = new ArrayAdapter(this,android.R.layout.simple_spinner_item, legalTypes);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        condition.setAdapter(a);
    }

    private void submitReport() throws IOException {
        // Reset errors.
        mLocation.setError(null);
        // Store values at the time of the login attempt.
        String location = mLocation.getText().toString();
        boolean cancel = false;
        // Check for a valid location, if the user entered one.
        if (TextUtils.isEmpty(location)) {
            mLocation.setError(getString(R.string.error_empty_location));
            cancel = true;
        }
        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(location, 1);
        Address add = list.get(0);
        String locality = add.getLocality();
        double lat = add.getLatitude();
        double lng = add.getLongitude();
        Log.d("TEST", locality);
        if (cancel) {
            // There was an error; don't attempt submit and focus the location field
            mLocation.requestFocus();
        } else {
            // log water report
            Singleton.pseudoDB.add(new WaterReport(String.valueOf(((TextView) findViewById(R.id.user_name)).getText()),
                    String.valueOf(((TextView) findViewById(R.id.text_time)).getText()),
                    ++Singleton.id_num, String.valueOf(mLocation.getText()), (String) source.getSelectedItem(), (String) condition.getSelectedItem(), lat, lng));
            Context context = getApplicationContext();
            CharSequence text = "Report Submitted !!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            finish();
            toast.show();
        }
    }
}
