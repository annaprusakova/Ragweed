package com.prusakova.ragweed.model;

import com.google.gson.annotations.SerializedName;

public class Tracker {
    @SerializedName("tracker_id")
    private int tracker_id;
    @SerializedName("tracker_date")
    private String tracker_date;
    @SerializedName("itchy_nose")
    private int itchy_nose;
    @SerializedName("water_eyes")
    private int water_eyes;
    @SerializedName("runny_nose")
    private int runny_nose;
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("eye_redness")
    private int eye_redness;
    @SerializedName("degree")
    private int degree;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTracker_id() {
        return tracker_id;
    }

    public void setTracker_id(int tracker_id) {
        this.tracker_id = tracker_id;
    }

    public String getTracker_date() {
        return tracker_date;
    }

    public void setTracker_date(String tracker_date) {
        this.tracker_date = tracker_date;
    }


    public int getItchy_nose() {
        return itchy_nose;
    }

    public void setItchy_nose(int itchy_nose) {
        this.itchy_nose = itchy_nose;
    }

    public int getRunny_nose() {
        return runny_nose;
    }

    public void setRunny_nose(int runny_nose) {
        this.runny_nose = runny_nose;
    }

    public int getWater_eyes() {
        return water_eyes;
    }

    public void setWater_eyes(int water_eyes) {
        this.water_eyes = water_eyes;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public int getEye_redness() {
        return eye_redness;
    }

    public void setEye_redness(int eye_redness) {
        this.eye_redness = eye_redness;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }
}
