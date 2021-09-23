package com.prusakova.ragweed.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.prusakova.ragweed.R;
import com.prusakova.ragweed.activities.AddTrackerActivity;
import com.prusakova.ragweed.api.Api;
import com.prusakova.ragweed.api.ApiClient;
import com.prusakova.ragweed.api.SharedPref;
import com.prusakova.ragweed.model.Tracker;

import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackerFragment extends Fragment {

    private Toolbar toolbar;
    private BarChart chart;
    private LineChart lineChart;
    public Api apiInterface;
    private List<Tracker> trackList;
    private Button months;
    private Button years;
    private Switch BarChartSwitch, LineChartSwitch;
    final String Your_Fragment_TAG = "TrackerFragment";


    int userId = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracker, container, false);
        chart = view.findViewById(R.id.chart);
        lineChart = view.findViewById(R.id.lineChart);
        months = view.findViewById(R.id.btnMonth);
        years = view.findViewById(R.id.btnYear);
        BarChartSwitch = view.findViewById(R.id.BarChartSwitch);
        LineChartSwitch = view.findViewById(R.id.LineChartSwitch);
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);
        apiInterface = ApiClient.getClient().create(Api.class);


        toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_tracker);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Трекер");
        }
        userId = SharedPref.getInstance(getActivity()).LoggedInUserId();
        chart.setNoDataText("Ще не має даних");

        if (BarChartSwitch.isChecked()) {
            chart.setVisibility(View.VISIBLE);
        } else {
            chart.setVisibility(View.INVISIBLE);
        }
        if (LineChartSwitch.isChecked()) {
            lineChart.setVisibility(View.VISIBLE);
        } else {
            lineChart.setVisibility(View.VISIBLE);
        }


        getData(userId);


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tracker, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent add = new Intent(getActivity(), AddTrackerActivity.class);
                startActivity(add);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void getData(int key) {
        Call<List<Tracker>> call = apiInterface.getTracker(key);
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
            count = list.get(i).getDegree();
            BarEntry value = new BarEntry(count, i);
            allergy.add(value);
        }

        BarDataSet allergyData = new BarDataSet(allergy, "Степінь аллергії");
        allergyData.setColor(Color.rgb(93, 166, 158));

        dataSets.add(allergyData);

        ArrayList<String> xAxis = new ArrayList<>();

        ArrayList<String> dateForYears = new ArrayList<>();


        for (int i = 0; i < list.size(); i++) {
            String d = list.get(i).getTracker_date();
            String month = "Січень";
            switch (d.substring(3, 5)) {
                case "01":
                    month = "Січень";
                    break;
                case "02":
                    month = "Лютий";
                    break;
                case "03":
                    month = "Березень";
                    break;
                case "04":
                    month = "Квітень";
                    break;
                case "05":
                    month = "Травень";
                    break;
                case "06":
                    month = "Червень";
                    break;
                case "07":
                    month = "Липень";
                    break;
                case "08":
                    month = "Серпень";
                    break;
                case "09":
                    month = "Вересень";
                    break;
                case "10":
                    month = "Жовтень";
                    break;
                case "11":
                    month = "Листопад";
                    break;
                case "12":
                    month = "Грудень";
                    break;
            }

            dateForYears.add(month + " " + d.substring(6));
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

    private void setDataMonth(List<Tracker> list) {
        ArrayList<BarDataSet> dataSets = new ArrayList<>();


        ArrayList<BarEntry> allergy = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            count = list.get(i).getDegree();
            BarEntry value = new BarEntry(count, i);
            allergy.add(value);
        }

        BarDataSet allergyData = new BarDataSet(allergy, "Степінь аллергії");
        allergyData.setColor(Color.rgb(93, 166, 158));

        dataSets.add(allergyData);

        ArrayList<String> xAxis = new ArrayList<>();

        ArrayList<String> dateForYears = new ArrayList<>();


        for (int i = 0; i < list.size(); i++) {

            String d = list.get(i).getTracker_date();
            String month = "Січень";
            switch (d.substring(3, 5)) {
                case "01":
                    month = "Січеня";
                    break;
                case "02":
                    month = "Лютого";
                    break;
                case "03":
                    month = "Березня";
                    break;
                case "04":
                    month = "Квітня";
                    break;
                case "05":
                    month = "Травня";
                    break;
                case "06":
                    month = "Червня";
                    break;
                case "07":
                    month = "Липня";
                    break;
                case "08":
                    month = "Серпня";
                    break;
                case "09":
                    month = "Вересня";
                    break;
                case "10":
                    month = "Жовтня";
                    break;
                case "11":
                    month = "Листопада";
                    break;
                case "12":
                    month = "Грудня";
                    break;
            }

            dateForYears.add(d.substring(0, 2) + " " + month);
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
