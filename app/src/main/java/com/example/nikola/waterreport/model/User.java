package com.example.nikola.waterreport.model;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Nikola Istvanic
 */
public class User {
    private String mUserName;
    private String mPassword;
    private String mEmail;
    private String mHomeAddress;
    private String mTitle;
    private String mPosition;

    public User() {}

    public User(String username, String password, String position) {
        mUserName = username;
        mPassword = password;
        mPosition = position;
    }

    public User(String username, String password, String email, String position) {
        this(username, password, position);
        mEmail = email;
    }

    public User(String username, String password, String email, String mHomeAddress, String mTitle, String position) {
        this(username, password, email, position);
        this.mHomeAddress = mHomeAddress;
        this.mTitle = mTitle;
    }

    public boolean authenticate(String password) {
        return password != null && password.equals(mPassword);
    }

    public void setmEmail(String email) {
        if (email != null) {
            mEmail = email;
        }
    }

    public void setmHomeAddress(String address) {
        if (address != null) {
            mHomeAddress = address;
        }
    }

    public void setmTitle(String title) {
        if (title != null) {
            mTitle = title;
        }
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmPosition() {
        return mPosition;
    }

    public void setmPosition(String mPosition) {
        this.mPosition = mPosition;
    }

    public void setmPassword(String password) {
        mPassword = password;
    }

    public String getmPassword() {
        return mPassword;
    }

    public String getmHomeAddress() {
        return mHomeAddress;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmUserName() {
        return mUserName;
    }

    public Map<String, Map<String, String>> getMap() {
        Map<String, Map<String, String>> u = new HashMap<>();
        Map<String, String> d = new HashMap<>();
        d.put("username", mUserName);
        d.put("password", mPassword);
        d.put("email", mEmail);
        d.put("homeaddress", mHomeAddress);
        d.put("title", mTitle);
        d.put("position", mPosition);
        u.put(mUserName, d);
        return u;
    }
}
