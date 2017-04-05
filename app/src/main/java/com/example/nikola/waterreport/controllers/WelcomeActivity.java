package com.example.nikola.waterreport.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.nikola.waterreport.R;
import com.example.nikola.waterreport.model.Singleton;
import com.example.nikola.waterreport.model.User;
import com.example.nikola.waterreport.model.WaterReport;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String filename = "users.dat";
        FileInputStream inputStream;

        try {
            inputStream = openFileInput(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Singleton.mappings = (Map<String, User>) objectInputStream.readObject();
            objectInputStream.close();
            inputStream.close();
        } catch (Exception e) {
            System.err.println(e.toString());
            System.err.println("Unable to load user data.");
        }
        filename = "reports.dat";
        try {
            inputStream = openFileInput(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Singleton.pseudoDB = (Set<WaterReport>) objectInputStream.readObject();
            objectInputStream.close();
            inputStream.close();
        } catch (Exception e) {
            System.err.println(e.toString());
            System.err.println("Unable to load reports.");
        }
        filename = "idNum.dat";
        try {
            inputStream = openFileInput(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Singleton.id_num = (Integer) objectInputStream.readObject();
            objectInputStream.close();
            inputStream.close();
        } catch (Exception e) {
            System.err.println(e.toString());
            System.err.println("Unable to load current idNum.");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
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
