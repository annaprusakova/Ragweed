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
import com.prusakova.ragweed.activities.MapStatisticActivity;

public class MapOptionFragment extends Fragment {

private Button goToMap;
private Button addPoint;
private Button seePoint;
private Button seeStatistic;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        goToMap = view.findViewById(R.id.watch_map);
        addPoint = view.findViewById(R.id.add_point);
        seePoint = view.findViewById(R.id.watch_point);
        seeStatistic = view.findViewById(R.id.watch_statistic);
        toolbar = view.findViewById(R.id.toolbar_map);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if(((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        }


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

        seeStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapStatisticActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
