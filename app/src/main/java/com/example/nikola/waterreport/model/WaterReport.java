package com.example.nikola.waterreport.model;

/**
 * Created by Prithviraj on 2/28/17.
 */

public class WaterReport {
    String userName, location, time;
    int id;
    public WaterReport(int id, String time, String location, String userName) {
        this.userName = userName;
        this.id = id;
        this.location = location;
        this.time = time;
    }

    /**
     * @param a The object that you are comparing it to
     * @return true if the id of the object is equal to this objects id, false otherwise
     */
    @Override
    public boolean equals(Object a) {
        if (a == null || !(a instanceof WaterReport)) {
            return false;
        }
        WaterReport that = (WaterReport) a;
        if (this == that) {
            return true;
        }
        return this.id == that.id;
    }


}
