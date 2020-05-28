package com.prusakova.ragweed.model;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;


public class MarkerClusterItem implements ClusterItem {

    private  LatLng mPosition;

    private  String mTitle;
    private  String mSnippet;
    private int mId;
    private String mDescription;
    private String mDate;
    private String mPhoto;

    public MarkerClusterItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    public MarkerClusterItem(double lat, double lng, String title, String snippet, int id, String description,String date, String photo) {

        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
        mId = id;
        mDescription = description;
        mDate = date;
        mPhoto = photo;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }

    public int getmId(){ return mId;}

    public String getmDescription(){ return mDescription;}

    public String getmDate() {return  mDate;}

    public String getmPhoto(){ return  mPhoto;}


}
