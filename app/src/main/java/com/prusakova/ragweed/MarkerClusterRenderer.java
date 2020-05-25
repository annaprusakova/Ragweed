package com.prusakova.ragweed;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.prusakova.ragweed.model.MarkerClusterItem;

public class MarkerClusterRenderer  extends DefaultClusterRenderer<MarkerClusterItem> {
    private final Context mContext;

    public MarkerClusterRenderer(Context context, GoogleMap map,
                                 ClusterManager<MarkerClusterItem> clusterManager) {
        super(context, map, clusterManager);

        mContext = context;
    }

    @Override protected void onBeforeClusterItemRendered(MarkerClusterItem item,
                                                         MarkerOptions markerOptions) {
        final BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);

        markerOptions.icon(markerDescriptor).snippet(item.getTitle());
    }

}
