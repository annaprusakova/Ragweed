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


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.prusakova.ragweed.R;
import com.prusakova.ragweed.activities.AddTrackerActivity;
import com.prusakova.ragweed.api.Api;
import com.prusakova.ragweed.api.ApiClient;
import com.prusakova.ragweed.api.SharedPref;
import com.prusakova.ragweed.model.Tracker;

import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackerFragment extends Fragment {

    private Toolbar toolbar;
    private BarChart barChartYear;
    private BarChart barChartMonth;
    public Api apiInterface;
    private List<Tracker> trackList;
    private Button months;
    private Button years;
    private static final String[] MONTHS = {"", "Січень", "Лютий", "Березень", "Квітень", "Травень", "Червень",
            "Липень", "Серпень", "Вересень", "Жовтень", "Листопад", "Грудень"};
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
        barChartYear = view.findViewById(R.id.barchartYear);
        barChartMonth = view.findViewById(R.id.barchartMonth);
        months = view.findViewById(R.id.btnMonth);
        years = view.findViewById(R.id.btnYear);
        apiInterface = ApiClient.getClient().create(Api.class);


        toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_tracker);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Трекер");
        }
        userId = SharedPref.getInstance(getActivity()).LoggedInUserId();
        barChartYear.setNoDataText("Ще не має даних");
        barChartMonth.setNoDataText("Ще не має даних");

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
                        try {
                            setDataByMonth(trackList);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        barChartYear.setVisibility(View.INVISIBLE);
                        barChartMonth.setVisibility(View.VISIBLE);

                    }
                });

                years.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setDataByYear(trackList);
                        barChartYear.setVisibility(View.VISIBLE);
                        barChartMonth.setVisibility(View.INVISIBLE);
                    }
                });
                setDataByYear(trackList);
            }

            @Override
            public void onFailure(Call<List<Tracker>> call, Throwable t) {

            }
        });
    }


    private void setDataByYear(List<Tracker> list) {
        cleanChart();
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        ArrayList<BarEntry> allergy = new ArrayList<>();
        ArrayList<String> monthsList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++){
            String month = list.get(i).getTracker_date().substring(3,5);
            if(!monthsList.contains(month)) {
                monthsList.add(month);
            }
        }

        for (int i = 0; i < monthsList.size(); i++) {
            String month = monthsList.get(i);
            int count = 0;
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getTracker_date().substring(3,5).equals(month)) {
                    count += list.get(j).getDegree();
                }
            }
            BarEntry value = new BarEntry(Integer.parseInt(month), count);
            allergy.add(value);
        }

        BarDataSet allergyData = new BarDataSet(allergy, "Степінь аллергії");
        allergyData.setColor(Color.rgb(93, 166, 158));

        dataSets.add(allergyData);

        BarData data = new BarData(dataSets);
        barChartYear.setData(data);

        XAxis xl = barChartYear.getXAxis();
        xl.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return MONTHS[(int) value];
            }

        });

        barChartYear.getAxisLeft().setEnabled(false);
        barChartYear.getXAxis().setDrawGridLines(false);
        YAxis axisLeft = barChartYear.getAxisLeft();
        axisLeft.setGranularity(10f);
        xl.setGranularityEnabled(true);
        axisLeft.setAxisMinimum(0);
        barChartYear.animateXY(2000, 2000);
        barChartYear.getDescription().setEnabled(false);
    }

    private void setDataByMonth(List<Tracker> list) throws ParseException {
        cleanChart();
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        ArrayList<BarEntry> allergy = new ArrayList<>();
        ArrayList<String> dateList = new ArrayList<>();
        final ArrayList<String> labels = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM", Locale.getDefault());

        for (int i = 0; i < list.size(); i++) {
            String date = list.get(i).getTracker_date().substring(0,5);
            if(!dateList.contains(date)) {
                dateList.add(date);
            }
        }

        for (int i = 0; i < dateList.size(); i++) {
            String dateString = dateList.get(i).substring(0,5);
            Date date = new SimpleDateFormat("dd.MM").parse(dateString);
            int dayDegree = list.get(i).getDegree();
            BarEntry value = new BarEntry(i, dayDegree);
            allergy.add(value);
            labels.add(simpleDateFormat.format(date));
        }

        BarDataSet allergyDay = new BarDataSet(allergy, "Степінь аллергії");
        allergyDay.setColor(Color.rgb(93, 166, 158));

        dataSets.add(allergyDay);

        BarData dayData = new BarData(dataSets);
        XAxis xAxis = barChartMonth.getXAxis();

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int intValue = (int) value;
                String result = (labels.size() > intValue && intValue >= 0) ? labels.get(intValue) : "";
                return result.replace(" ", ".");
            }

        });
        barChartMonth.setData(dayData);
        xAxis.setDrawGridLines(false);
        YAxis axisLeft = barChartMonth.getAxisLeft();
        axisLeft.setEnabled(false);
        axisLeft.setGranularity(10f);
        xAxis.setGranularityEnabled(true);
        axisLeft.setAxisMinimum(0);
        barChartMonth.animateXY(2000, 2000);
        barChartMonth.getDescription().setEnabled(false);
        barChartMonth.invalidate();
    }

    private void cleanChart() {
        barChartMonth.invalidate();
        barChartYear.invalidate();
        barChartYear.clear();
        barChartMonth.clear();
    }
}
