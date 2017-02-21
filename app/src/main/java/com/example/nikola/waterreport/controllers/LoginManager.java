package com.example.nikola.waterreport.controllers;

import com.example.nikola.waterreport.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nikola Istvnaic
 */
public class LoginManager {
    public static Map<String, User> mappings = new HashMap<>();

    public static boolean lookup(String username, String password) {
        if(mappings.get(username) == null || mappings.isEmpty())
            return false;
        return mappings.get(username).authenticate(password);
    }
}
