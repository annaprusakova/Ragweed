package com.prusakova.ragweed.model;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;


public class MarkerClusterItem implements ClusterItem {

    private  LatLng mPosition;
    private  String mTitle;
    private  String mSnippet;

    public MarkerClusterItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    public MarkerClusterItem(double lat, double lng, String title, String snippet) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
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

}
