package com.example.nikola.waterreport.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.nikola.waterreport.R;
import com.example.nikola.waterreport.model.QualityReport;
import com.example.nikola.waterreport.model.Singleton;
import com.example.nikola.waterreport.model.WaterReport;

public class ViewAllActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        TextView tv = (TextView) findViewById(R.id.all_reports);
        if (Singleton.getInstance().waterreports.size() <= 0) {
            tv.setText(getString(R.string.none));
        } else {
            for (WaterReport wr : Singleton.getInstance().waterreports) {
                tv.append(wr.toString() + "\n\n");
            }
        }
        if (Singleton.getInstance().waterreports.size() <= 0) {
            tv.setText(getString(R.string.none));
        } else {
            for (QualityReport qr : Singleton.getInstance().qualityreports) {
                tv.append(qr.toString() + "\n\n");
            }
        }
    }
}
