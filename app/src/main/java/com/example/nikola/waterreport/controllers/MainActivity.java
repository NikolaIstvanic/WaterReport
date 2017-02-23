package com.example.nikola.waterreport.controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.nikola.waterreport.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Nikola Istvnaic
 */
public class MainActivity extends Activity {
    // UI references.
    private EditText mHomeAddress;
    private EditText mEmailAddress;
    private Spinner mTitle;
    private Button mSubmitProfile;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_profile);
        super.onCreate(savedInstanceState);
        mSubmitProfile = (Button)findViewById(R.id.reg_Profile_Submit);

        mHomeAddress = (EditText) findViewById(R.id.reg_Home);
        mEmailAddress = (EditText) findViewById(R.id.reg_Email);
        mTitle = (Spinner)findViewById(R.id.reg_Title);

        Button logoutButton = (Button)findViewById(R.id.reg_Logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        mSubmitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit_profile();
            }
        });

        // setting up the elements in the Title spinner
        Resources res = getResources();
        List<String> legalClassStanding = Arrays.asList(res.getStringArray(R.array.title_spinner_names));
        ArrayAdapter<String> adap = new ArrayAdapter(this,android.R.layout.simple_spinner_item, legalClassStanding);
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTitle.setAdapter(adap);

    }

    private void logout() {
        Intent i = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(i);
    }

    private void submit_profile() {
        // get data from the UI elements and add it to the User Model
    }

}
