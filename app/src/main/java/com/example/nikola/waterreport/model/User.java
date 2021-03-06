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
    private int mFailedLoginAttempts;

    /**
     * No arg constructor (for Firebase)
     */
    public User() {}

    /**
     * Username, password, position (User, Admin, ...) constructor
     * @param username username of this User
     * @param password password for this User
     * @param position position for this User
     */
    public User(String username, String password, String position) {
        mUserName = username;
        mPassword = password;
        mPosition = position;
    }

    /**
     * Username, password, position (User, Admin, ...), email constructor
     * @param username username of this User
     * @param password password for this User
     * @param email email for this User
     * @param position position for this User
     */
    public User(String username, String password, String email, String position) {
        this(username, password, position);
        mEmail = email;
    }

    /**
     * Username, password, position (User, Admin, ...), email, home address constructor
     * @param username username of this User
     * @param password password for this User
     * @param email email for this User
     * @param mHomeAddress home address for this User
     * @param position position for this User
     */
    public User(String username, String password, String email, String mHomeAddress, String mTitle,
                String position) {
        this(username, password, email, position);
        this.mHomeAddress = mHomeAddress;
        this.mTitle = mTitle;
    }


    /**
     * Checks if the parameter password is the correct password
     * @param password password being checked
     * @return true whether the parameter password is correct; false otherwise
     */
    public boolean authenticate(String password) {
        if (mFailedLoginAttempts >= 3) {
            return false;
        }
        boolean valid = password != null && password.equals(mPassword);
        if (valid) {
            mFailedLoginAttempts = 0;
        } else {
            mFailedLoginAttempts++;
        }
        return valid;
    }

    /**
     * Setter for email
     * @param email new email
     */
    public void setmEmail(String email) {
        if (email != null) {
            mEmail = email;
        }
    }

    /**
     * Setter for home address
     * @param address new address
     */
    public void setmHomeAddress(String address) {
        if (address != null) {
            mHomeAddress = address;
        }
    }

    /**
     * Setter for title for this User
     * @param title new title
     */
    public void setmTitle(String title) {
        if (title != null) {
            mTitle = title;
        }
    }

    /**
     * Getter for this User's title
     * @return title String
     */
    public String getmTitle() {
        return mTitle;
    }

    /**
     * Getter for User's position
     * @return position String
     */
    public String getmPosition() {
        return mPosition;
    }

    /**
     * Setter for this User's position
     * @param mPosition new position
     */
    public void setmPosition(String mPosition) {
        this.mPosition = mPosition;
    }

    /**
     * Setter for User's password
     * @param password new password
     */
    public void setmPassword(String password) {
        mPassword = password;
    }

    /**
     * Getter for this User's password
     * @return password
     */
    public String getmPassword() {
        return mPassword;
    }

    /**
     * Getter for home address
     * @return home address
     */
    public String getmHomeAddress() {
        return mHomeAddress;
    }

    /**
     * Getter for email adddress
     * @return email address
     */
    public String getmEmail() {
        return mEmail;
    }

    /**
     * Getter for User's username
     * @return username
     */
    public String getmUserName() {
        return mUserName;
    }

    /**
     * Getter for Mapping which is required for Firebase
     * @return mapping of unique identifier for this User to a map of its fields
     */
    public Map<String, Map<String, String>> getMap() {
        Map<String, Map<String, String>> u = new HashMap<>();
        Map<String, String> d = new HashMap<>();
        d.put("username", mUserName);
        d.put("password", mPassword);
        d.put("email", mEmail);
        d.put("homeaddress", mHomeAddress);
        d.put("title", mTitle);
        d.put("position", mPosition);
        d.put("loginattempts", String.valueOf(mFailedLoginAttempts));
        u.put(mUserName, d);
        return u;
    }
}
