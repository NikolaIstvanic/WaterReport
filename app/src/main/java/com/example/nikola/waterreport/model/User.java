package com.example.nikola.waterreport.model;

/**
 * @author Nikola Istvnaic
 */
public class User {
    private String mUserName;
    private String mPassword;
    private String mId;

    public User() {}

    public User(String username, String password) {
        mUserName = username;
        mPassword = password;
    }

    public User(String username, String password, String ID) {
        this(username, password);
        mId = ID;
    }

    public boolean authenticate(String password) {
        return password != null && password.equals(mPassword);
    }
}
