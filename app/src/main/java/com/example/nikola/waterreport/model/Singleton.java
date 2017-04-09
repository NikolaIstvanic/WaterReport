package com.example.nikola.waterreport.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nikola Istvanic
 */
public class Singleton {
    private static final Singleton instance = new Singleton();
    /**
     * Database
     */
    private FirebaseDatabase fdb = FirebaseDatabase.getInstance();
    /**
     * variable for storing mapping betweeen Username and User
     */
    public Map<String, User> mappings = new HashMap<>();
    /**
     * variable for storing water reports locally
     */
    public List<WaterReport> waterreports = new ArrayList<>();
    /**
     * variable for storing quality reports locally
     */
    public List<QualityReport> qualityreports = new ArrayList<>();

    /**
     * Private constructor so there are no multiple instances
     */
    private Singleton() {}

    /**
     * Returns instance of Singleton
     * @return instance of Singleton
     */
    public static Singleton getInstance() {
        return instance;
    }

    /**
     * Add <String, Username> pair to database.
     * @param username Username being added
     * @param user User whose username is username
     * @return True if entry was added, false otherwise
     */
    public boolean addToMappings(String username, User user) {
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
    public boolean lookup(String username, String password) {
        return !(mappings.get(username) == null || mappings.isEmpty()) && mappings.get(username).authenticate(password);
    }

    /**
     * Returns mapping of month to virus PPM
     * @param location location string
     * @param year year string
     * @return map of graph points
     */
    public HashMap<Integer, Double> VPPMValues(String location, String year) {
        HashMap<Integer, Double> values = new HashMap<>();
        int[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        for (int i = 1; i <= 12; i++) {
            int num = 0;
            double sum = 0;
            for (QualityReport q : qualityreports) {
                String r_year = q.getmTime().split(" ")[1].substring(6, 10);
                Integer reportMonth = Integer.parseInt(q.getmTime().split(" ")[1].substring(0, 2));
                if (q.getmLocation().equals(location) && r_year.equals(year) && reportMonth == i) {
                    sum += q.getmVirusPPM();
                    num++;
                }
            }
            double avg = num == 0 ? 0 : sum / num;
            if (num != 0) {
                values.put(months[i - 1], avg);
            }
        }
        return values;
    }

    /**
     * Returns mapping of month to contaminant PPM
     * @param location location string
     * @param year year string
     * @return map of graph points
     */
    public HashMap<Integer, Double> CPPMValues(String location, String year) {
        HashMap<Integer, Double> values = new HashMap<>();
        int[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        for (int i = 1; i <= 12; i++) {
            int num = 0;
            double sum = 0;
            for (QualityReport q : qualityreports) {
                String r_year = q.getmTime().split(" ")[1].substring(6, 10);
                Integer reportMonth = Integer.parseInt(q.getmTime().split(" ")[1].substring(0, 2));
                if (q.getmLocation().equals(location) && r_year.equals(year) && reportMonth == i) {
                    sum += q.getmContaminantPPM();
                    num++;
                }
            }
            double avg = num == 0 ? 0 : sum / num;
            if (num != 0) {
                values.put(months[i - 1], avg);
            }
        }
        return values;
    }

    /**
     * Add User to Firebase database
     * @param u User to add
     */
    public void addUser(final User u) {
        DatabaseReference userrs = fdb.getReference().child("Users");
        userrs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again whenever data at this location is updated.
                Object o = dataSnapshot.getValue();
                if (!(o instanceof ArrayList)) {
                    ArrayList<Map<String, Map<String, String>>> dbList = new ArrayList<>();
                    dbList.add(u.getMap());
                    fdb.getReference().child("Users").setValue(dbList);
                } else {
                    ArrayList existingList = (ArrayList) o;
                    boolean valid = true;
                    for (Object h : existingList) {
                        if (h instanceof HashMap) {
                            if (((HashMap) h).containsKey(u.getmUserName())) {
                                valid = false;
                                break;
                            }
                        }
                    }
                    if (valid) {
                        existingList.add(u.getMap());
                        fdb.getReference().child("Users").setValue(existingList);
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

    /**
     * Add WaterReport to Firebase database
     * @param w WaterReport being added
     */
    public void addWaterReport(final WaterReport w) {
        DatabaseReference waterReports = fdb.getReference().child("WaterReports");
        waterReports.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again whenever data at this location is updated.
                Object o = dataSnapshot.getValue();
                if (!(o instanceof ArrayList)) {
                    ArrayList<Map<String, Map<String, String>>> dbList = new ArrayList<>();
                    dbList.add(w.getMap());
                    fdb.getReference().child("WaterReports").setValue(dbList);
                } else {
                    ArrayList existingList = (ArrayList) o;
                    boolean valid = true;
                    for (Object h : existingList) {
                        if (h instanceof HashMap) {
                            if (((HashMap) h).containsKey(w.getmSource()
                                    + String.valueOf(w.getmLat()).replaceAll("\\.", "")
                                    + String.valueOf(w.getmLng()).replaceAll("\\.", "")
                                    + w.getmTime().replaceAll("/", ""))) {
                                valid = false;
                                break;
                            }
                        }
                    }
                    if (valid) {
                        existingList.add(w.getMap());
                        fdb.getReference().child("WaterReports").setValue(existingList);
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

    /**
     * Add Quality/PurityReport to Firebase database
     * @param q the quality report to add
     */
    public void addQualityReport(final QualityReport q) {
        DatabaseReference qualityReports = fdb.getReference().child("QualityReports");
        qualityReports.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again whenever data at this location is updated.
                Object o = dataSnapshot.getValue();
                if (!(o instanceof ArrayList)) {
                    ArrayList<Map<String, Map<String, String>>> dbList = new ArrayList<>();
                    dbList.add(q.getMap());
                    fdb.getReference().child("QualityReports").setValue(dbList);
                } else {
                    ArrayList existingList = (ArrayList) o;
                    boolean valid = true;
                    for (Object h : existingList) {
                        if (h instanceof HashMap) {
                            if (((HashMap) h).containsKey(
                                    String.valueOf(q.getmLat()).replaceAll("\\.", "")
                                    + String.valueOf(q.getmLng()).replaceAll("\\.", "")
                                    + q.getmTime().replaceAll("/", ""))) {
                                valid = false;
                                break;
                            }
                        }
                    }
                    if (valid) {
                        existingList.add(q.getMap());
                        fdb.getReference().child("QualityReports").setValue(existingList);
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

    public void updateLocal() {
        DatabaseReference userDB = fdb.getReference().child("Users");
        DatabaseReference waterDB = fdb.getReference().child("WaterReports");
        DatabaseReference qualityDB = fdb.getReference().child("QualityReports");
        userDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again whenever data at this location is updated.
                Object o = dataSnapshot.getValue();
                if (!(o instanceof ArrayList)) {

                } else {
                    ArrayList existingList = (ArrayList) o;
                    for (Object h : existingList) {
                        if (h instanceof HashMap) {
                            HashMap<String, HashMap<String, String>> mapping = (HashMap<String, HashMap<String,String>>) h;
                            String key = "";
                            for (String s : mapping.keySet()) {
                                key = s;
                            }
                            HashMap<String, String> existing = mapping.get(key);
                            addToMappings(existing.get("username"),
                                    new User(existing.get("username"),
                                            existing.get("password"),
                                            existing.get("email"),
                                            existing.get("homeaddress"),
                                            existing.get("title"),
                                            existing.get("position")));
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
        waterDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again whenever data at this location is updated.
                Object o = dataSnapshot.getValue();
                if (!(o instanceof ArrayList)) {

                } else {
                    ArrayList existingList = (ArrayList) o;
                    for (Object h : existingList) {
                        if (h instanceof HashMap) {
                            HashMap<String, HashMap<String, String>> mapping = (HashMap<String, HashMap<String,String>>) h;
                            String key = "";
                            for (String s : mapping.keySet()) {
                                key = s;
                            }
                            HashMap<String, String> existing = mapping.get(key);
                            WaterReport wr = new WaterReport(existing.get("username"),
                                    existing.get("time"), Integer.parseInt(existing.get("id")),
                                    existing.get("location"), existing.get("source"),
                                    existing.get("condition"),
                                    Double.parseDouble(existing.get("latitude")),
                                    Double.parseDouble(existing.get("longitude")));
                            if (!waterreports.contains(wr)) {
                                waterreports.add(wr);
                            }
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
        qualityDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again whenever data at this location is updated.
                Object o = dataSnapshot.getValue();
                if (!(o instanceof ArrayList)) {

                } else {
                    ArrayList existingList = (ArrayList) o;
                    for (Object h : existingList) {
                        if (h instanceof HashMap) {
                            HashMap<String, HashMap<String, String>> mapping = (HashMap<String, HashMap<String,String>>) h;
                            String key = "";
                            for (String s : mapping.keySet()) {
                                key = s;
                            }
                            HashMap<String, String> existing = mapping.get(key);
                            QualityReport qr = new QualityReport(existing.get("username"),
                                    existing.get("time"), Integer.parseInt(existing.get("id")),
                                    existing.get("location"), existing.get("condition"),
                                    Double.parseDouble(existing.get("virus")),
                                    Double.parseDouble(existing.get("contaminant")),
                                    Double.parseDouble(existing.get("latitude")),
                                    Double.parseDouble(existing.get("longitude")));
                            if (!qualityreports.contains(qr)) {
                                qualityreports.add(qr);
                            }
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
