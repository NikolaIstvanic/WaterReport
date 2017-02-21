package com.example.nikola.waterreport.model;

/**
 * @author Nikola Istvnaic
 */

public class User {

    private String mUserName;
    private String mPassword;
    private String mId;
    public User(){

    }
    public User(String username, String password) {

        mUserName = username;
        mPassword = password;
    }

    public boolean authenticate(String password) {
        return password == null ? false : password.equals(mPassword);
    }
}

