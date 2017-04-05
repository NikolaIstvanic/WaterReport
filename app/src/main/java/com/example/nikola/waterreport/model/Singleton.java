package com.example.nikola.waterreport.model;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;

/**
 * @author Nikola Istvanic
 */
public class Singleton {
    /**
     * variable for storing mapping betweeen Username and User
     */
    public static Map<String, User> mappings = new HashMap<>();
    /**
     * Database of submitted WaterReports
     */
    public static Set<WaterReport> pseudoDB = new HashSet<>();
    /**
     * variable for storing how many water reports have been entered
     */
    public static int id_num = 0;

    /**
     * Add <String, Username> pair to database.
     * @param username Username being added
     * @param user User whose username is username
     * @return True if entry was added, false otherwise
     */
    public static boolean addToMappings(String username, User user) {
        if (username == null || user == null) {
            return false;
        }
        mappings.put(username, user);
        return true;
    }

    /**
     * Looks if the username to password pairing matches what the User object in the map's password
     * field is a match.
     * @param username the username to lookup
     * @param password the password entered by user
     * @return true if the mapping exists false elsewhere
     */
    public static boolean lookup(String username, String password) {
        return !(mappings.get(username) == null || mappings.isEmpty()) && mappings.get(username).authenticate(password);
    }
}
