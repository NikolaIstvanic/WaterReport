package model;

import java.io.Serializable;

/**
 * Quality/Purity report information holder.
 *
 * Created by Nikola Istvanic.
 */
public class QualityReport implements Serializable{
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
                         double virusPPM, double contaminantPPM) {
        this.mUserName = userName;
        this.mTime = time;
        this.mId = id;
        this.mLocation = location;
        this.mCondition = condition;
        this.mVirusPPM = virusPPM;
        this.mContaminantPPM = contaminantPPM;
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

    /**
     * Setter for username
     * @param mUserName new username
     */
    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    /**
     * Setter for time
     * @param mTime new time
     */
    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    /**
     * Setter for location string
     * @param mLocation new location
     */
    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    /**
     * Setter for longitude
     * @param mLng new longitude
     */
    public void setmLng(double mLng) {
        this.mLng = mLng;
    }

    /**
     * Setter for condition string
     * @param mCondition new condition
     */
    public void setmCondition(String mCondition) {
        this.mCondition = mCondition;
    }

    /**
     * Setter for contaminant PPM
     * @param mContaminantPPM new PPM
     */
    public void setmContaminantPPM(double mContaminantPPM) {
        this.mContaminantPPM = mContaminantPPM;
    }

    /**
     * Setter for ID
     * @param mId new QualityReport ID
     */
    public void setmId(int mId) {
        this.mId = mId;
    }

    /**
     * Setter for latitude
     * @param mLat new latitude
     */
    public void setmLat(double mLat) {
        this.mLat = mLat;
    }

    /**
     * Setter for virus PPM
     * @param mVirusPPM new PPM
     */
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
}
