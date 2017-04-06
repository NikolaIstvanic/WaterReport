package com.example.nikola.waterreport.model;

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

    public User(String username, String password, String email) {
        this(username, password);
        mEmail = email;
    }

    public boolean authenticate(String password) {
        return password != null && password.equals(mPassword);
    }

    public void setmId(int id) {
        mId = id;
    }

    public int getmId() {
        return mId;
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
}
