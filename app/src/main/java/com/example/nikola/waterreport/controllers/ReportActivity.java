package com.example.nikola.waterreport.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nikola.waterreport.R;
import com.example.nikola.waterreport.model.Singleton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        ((TextView) findViewById(R.id.text_time)).setText("Date: " + sdf.format(new Date()));
        ((TextView) findViewById(R.id.user_name)).setText("Username: " + getIntent().getExtras().getString(Intent.EXTRA_USER));
        ((TextView) findViewById(R.id.report_number)).setText("Report ID: " + ++Singleton.id_num);
        Button submitReport = (Button) findViewById(R.id.submit_report);
        submitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
