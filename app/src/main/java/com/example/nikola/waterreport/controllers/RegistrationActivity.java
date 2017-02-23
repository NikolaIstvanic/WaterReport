package com.example.nikola.waterreport.controllers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.nikola.waterreport.R;
import com.example.nikola.waterreport.model.Admin;
import com.example.nikola.waterreport.model.Manager;
import com.example.nikola.waterreport.model.User;
import com.example.nikola.waterreport.model.Worker;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A login screen that offers login via username/password.
 */
public class RegistrationActivity extends AppCompatActivity {
    /**
     * Keep track of the registration task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;
    // UI references.
    private EditText mUserView;
    private EditText mIDView;
    private EditText mPasswordView;
    private EditText mPasswordView2;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        // Set up the login form.
        mUserView = (EditText) findViewById(R.id.reg_userame);
        mIDView = (EditText) findViewById(R.id.reg_id);
        mPasswordView = (EditText) findViewById(R.id.reg_password);
        mPasswordView2 = (EditText) findViewById(R.id.reg_password2);
        mPasswordView2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });
        Button mUsernameRegisterButton = (Button) findViewById(R.id.reg_button);
        mUsernameRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
        mProgressView = findViewById(R.id.reg_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister() {
        if (mAuthTask != null) {
            return;
        }
        // Reset errors.
        mUserView.setError(null);
        mPasswordView.setError(null);
        // Store values at the time of the registration attempt.
        String username = mUserView.getText().toString();
        String id = mIDView.getText().toString();
        String password1 = mPasswordView.getText().toString();
        String password2 = mPasswordView2.getText().toString();
        boolean cancel = false;
        View focusView = null;
        // Check for a valid password, if the user entered one.
        if (!isPasswordValid(password1, password2)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        } else if (!password1.equals(password2)) {
            mPasswordView.setError(getString(R.string.error_not_same));
            focusView = mPasswordView2;
            cancel = true;
        }
        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            mUserView.setError(getString(R.string.error_field_required));
            focusView = mUserView;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            mUserView.setError(getString(R.string.error_user));
            focusView = mUserView;
            cancel = true;
        }
        // Check for a valid ID.
        if (TextUtils.isEmpty(id)) {
            mUserView.setError(getString(R.string.error_field_required));
            focusView = mIDView;
            cancel = true;
        } else if (!isIDValid(id)) {
            mIDView.setError(getString(R.string.error_invalid_email));
            focusView = mIDView;
            cancel = true;
        }
        if (LoginManager.mappings.keySet().contains(username)) {
            mUserView.setError(getString(R.string.error_exists));
            focusView = mUserView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            /* determine what kind of User the user selected */
            mAuthTask = new UserLoginTask(username, password1, id,
                    ((RadioButton) findViewById(R.id.radio_user)).isChecked() ? "User" :
                    ((RadioButton) findViewById(R.id.radio_manager)).isChecked() ? "Manager" :
                    ((RadioButton) findViewById(R.id.radio_worker)).isChecked() ? "Worker" : "Admin");

            mAuthTask.execute((Void) null);
        }
    }

    private boolean isUsernameValid(String username) {
        return username != null && username.length() > 0;
    }

    private boolean isIDValid(String id) {
        return Pattern.matches("[A-Za-z0-9\\._%+-]+@[A-Za-z0-9]+\\.[A-Za-z]{2,4}", id);
    }

    private boolean isPasswordValid(String password1, String password2) {
        return password1.length() > 0 && password2.length() > 0;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * Represents an asynchronous registration task used to authenticate the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
        private final String mUser;
        private final String mID;
        private final String mPass;
        private final String mType;

        UserLoginTask(String user, String pass, String id, String type) {
            mUser = user;
            mID = id;
            mPass = pass;
            mType = type;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            /* add user to "database" (if you're at this point, then everything is valid to be added */
            LoginManager.mappings.put(mUser, mType.equals("User") ? new User(mUser, mPass, mID)
                : mType.equals("Worker") ? new Worker(mUser, mPass, mID) : mType.equals("Manager")
                ? new Manager(mUser, mPass, mID) : new Admin(mUser, mPass, mID));
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            if (success) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
                mUserView.setText("");
                mPasswordView.setText("");
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
