package com.prusakova.ragweed.model;

import com.google.gson.annotations.SerializedName;

public class Tracker {
    @SerializedName("tracker_id")
    private int tracker_id;
    @SerializedName("tracker_date")
    private String tracker_date;
    @SerializedName("itchy_eyes")
    private int itchy_eyes;
    @SerializedName("water_eyes")
    private int water_eyes;
    @SerializedName("sore_throat")
    private int sore_throat;
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("runny_nose")
    private int runny_nose;
    @SerializedName("cough")
    private int cough;
    @SerializedName("pressure_sinuses")
    private int pressure_sinuses;
    @SerializedName("blue_under_eyes")
    private int blue_under_eyes;
    @SerializedName("bad_sleep")
    private int bad_sleep;
    @SerializedName("allergy_eczema")
    private int allergy_eczema;
    @SerializedName("degree")
    private int degree;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;

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

    public int getItchy_eyes() {
        return itchy_eyes;
    }

    public void setItchy_eyes(int itchy_eyes) {
        this.itchy_eyes = itchy_eyes;
    }

    public int getWater_eyes() {
        return water_eyes;
    }

    public void setWater_eyes(int water_eyes) {
        this.water_eyes = water_eyes;
    }

    public int getSore_throat() {
        return sore_throat;
    }

    public void setSore_throat(int sore_throat) {
        this.sore_throat = sore_throat;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRunny_nose() {
        return runny_nose;
    }

    public void setRunny_nose(int runny_nose) {
        this.runny_nose = runny_nose;
    }

    public int getCough() {
        return cough;
    }

    public void setCough(int cough) {
        this.cough = cough;
    }

    public int getPressure_sinuses() {
        return pressure_sinuses;
    }

    public void setPressure_sinuses(int pressure_sinuses) {
        this.pressure_sinuses = pressure_sinuses;
    }

    public int getBlue_under_eyes() {
        return blue_under_eyes;
    }

    public void setBlue_under_eyes(int blue_under_eyes) {
        this.blue_under_eyes = blue_under_eyes;
    }

    public int getBad_sleep() {
        return bad_sleep;
    }

    public void setBad_sleep(int bad_sleep) {
        this.bad_sleep = bad_sleep;
    }

    public int getAllergy_eczema() {
        return allergy_eczema;
    }

    public void setAllergy_eczema(int allergy_eczema) {
        this.allergy_eczema = allergy_eczema;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
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
}
