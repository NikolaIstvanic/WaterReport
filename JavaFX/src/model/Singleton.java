package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

/**
 * @author Nikola Istvanic
 */
public class Singleton {
    private static final Singleton instance = new Singleton();
    /**
     * variable for storing mapping betweeen Username and User
     */
    public Map<String, User> mappings = new HashMap<>();
    /**
     * variable for storing water reports locally
     */
    public Set<WaterReport> waterreports = new HashSet<>();
    /**
     * variable for storing quality reports locally
     */
    public Set<QualityReport> qualityreports = new HashSet<>();

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
        return !(mappings.get(username) == null || mappings.isEmpty())
                && mappings.get(username).authenticate(password);
    }

    /**
     * Returns mapping of month to virus PPM
     * @param location location string
     * @param year year string
     * @return map of graph points
     */
    public ObservableList<XYChart.Data<Number, Number>> VPPMValues(String location, String year) {
    	ObservableList<XYChart.Data<Number, Number>> values = FXCollections.observableArrayList();
        int[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        for (int i = 1; i <= 12; i++) {
            int num = 0;
            double sum = 0;
            for (QualityReport q : qualityreports) {
                String r_year = q.getmTime().split(" ")[0].substring(6, 10);
                Integer reportMonth = Integer.parseInt(q.getmTime().split(" ")[0].substring(0, 2));
                if (q.getmLocation().equals(location) && r_year.equals(year) && reportMonth == i) {
                    sum += q.getmVirusPPM();
                    num++;
                }
            }
            double avg = num == 0 ? 0 : sum / num;
            if (num != 0) {
                values.add(new XYChart.Data<>(months[i - 1], avg));
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
    public ObservableList<XYChart.Data<Number, Number>> CPPMValues(String location, String year) {
    	ObservableList<XYChart.Data<Number, Number>> values = FXCollections.observableArrayList();
        int[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        for (int i = 1; i <= 12; i++) {
            int num = 0;
            double sum = 0;
            for (QualityReport q : qualityreports) {
                String r_year = q.getmTime().split(" ")[0].substring(6, 10);
                Integer reportMonth = Integer.parseInt(q.getmTime().split(" ")[0].substring(0, 2));
                if (q.getmLocation().equals(location) && r_year.equals(year) && reportMonth == i) {
                    sum += q.getmContaminantPPM();
                    num++;
                }
            }
            double avg = num == 0 ? 0 : sum / num;
            if (num != 0) {
            	values.add(new XYChart.Data<>(months[i - 1], avg));
            }
        }
        return values;
    }

    public void update() {
        String filename = "users.dat";
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            mappings = (Map<String, User>) objectInputStream.readObject();
            objectInputStream.close();
            inputStream.close();
        } catch (Exception e) {
            System.err.println(e.toString());
            System.err.println("Unable to load user data.");
        }
        filename = "reports.dat";
        try {
            inputStream = new FileInputStream(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            waterreports = (Set<WaterReport>) objectInputStream.readObject();
            objectInputStream.close();
            inputStream.close();
        } catch (Exception e) {
            System.err.println(e.toString());
            System.err.println("Unable to load report data.");
        }
        filename = "quality.dat";
        try {
            inputStream = new FileInputStream(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            qualityreports = (Set<QualityReport>) objectInputStream.readObject();
            objectInputStream.close();
            inputStream.close();
        } catch (Exception e) {
            System.err.println(e.toString());
            System.err.println("Unable to load user data.");
        }
    }

    public void updateWaterReports() {
        String filename = "reports.dat";
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(filename);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(waterreports);
            objectOutputStream.close();
            outputStream.close();
        } catch (Exception e) {
            System.err.println(e.toString());
            System.err.println("Unable to save water report data.");
        }
    }

    public void updateQualityReports() {
        String filename = "quality.dat";
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(filename);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(qualityreports);
            objectOutputStream.close();
            outputStream.close();
        } catch (Exception e) {
            System.err.println(e.toString());
            System.err.println("Unable to save quality report data.");
        }
    }

    public void updateUsers() {
        String filename = "users.dat";
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(filename);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(mappings);
            objectOutputStream.close();
            outputStream.close();
        } catch (Exception e) {
            System.err.println(e.toString());
            System.err.println("Unable to save quality report data.");
        }
    }
}
