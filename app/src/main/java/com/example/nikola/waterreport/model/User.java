package com.example.nikola.waterreport.model;

import android.util.Log;

/**
 * @author Nikola Istvnaic
 */
public class User {
    private String mUserName;
    private String mPassword;
    private String mEmail;
    private int mId = 0;
    private String mHomeAddress;
    private String mTitle;

    public User() {}

    public User(String username, String password) {
        mUserName = username;
        mPassword = password;
    }

    public User(String username, String password, String email, int id) {
        this(username, password, email);
        mId = id;
    }

    public String getPassword()
    {
        return mPassword;
    }

    public User(String username, String password, String email) {
        this(username, password);
        mEmail = email;
    }

    public boolean authenticate(String password) {
        Log.d("TEST", password + " " + mPassword);
        return password != null && password.equals(mPassword);
    }

    public void setmId(int id) {
        mId = id;
    }

    public int getmId() {
        return mId;
    }

    public void setEmail(String email) {
        if (email != null) {
            mEmail = email;
        }
    }

    public void setHomeAddress(String address) {
        if (address != null) {
            mHomeAddress = address;
        }
    }

    public void setTitle(String title) {
        if (title != null) {
            mTitle = title;
        }
    }

    public String getTitle() {
        return mTitle;
    }

    public String getHomeAddress() {
        return mHomeAddress;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmUserName() {
        return mUserName;
    }
}
