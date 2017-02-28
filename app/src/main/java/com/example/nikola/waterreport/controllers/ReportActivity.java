package com.example.nikola.waterreport.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.nikola.waterreport.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDateandTime = sdf.format(new Date());
        // 20170227_162758
    }
}
