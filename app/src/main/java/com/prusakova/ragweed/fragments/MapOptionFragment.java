package com.prusakova.ragweed.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.prusakova.ragweed.MapsActivity;
import com.prusakova.ragweed.R;
import com.prusakova.ragweed.activities.AddPointActivity;
import com.prusakova.ragweed.activities.LocationViewActivity;

public class MapOptionFragment extends Fragment {

private Button goToMap;
private Button addPoint;
private Button seePoint;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        goToMap = view.findViewById(R.id.watch_map);
        addPoint = view.findViewById(R.id.add_point);
        seePoint = view.findViewById(R.id.watch_point);
        toolbar = view.findViewById(R.id.mapToolBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


        goToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
            }
        });

        addPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddPointActivity.class);
                startActivity(intent);
            }
        });

        seePoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LocationViewActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
