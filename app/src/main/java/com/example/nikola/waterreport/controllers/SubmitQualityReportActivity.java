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
import com.example.nikola.waterreport.model.Manager;
import com.example.nikola.waterreport.model.QualityReport;
import com.example.nikola.waterreport.model.Singleton;
import com.example.nikola.waterreport.model.User;
import com.example.nikola.waterreport.model.WaterReport;
import com.example.nikola.waterreport.model.Worker;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SubmitQualityReportActivity extends AppCompatActivity {
    private Spinner condition;
    private EditText mLocation;
    private EditText mVirusPPM;
    private EditText mContaminantPPM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        mLocation = (EditText) findViewById(R.id.location);
        mVirusPPM = (EditText) findViewById(R.id.virus_ppm);
        mContaminantPPM = (EditText) findViewById(R.id.contaminant_ppm);
        condition = (Spinner) findViewById(R.id.condition_spinner);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.US);
        ((TextView) findViewById(R.id.text_time)).setText(getString(R.string.date, sdf.format(new Date())));
        ((TextView) findViewById(R.id.user_name)).setText(getString(R.string.getUser, getIntent().getExtras().getString(Intent.EXTRA_USER)));
        ((TextView) findViewById(R.id.report_number)).setText(getString(R.string.ReportID, Singleton.id_num + 1));
        final Button submitReport = (Button) findViewById(R.id.submit_report);
        submitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    submitReport();
                } catch (IOException e) {
                    Log.d("ERROR", e.getMessage());
                }
            }
        });
        /* setup spinner values */
        Resources res = getResources();
        List<String> legalTypes = Arrays.asList(res.getStringArray(
                R.array.quality_condition));
        ArrayAdapter<String> a = new ArrayAdapter<>
                (this,android.R.layout.simple_spinner_item, legalTypes);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        condition.setAdapter(a);
    }

    private void submitReport() throws IOException {
        // Reset errors.
        mLocation.setError(null);
        mVirusPPM.setError(null);
        mContaminantPPM.setError(null);
        // Store values at the time of the login attempt.
        String location = mLocation.getText().toString();
        boolean cancel = false;
        boolean virusCancel = false;
        boolean contaminantCancel = false;
        // Check for a valid location, if the user entered one.
        if (TextUtils.isEmpty(location)) {
            mLocation.setError(getString(R.string.error_empty_location));
            cancel = true;
        }
        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(location, 1);
        Address add = list.get(0);
        double lat = add.getLatitude();
        double lng = add.getLongitude();
        double v = 0;
        double c = 0;
        try {
            v = Double.parseDouble(mVirusPPM.getText().toString());
        } catch (NumberFormatException nfe) {
            mVirusPPM.setError(getString(R.string.error_virus));
            virusCancel = true;
        }
        try {
            c = Double.parseDouble(mContaminantPPM.getText().toString());
        } catch (NumberFormatException nfe) {
            mContaminantPPM.setError(getString(R.string.error_contaminant));
            contaminantCancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt submit and focus the location field
            mLocation.requestFocus();
        } else if (virusCancel) {
            mVirusPPM.requestFocus();
        } else if (contaminantCancel) {
            mContaminantPPM.requestFocus();
        } else {
            // log quality report
            Singleton.qualityreports.add(new QualityReport(
                    String.valueOf(((TextView) findViewById(R.id.user_name)).getText()),
                    String.valueOf(((TextView) findViewById(R.id.text_time)).getText()),
                    ++Singleton.id_num, String.valueOf(mLocation.getText()),
                    condition.getSelectedItem().toString(), v, c, lat, lng));
            Context context = getApplicationContext();
            CharSequence text = "Report Submitted !!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            finish();
            toast.show();
        }
    }
}
