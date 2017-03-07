package com.example.nikola.waterreport.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.nikola.waterreport.R;

/**
 * @author Nikola Istvanic
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button profile = (Button) findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), ProfileActivity.class);
                i.putExtra(Intent.EXTRA_USER, getIntent().getExtras().getString(Intent.EXTRA_USER));
                startActivity(i);
            }
        });
        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), ReportActivity.class);
                i.putExtra(Intent.EXTRA_USER, getIntent().getExtras().getString(Intent.EXTRA_USER));
                startActivity(i);
            }
        });
        Button view = (Button) findViewById(R.id.viewAll);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), ViewAllActivity.class);
                startActivity(i);
            }
        });
    }
}
