package com.example.nikola.waterreport.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Quality/Purity report information holder.
 *
 * Created by Nikola Istvanic.
 */
public class QualityReport {
    private String mUserName;
    private String mTime;
    private int mId;
    private String mLocation;
    private String mCondition;
    private double mVirusPPM;
    private double mContaminantPPM;
    private double mLat;
    private double mLng;

    /**
     * No arg constructor (for Firebase)
     */
    public QualityReport() {}

    /**
     * Constructor for QualityReport object
     * @param userName Username of user who created this QualityReport
     * @param time Time autogenerated whenever this QualityReport was created
     * @param id ID autogenerated whenever this QualityReport was created
     * @param location Location entered by the user upon creation
     * @param condition Condition chosen by the user
     * @param virusPPM virus PPM double
     * @param contaminantPPM contamination PPM double
     * @param lat QualityReport latitude
     * @param lng QualityReport longitude
     */
    public QualityReport(String userName, String time, int id, String location, String condition,
                         double virusPPM, double contaminantPPM, double lat, double lng) {
        this.mUserName = userName;
        this.mTime = time;
        this.mId = id;
        this.mLocation = location;
        this.mCondition = condition;
        this.mVirusPPM = virusPPM;
        this.mContaminantPPM = contaminantPPM;
        this.mLat = lat;
        this.mLng = lng;
    }

    /**
     * Getter for userName
     * @return Username of the user who created this QualityReport
     */
    public String getmUserName() {
        return mUserName;
    }

    /**
     * Location user entered whenever creating this QualityReport
     * @return location private data
     */
    public String getmLocation() {
        return mLocation;
    }

    /**
     * Getter for the time autogenerated when this QualityReport was created
     * @return time private data
     */
    public String getmTime() {
        return mTime;
    }

    /**
     * Getter for the ID of this QualityReport
     * @return ID autogenerated whenever this QualityReport was created
     */
    public int getmId() {
        return mId;
    }

    /**
     * Getter for condition
     * @return String representation of the source
     */
    public String getmCondition() {
        return mCondition;
    }

    /**
     * Getter for virusPPM.
     * @return virusPPM
     */
    public double getmVirusPPM() {
        return mVirusPPM;
    }

    /**
     * Getter for contaminantPPM.
     * @return contaminantPPM
     */
    public double getmContaminantPPM() {
        return mContaminantPPM;
    }

    /**
     * Getter for latitude
     * @return latitude for this WaterReport
     */
    public double getmLat() {
        return mLat;
    }

    /**
     * Getter for longitude
     * @return longitude for this WaterReport
     */
    public double getmLng() {
        return mLng;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public void setmLng(double mLng) {
        this.mLng = mLng;
    }

    public void setmCondition(String mCondition) {
        this.mCondition = mCondition;
    }

    public void setmContaminantPPM(double mContaminantPPM) {
        this.mContaminantPPM = mContaminantPPM;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public void setmLat(double mLat) {
        this.mLat = mLat;
    }

    public void setmVirusPPM(double mVirusPPM) {
        this.mVirusPPM = mVirusPPM;
    }

    /**
     * Equality of QualityReports is determined by location and virus/contaminant PPM.
     * @param a The object that you are comparing it to
     * @return true if the id of the object is equal to this objects id, false otherwise
     */
    @Override
    public boolean equals(Object a) {
        if (a == null || !(a instanceof QualityReport)) {
            return false;
        }
        if (this == a) {
            return true;
        }
        QualityReport that = (QualityReport) a;
        return getmLat() == that.getmLat() && getmLng() == that.getmLng()
                && getmVirusPPM() == that.getmVirusPPM()
                && getmContaminantPPM() == that.getmContaminantPPM();
    }

    /**
     * String representation of QualityReport
     * @return QualityReport as a String with all metadata listed
     */
    @Override
    public String toString() {
        return "ID: " + mId + "\nCreated on " + mTime + "\nCreated by " + mUserName + "\nSource: "
                + "\nCondition: " + mCondition + "\nLocation: " + mLocation
                + "\nVirus PPM: " + mVirusPPM + "\nContaminant PPM: " + mContaminantPPM
                + "\nLatitude: " + mLat + "\nLongitude: " + mLng + "\n\n";
    }

    public Map<String, Map<String, String>> getMap() {
        Map<String, Map<String, String>> u = new HashMap<>();
        Map<String, String> d = new HashMap<>();
        d.put("id", String.valueOf(mId)); // must store as String for firebase
        d.put("time", mTime);
        d.put("username", mUserName);
        d.put("condition", mCondition);
        d.put("location", mLocation);
        d.put("latitude", String.valueOf(mLat));
        d.put("longitude", String.valueOf(mLng));
        d.put("virus", String.valueOf(mVirusPPM));
        d.put("contaminant", String.valueOf(mContaminantPPM));
        u.put(String.valueOf(mLat).replaceAll("\\.", "")
                + String.valueOf(mLng).replaceAll("\\.", "") + mTime.replaceAll("/", ""), d);
        return u;
    }
}