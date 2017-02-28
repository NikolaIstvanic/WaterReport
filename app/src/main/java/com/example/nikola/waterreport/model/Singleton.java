package com.example.nikola.waterreport.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nikola Istvanic
 */

public class Singleton {
    /**
     * variable for storing mapping betweeen Username and User
     */
    public static Map<String, User> mappings = new HashMap<>();
    public static ArrayList<WaterReport> pseudoDB = new ArrayList<WaterReport>();
    /**
     * variable for storing how many water reports have been entered
     */
    public static int id_num = 0;

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
