package com.prusakova.ragweed.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.prusakova.ragweed.R;
import com.prusakova.ragweed.activities.AddTrackerActivity;
import com.prusakova.ragweed.activities.MedicinesFragment;
import com.prusakova.ragweed.api.Api;
import com.prusakova.ragweed.api.ApiClient;
import com.prusakova.ragweed.model.Tracker;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackerFragment  extends Fragment {

    private Toolbar toolbar;
    private BarChart chart;
    public Api apiInterface;
    private List<Tracker> trackList;
    private Button months;
    private Button years;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracker, container, false);
        chart =  view.findViewById(R.id.chart);
        months = view.findViewById(R.id.btnMonth);
        years  = view.findViewById(R.id.btnYear);

        apiInterface = ApiClient.getClient().create(Api.class);


        toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_tracker);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        getData("tracker", "");
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tracker, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case  R.id.action_add:
                Intent add = new Intent(getActivity(), AddTrackerActivity.class);
                startActivity(add);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void getData(String type, String key) {
        Call<List<Tracker>> call = apiInterface.getTracker(type, key);
        call.enqueue(new Callback<List<Tracker>>() {
            @Override
            public void onResponse(Call<List<Tracker>> call, Response<List<Tracker>> response) {
                trackList = response.body();

                months.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setDataMonth(trackList);

                    }
                });

                years.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setData(trackList);
                    }
                });

                setData(trackList);
            }

            @Override
            public void onFailure(Call<List<Tracker>> call, Throwable t) {

            }
        });
    }



        private void setData(List<Tracker> list) {

           ArrayList<BarDataSet> dataSets = new ArrayList<>();


            ArrayList<BarEntry> allergy = new ArrayList<>();
            int count = 0;
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).getItchy_nose() == 1 && list.get(i).getRunny_nose() == 1 && list.get(i).getWater_eyes() == 1){
                    count = 3;
                }
                else if(list.get(i).getItchy_nose() == 1 && list.get(i).getRunny_nose() == 1 && list.get(i).getWater_eyes() == 0){
                    count = 2;
                }
                else if(list.get(i).getItchy_nose() == 1 && list.get(i).getRunny_nose() == 0 && list.get(i).getWater_eyes() == 0){
                    count = 1;
                }
                else if(list.get(i).getItchy_nose() == 0 && list.get(i).getRunny_nose() == 1 && list.get(i).getWater_eyes() == 1){
                    count = 2;
                }
                else if(list.get(i).getItchy_nose() == 1 && list.get(i).getRunny_nose() == 0 && list.get(i).getWater_eyes() == 1){
                    count = 2;
                }
                else if(list.get(i).getItchy_nose() == 0 && list.get(i).getRunny_nose() == 1 && list.get(i).getWater_eyes() == 0){
                    count = 1;
                }
                else if(list.get(i).getItchy_nose() == 0 && list.get(i).getRunny_nose() == 0 && list.get(i).getWater_eyes() == 1){
                    count = 1;
                }  else if(list.get(i).getItchy_nose() == 0 && list.get(i).getRunny_nose() == 0 && list.get(i).getWater_eyes() == 0){
                    count = 0;
                }
                BarEntry value = new BarEntry(count,i);
                allergy.add(value);
            }

            BarDataSet allergyData = new BarDataSet(allergy, "Степінь аллергії");
            allergyData.setColor(Color.rgb(93, 166, 158));

            dataSets.add(allergyData);

            ArrayList<String> xAxis = new ArrayList<>();

            ArrayList<String> dateForYears = new ArrayList<>();


            for(int i = 0; i < list.size();i++) {

                String d = list.get(i).getTracker_date();
                dateForYears.add(d.substring(5,7));
            }


            for (String months : dateForYears) {
                Log.d("CHART_RESPONSE", "month: " + months.toString());
                xAxis.add(months);
            }

            com.github.mikephil.charting.charts.BarChart chart = getActivity().findViewById(R.id.chart);

            BarData data = new BarData(xAxis, dataSets);

            chart.setData(data);
            chart.getAxisLeft().setEnabled(false);
            chart.getAxisRight().setEnabled(false);
            chart.getXAxis().setDrawGridLines(false);
            chart.animateXY(2000, 2000);
            chart.setDescription("");
            chart.invalidate();

        }

 private void setDataMonth(List<Tracker> list){
     ArrayList<BarDataSet> dataSets = new ArrayList<>();


     ArrayList<BarEntry> allergy = new ArrayList<>();
     int count = 0;
     for (int i = 0; i < list.size(); i++) {
         if(list.get(i).getItchy_nose() == 1 && list.get(i).getRunny_nose() == 1 && list.get(i).getWater_eyes() == 1){
             count = 3;
         }
         else if(list.get(i).getItchy_nose() == 1 && list.get(i).getRunny_nose() == 1 && list.get(i).getWater_eyes() == 0){
             count = 2;
         }
         else if(list.get(i).getItchy_nose() == 1 && list.get(i).getRunny_nose() == 0 && list.get(i).getWater_eyes() == 0){
             count = 1;
         }
         else if(list.get(i).getItchy_nose() == 0 && list.get(i).getRunny_nose() == 1 && list.get(i).getWater_eyes() == 1){
             count = 2;
         }
         else if(list.get(i).getItchy_nose() == 1 && list.get(i).getRunny_nose() == 0 && list.get(i).getWater_eyes() == 1){
             count = 2;
         }
         else if(list.get(i).getItchy_nose() == 0 && list.get(i).getRunny_nose() == 1 && list.get(i).getWater_eyes() == 0){
             count = 1;
         }
         else if(list.get(i).getItchy_nose() == 0 && list.get(i).getRunny_nose() == 0 && list.get(i).getWater_eyes() == 1){
             count = 1;
         }
         BarEntry value = new BarEntry(count,i);
         allergy.add(value);
     }

     BarDataSet allergyData = new BarDataSet(allergy, "Степінь аллергії");
     allergyData.setColor(Color.rgb(93, 166, 158));

     dataSets.add(allergyData);

     ArrayList<String> xAxis = new ArrayList<>();

     ArrayList<String> dateForYears = new ArrayList<>();


     for(int i = 0; i < list.size();i++) {

         String d = list.get(i).getTracker_date();
         dateForYears.add(d.substring(5));
     }


     for (String months : dateForYears) {
         Log.d("CHART_RESPONSE", "month: " + months.toString());
         xAxis.add(months);
     }

     com.github.mikephil.charting.charts.BarChart chart = getActivity().findViewById(R.id.chart);

     BarData data = new BarData(xAxis, dataSets);

     chart.setData(data);
     chart.getAxisLeft().setEnabled(false);
     chart.getAxisRight().setEnabled(false);
     chart.getXAxis().setDrawGridLines(false);
     chart.animateXY(2000, 2000);
     chart.setDescription("");
     chart.invalidate();
 }

}
