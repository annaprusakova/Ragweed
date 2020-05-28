package com.prusakova.ragweed;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{

    private final LayoutInflater mInflater;

    public CustomInfoWindowAdapter(LayoutInflater inflater) {
        this.mInflater = inflater;
    }

    @Override public View getInfoWindow(Marker marker) {
        final View popup = mInflater.inflate(R.layout.info_window, null);


        ((TextView) popup.findViewById(R.id.titleInfo)).setText(marker.getTitle());
        ((TextView) popup.findViewById(R.id.snippInfo)).setText(marker.getSnippet());

        return popup;
        //return null;
    }

    @Override public View getInfoContents(Marker marker) {
        final View popup = mInflater.inflate(R.layout.info_window, null);

        ((TextView) popup.findViewById(R.id.titleInfo)).setText(marker.getTitle());
         ((TextView) popup.findViewById(R.id.snippInfo)).setText(marker.getSnippet());


        return popup;
    }
}
