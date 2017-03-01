package com.example.nikola.waterreport.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikola.waterreport.R;
import com.example.nikola.waterreport.model.Singleton;
import com.example.nikola.waterreport.model.WaterReport;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ReportActivity extends AppCompatActivity {
    private Spinner source;
    private Spinner condition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        source = (Spinner) findViewById(R.id.spinner);
        condition = (Spinner) findViewById(R.id.condition_spinner);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        ((TextView) findViewById(R.id.text_time)).setText("Date: " + sdf.format(new Date()));
        ((TextView) findViewById(R.id.user_name)).setText("Username: " + getIntent().getExtras().getString(Intent.EXTRA_USER));
        ((TextView) findViewById(R.id.report_number)).setText("Report ID: " + ++Singleton.id_num);
        final Button submitReport = (Button) findViewById(R.id.submit_report);
        submitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitReport();
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

    private void submitReport() {
        Singleton.pseudoDB.add(new WaterReport(String.valueOf(((TextView) findViewById(R.id.user_name)).getText()),
                String.valueOf(((TextView) findViewById(R.id.text_time)).getText()),
                Integer.parseInt(String.valueOf(((TextView) findViewById(R.id.report_number)).getText()).split(": ")[1]),
                String.valueOf(((TextView) findViewById(R.id.location)).getText()),
                (String) source.getSelectedItem(), (String) condition.getSelectedItem()));
        Context context = getApplicationContext();
        CharSequence text = "Report Submitted !!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        finish();
        toast.show();
    }
}
