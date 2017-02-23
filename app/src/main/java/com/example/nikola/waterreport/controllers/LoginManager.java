package com.example.nikola.waterreport.controllers;

import com.example.nikola.waterreport.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nikola Istvnaic
 */

public class LoginManager {
    /**
     * variable for storing mapping betweeen Username and User
     */
    public static Map<String, User> mappings = new HashMap<>();

    /**
     *
     * @param username the username to lookup
     * @param password the password entered by user
     * @return true if the mapping exists false elsewhere
     */
    public static boolean lookup(String username, String password) {
        return !(mappings.get(username) == null || mappings.isEmpty()) && mappings.get(username).authenticate(password);
    }
}
