package com.example.nikola.waterreport.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map;

/**
 * @author Nikola Istvanic
 */
public class Singleton {
    /**
     * Database
     */
    private static FirebaseDatabase fdb = FirebaseDatabase.getInstance();

    /**
     * variable for storing mapping betweeen Username and User
     */
    public static Map<String, User> mappings = new HashMap<>();
    /**
     * Database of submitted WaterReports
     */
    public static Set<WaterReport> waterreports = new HashSet<>();
    /**
     * Database of submitted QualityReports
     */
    public static Set<QualityReport> qualityreports = new HashSet<>();
    /**
     * variable for storing how many users have been have been entered
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

    /**
     * Add WaterReport to Firebase database
     * @param w WaterReport being added
     */
    public static void addWaterReport(WaterReport w) {
        fdb.getReference("WaterReports").child(Integer.toString(w.getmId())).setValue(w);
    }

    /**
     * Add Quality/PurityReport to Firebase database
     * @param q the quality report to add
     */
    public static void addQualityReport(QualityReport q) {
        fdb.getReference("QualityReports").child(Integer.toString(qualityreports.size())).setValue(q);
    }

    /**
     * Add User to Firebase database
     * @param u User to add
     */
    public static void addUser(User u) {
        u.setmId(mappings.size());
        u.setmHomeAddress("");
        u.setmTitle("Mr.");
        fdb.getReference("Users").child(String.valueOf(u.getmId())).setValue(u);
    }

    public static void onDataChanged() {
        DatabaseReference users = fdb.getReference().child("Users");
        DatabaseReference water = fdb.getReference().child("WaterReports");
        DatabaseReference quality = fdb.getReference().child("QualityReports");
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again whenever data at this location is updated.
                GenericTypeIndicator<List<User>> l = new GenericTypeIndicator<List<User>>() {};
                List<User> users = dataSnapshot.getValue(l);
                users.removeAll(Collections.singleton(null));
                if (mappings.keySet().size() == 0) {
                    for (User u : users) {
                        addToMappings(u.getmUserName(), u);
                    }
                } else {
                    User u = users.get(users.size());
                    mappings.put(u.getmUserName(), u);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("FAILURE", "Failed to read value.", error.toException());
            }
        });

        water.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again whenever data at this location is updated.
                waterreports.removeAll(Collections.singleton(null));
                GenericTypeIndicator<List<WaterReport>> l =
                        new GenericTypeIndicator<List<WaterReport>>() {};
                List<WaterReport> reports = dataSnapshot.getValue(l);
                reports.removeAll(Collections.singleton(null));
                if (waterreports.size() == 0) {
                    for (WaterReport r : reports) {
                        waterreports.add(r);
                    }
                } else {
                    for (WaterReport r : reports) {
                        waterreports.add(r);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("FAILURE", "Failed to read value.", error.toException());
            }
        });

        quality.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again whenever data at this location is updated.
                GenericTypeIndicator<List<QualityReport>> l =
                        new GenericTypeIndicator<List<QualityReport>>() {};
                List<QualityReport> purities = dataSnapshot.getValue(l);
                if (qualityreports.size() == 0) {
                    for (QualityReport r : purities) {
                        qualityreports.add(r);
                    }
                } else {
                    for (QualityReport r : purities) {
                        if (!qualityreports.contains(r)) {
                            qualityreports.add(r);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("FAILURE", "Failed to read value.", error.toException());
            }
        });
    }
}
