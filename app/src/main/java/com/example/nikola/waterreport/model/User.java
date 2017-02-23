package com.example.nikola.waterreport.model;

/**
 * @author Nikola Istvnaic
 */
public class User {
    private String mUserName;
    private String mPassword;
    private String mId;
    private String mHomeAddress;
    private String mTitle;
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
    public void setEmail(String email){
        if(email != null)
           mId = email;
    }
    public void setHomeAddress(String address){
        if(address != null){
            mHomeAddress = address;
        }
    }
    public void setTitle(String title){
        if(title != null){
            mTitle = title;
        }
    }
    public String getTitle(){
        return mTitle;
    }
    public String getHomeAddress(){
        return mHomeAddress;
    }
    public String getEmail(){
        return mId;
    }

}
