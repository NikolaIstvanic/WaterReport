package com.example.nikola.waterreport.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.nikola.waterreport.R;
import com.example.nikola.waterreport.model.Singleton;
import com.google.firebase.FirebaseApp;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        FirebaseApp.initializeApp(this);
        Singleton.onDataChanged();
        Button log = (Button) findViewById(R.id.button_login);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        Button reg = (Button) findViewById(R.id.button_register);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), RegistrationActivity.class);
                startActivity(i);
            }
        });
    }
}
