package com.example.nikola.waterreport.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nikola.waterreport.R;
import com.example.nikola.waterreport.model.Singleton;
import com.example.nikola.waterreport.model.WaterReport;

public class ViewAllActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        TextView tv = (TextView) findViewById(R.id.all_reports);
        Button view_histotry = (Button) findViewById(R.id.ViewHistory);
        view_histotry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewAllActivity.this, HistoricalViewActivity.class);
                //i.putExtra(Intent.EXTRA_USER, getIntent().getExtras().getString(Intent.EXTRA_USER));
                startActivityForResult(i, 0);
            }
        });
        if (Singleton.pseudoDB.size() <= 0) {
            tv.setText("No Water Reports Created...");
        } else {
            for (WaterReport wr : Singleton.pseudoDB) {
                tv.append(wr.toString() + "\n\n");
            }
        }
    }
}
