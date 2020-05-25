package com.prusakova.ragweed.model;

import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("location_id")
    private int location_id;
    @SerializedName("loc_name")
    private String loc_name;
    @SerializedName("loc_date")
    private String loc_date;
    @SerializedName("loc_photo")
    private String loc_photo;
    @SerializedName("loc_point")
    private String loc_point;
    @SerializedName("loc_description")
    private String loc_description;
   @SerializedName("user_id_loc")
    private int user_id_loc;
    @SerializedName("loc_latlng")
    private String loc_latlng;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public String getLoc_name() {
        return loc_name;
    }

    public void setLoc_name(String loc_name) {
        this.loc_name = loc_name;
    }

    public String getLoc_date() {
        return loc_date;
    }

    public void setLoc_date(String loc_date) {
        this.loc_date = loc_date;
    }

    public String getLoc_photo() {
        return loc_photo;
    }

    public void setLoc_photo(String loc_photo) {
        this.loc_photo = loc_photo;
    }

    public String getLoc_point() {
        return loc_point;
    }

    public void setLoc_point(String loc_point) {
        this.loc_point = loc_point;
    }

    public String getLoc_description() {
        return loc_description;
    }

    public void setLoc_description(String loc_description) {
        this.loc_description = loc_description;
    }

    public int getUser_id() {
        return user_id_loc;
    }

    public void setUser_id(int user_id_loc) {
        this.user_id_loc = user_id_loc;
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

    public String getLoc_lat_lng() {
        return loc_latlng;
    }

    public void setLoc_lat_lng(String loc_lat_lng) {
        this.loc_latlng = loc_lat_lng;
    }
}
