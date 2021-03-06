package model;

import java.io.Serializable;

/**
 * Created by Prithviraj Ammanabrolu.
 */
public class WaterReport implements Serializable {
    private String mUserName;
    private String mTime;
    private int mId;
    private String mLocation;
    private String mSource;
    private String mCondition;
    private double mLat;
    private double mLng;

    /**
     * Constructor for WaterReport object
     * @param userName Username of user who created this WaterReport
     * @param time Time autogenerated whenever this WaterReport was created
     * @param id ID autogenerated whenever this  WaterReport was created
     * @param location Location entered by the user upon creation
     * @param source Source chosen by the user
     * @param condition Condition chosen by the user
     * @param lat latitude
     * @param lng longitude
     */
    public WaterReport(String userName, String time, int id, String location, String source,
                       String condition, double lat, double lng) {
        this.mUserName = userName;
        this.mTime = time;
        this.mId = id;
        this.mLocation = location;
        this.mSource = source;
        this.mCondition = condition;
        this.mLat = lat;
        this.mLng = lng;
    }

    /**
     * Constructor for WaterReport object
     * @param userName Username of user who created this WaterReport
     * @param time Time autogenerated whenever this WaterReport was created
     * @param id ID autogenerated whenever this  WaterReport was created
     * @param location Location entered by the user upon creation
     * @param source Source chosen by the user
     * @param condition Condition chosen by the user
     */
    public WaterReport(String userName, String time, int id, String location, String source,
                       String condition) {
        this.mUserName = userName;
        this.mTime = time;
        this.mId = id;
        this.mLocation = location;
        this.mSource = source;
        this.mCondition = condition;
    }

    /**
     * Getter for userName
     * @return Username of the user who created this WaterReport
     */
    public String getmUserName() {
        return mUserName;
    }

    /**
     * Location user entered whenever creating this WaterReport
     * @return location private data
     */
    public String getmLocation() {
        return mLocation;
    }

    /**
     * Getter for the time autogenerated when this WaterReport was created
     * @return time private data
     */
    public String getmTime() {
        return mTime;
    }

    /**
     * Getter for the ID of this WaterReport
     * @return ID autogenerated whenever this WaterReport was created
     */
    public int getmId() {
        return mId;
    }

    /**
     * Getter for source
     * @return String representation of the source
     */
    public String getmSource() {
        return mSource;
    }

    /**
     * Getter for condition
     * @return String representation of the source
     */
    public String getmCondition() {
        return mCondition;
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
     * Setter for condition
     * @param mCondition new condition String
     */
    public void setmCondition(String mCondition) {
        this.mCondition = mCondition;
    }

    /**
     * Setter for WaterReport's ID
     * @param mId new ID
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
     * Setter for longitude
     * @param mLng new longitude
     */
    public void setmLng(double mLng) {
        this.mLng = mLng;
    }

    /**
     * Setter for location
     * @param mLocation new location
     */
    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    /**
     * Setter for source String
     * @param mSource new source
     */
    public void setmSource(String mSource) {
        this.mSource = mSource;
    }

    /**
     * Setter for time
     * @param mTime new time String
     */
    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    /**
     * Setter for this WaterReport's username for User who filed it
     * @param mUserName new username
     */
    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    /**
     * Equality of WaterReports is determined by source and location.
     * @param a The object that you are comparing it to
     * @return true sources, latitudes, and longitudes are the same
     */
    @Override
    public boolean equals(Object a) {
        if (a == null || !(a instanceof WaterReport)) {
            return false;
        }
        if (this == a) {
            return true;
        }
        WaterReport that = (WaterReport) a;
        return getmSource().equals(that.getmSource())
                && getmLat() == that.getmLat()
                && getmLng() == that.getmLng()
                && getmId() == that.getmId();
    }

    /**
     * String representation of WaterReport
     * @return WaterReport as a String with all metadata listed
     */
    @Override
    public String toString() {
        return "ID: " + mId + "\nCreated on " + mTime + "\nCreated by " + mUserName + "\nSource: "
                + mSource + "\nCondition: " + mCondition + "\nLocation: " + mLocation
                + "\nLatitude: " + mLat + "\nLongitude: " + mLng + "\n\n";
    }
}
