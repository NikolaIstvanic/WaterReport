package com.example.nikola.waterreport.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nikola.waterreport.R;
import com.example.nikola.waterreport.model.Singleton;
import com.example.nikola.waterreport.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ProfileActivity extends AppCompatActivity {
    // UI references.
    private EditText mHomeAddress;
    private EditText mEmailAddress;
    private Spinner mTitle;
    private User mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_profile);
        super.onCreate(savedInstanceState);
        Button mSubmitProfile = (Button)findViewById(R.id.reg_Profile_Submit);

        mHomeAddress = (EditText) findViewById(R.id.reg_Home);
        mEmailAddress = (EditText) findViewById(R.id.reg_Email);
        mTitle = (Spinner)findViewById(R.id.reg_Title);

        mSubmitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit_profile();
            }
        });
        // setting up the elements in the Title spinner
        Resources res = getResources();
        List<String> legalTitles = Arrays.asList(res.getStringArray(R.array.title_spinner_names));
        ArrayAdapter<String> adap = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, legalTitles);
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTitle.setAdapter(adap);
        // get the current user
        String user_name = getIntent().getExtras().getString(Intent.EXTRA_USER);
        mCurrentUser = Singleton.getInstance().mappings.get(user_name);
        // if the profile values are already set then display those values
        if (mCurrentUser.getmEmail() != null) {
            mEmailAddress.setText(mCurrentUser.getmEmail());
        }
        if (mCurrentUser.getmTitle() != null) {
            int i  = 0;
            for (String s: legalTitles) {
                if (s.equals(mCurrentUser.getmTitle())) {
                    break;
                } else {
                    i++;
                }
            }
            mTitle.setSelection(i);
        }
        if (mCurrentUser.getmHomeAddress() != null) {
            mHomeAddress.setText(mCurrentUser.getmHomeAddress());
        }
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
        mCurrentUser.setmEmail(mEmailAddress.getText().toString());
        mCurrentUser.setmHomeAddress(mHomeAddress.getText().toString());
        mCurrentUser.setmTitle((String) mTitle.getSelectedItem());
        Context context = getApplicationContext();
        CharSequence text = "Changes Submitted !!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        finish();
        toast.show();
    }

    private boolean isIDValid(String id) {
        return Pattern.matches("[A-Za-z0-9\\._%+-]+@[A-Za-z0-9]+\\.[A-Za-z]{2,4}", id);
    }
}
