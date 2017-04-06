package com.example.nikola.waterreport.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

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

    /**
     * Add WaterReport to Firebase database
     * @param w WaterReport being added
     */
    public static void addWaterReport(WaterReport w) {
        fdb.getReference("WaterReports").child(Integer.toString(waterreports.size())).setValue(w);
    }

    /**
     * Add Quality/PurityReport to Firebase database
     * @param qr the quality report to add
     */
    public static void addQualityReport(QualityReport qr) {
        fdb.getReference("QualityReports").child(Integer.toString(qualityreports.size())).setValue(qr);
    }

    /**
     * Add User to Firebase database
     * @param u User to add
     */
    public static void addUser(User u) {
        u.setmId(mappings.size() + 1);
        Log.d("TEST", u.getPassword());
        fdb.getReference("Users").child(String.valueOf(u.getmId())).setValue(u);
    }

    /**
     * Refreshes instance variables storing app information from database.
     */
    public static void setupDatabaseReferences() {
        DatabaseReference users = fdb.getReference().child("Users");
        DatabaseReference waterReports = fdb.getReference().child("WaterReports");
        DatabaseReference waterPurityReports = fdb.getReference().child("QualityReport");
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                GenericTypeIndicator<List<User>> l = new GenericTypeIndicator<List<User>>() {};
                List<User> userList = dataSnapshot.getValue(l);
                if (mappings.keySet().size() == 0) {
                    for (User u : userList) {
                        mappings.put(u.getmUserName(), u);
                    }
                } else {
                    User u = userList.get(userList.size() - 1);
                    mappings.put(u.getmUserName(), u);
                }
                Log.d("Success", "Value is: " + userList);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Failure", "Failed to read value.", error.toException());
            }
        });

        waterReports.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                GenericTypeIndicator<List<WaterReport>> l =
                        new GenericTypeIndicator<List<WaterReport>>() {};
                List<WaterReport> reportListDB = dataSnapshot.getValue(l);
                if (waterreports.size() == 0) {
                    for (WaterReport r : reportListDB) {
                        waterreports.add(r);
                    }
                } else {
                    for (WaterReport r : reportListDB) {
                        if (!waterreports.contains(r)) {
                            waterreports.add(r);
                        }
                    }
                }
                Log.d("Success", "Value is: " + reportListDB);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Failure", "Failed to read value.", error.toException());
            }
        });

        waterPurityReports.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                GenericTypeIndicator<List<QualityReport>> l =
                        new GenericTypeIndicator<List<QualityReport>>() {};
                List<QualityReport> purityReportListDB = dataSnapshot.getValue(l);
                if (qualityreports.size() == 0) {
                    for (QualityReport r : purityReportListDB) {
                        qualityreports.add(r);
                    }
                } else {
                    for (QualityReport r : purityReportListDB) {
                        if (!qualityreports.contains(r)) {
                            qualityreports.add(r);
                        }
                    }
                }
                Log.d("Success", "Value is: " + purityReportListDB);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Failure", "Failed to read value.", error.toException());
            }
        });
    }
}
