package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
}
