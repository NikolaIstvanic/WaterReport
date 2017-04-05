package com.example.nikola.waterreport.controllers;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.nikola.waterreport.R;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class HistoricalViewActivity extends AppCompatActivity {
    private Spinner PPM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_view);
        PPM = (Spinner) findViewById(R.id.spinner2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button submit_request = (Button) findViewById(R.id.choose_report);
        submit_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HistoricalViewActivity.this, GraphViewActivity.class);
                startActivityForResult(i, 0);
            }
        });

        Resources res = getResources();
        List<String> PPM_choices = Arrays.asList(res.getStringArray(
                R.array.PPM_Choices_spinner_values));
        ArrayAdapter<String> adap = new ArrayAdapter<>
                (this,android.R.layout.simple_spinner_item, PPM_choices);
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PPM.setAdapter(adap);

    }

}
