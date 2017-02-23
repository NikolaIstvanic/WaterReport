package com.example.nikola.waterreport.controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.nikola.waterreport.R;
import com.example.nikola.waterreport.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Nikola Istvnaic
 */
public class MainActivity extends Activity {
    // UI references.
    private EditText mHomeAddress;
    private EditText mEmailAddress;
    private Spinner mTitle;
    private Button mSubmitProfile;
    private User mCurrentUser;

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
        List<String> legalTitles = Arrays.asList(res.getStringArray(R.array.title_spinner_names));
        ArrayAdapter<String> adap = new ArrayAdapter(this,android.R.layout.simple_spinner_item, legalTitles);
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTitle.setAdapter(adap);
        // get the current user
        String user_name = getIntent().getStringExtra(Intent.EXTRA_USER);
        mCurrentUser = LoginManager.mappings.get(user_name);
        // if the profile values are already set then display those values
        if( mCurrentUser.getEmail() != null){
            mEmailAddress.setText(mCurrentUser.getEmail());
        }

        if( mCurrentUser.getTitle() != null){
            int i  = 0;
            for (String s: legalTitles)
               if(s.equals(mCurrentUser.getTitle()))
                   break;
               else
                   i++;
            mTitle.setSelection(i);
        }
        if( mCurrentUser.getHomeAddress() != null){
            mHomeAddress.setText(mCurrentUser.getHomeAddress());
        }
    }

    private void logout() {
        Intent i = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(i);
    }

    private void submit_profile() {
        // get data from the UI elements and add it to the User Model
        //validating user input
        // find the user using Login manager
        // Check for a valid ID.
        if (TextUtils.isEmpty(mEmailAddress.getText().toString())) {
            mEmailAddress.setError(getString(R.string.error_field_required));
            return;
        } else if (!isIDValid(mEmailAddress.getText().toString())) {
            mEmailAddress.setError(getString(R.string.error_invalid_email));
            return;
        }
        // errror checks made. need to change the model
        mCurrentUser.setEmail(mEmailAddress.getText().toString());
        mCurrentUser.setHomeAddress(mHomeAddress.getText().toString());
        mCurrentUser.setTitle((String) mTitle.getSelectedItem());

    }
    private boolean isIDValid(String id) {
        return Pattern.matches("[A-Za-z0-9\\._%+-]+@[A-Za-z0-9]+\\.[A-Za-z]{2,4}", id);
    }

}