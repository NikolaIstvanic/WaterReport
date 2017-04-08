package com.example.nikola.waterreport.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.nikola.waterreport.R;

public class WorkerSelectReportActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_select_report);
        Button quality = (Button) findViewById(R.id.submit_quality);
        quality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WorkerSelectReportActivity.this, SubmitQualityReportActivity.class);
                i.putExtra(Intent.EXTRA_USER, getIntent().getExtras().getString(Intent.EXTRA_USER));
                startActivityForResult(i, 0);
                finish();
            }
        });
        Button regular = (Button) findViewById(R.id.worker_submit_report);
        regular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WorkerSelectReportActivity.this, ReportActivity.class);
                i.putExtra(Intent.EXTRA_USER, getIntent().getExtras().getString(Intent.EXTRA_USER));
                startActivityForResult(i, 0);
                finish();
            }
        });
    }
}
