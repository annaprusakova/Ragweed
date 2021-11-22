package com.prusakova.ragweed.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.prusakova.ragweed.R;
import com.prusakova.ragweed.api.Api;
import com.prusakova.ragweed.api.ApiClient;
import com.prusakova.ragweed.model.Location;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapStatisticActivity  extends AppCompatActivity {

    private BarChart barChart;
    private Toolbar toolbar;
    public Api apiInterface;
    private List<Location> locationList;
    ArrayList<IBarDataSet> dataSets = new ArrayList<>();
    private static final String[] REGIONS = {"Крим", "Вінницька область", "Волинська область", "Дніпропетровська область",
            "Донецька область", "Житомирська область", "Закарпатська область", "Запорізька область", "Івано-Франківська область",
            "Київська область", "Кіровоградська область", "Луганська область", "Львівська область", "Миколаївська область","Одеська область",
    "Полтавська область", "Рівненська область", "Сумська область", "Тернопільська область", "Харківська область", "Херсонська область", "Хмельницька область",
    "Черкаська область", "Чернівецька область", "Чернігівська область"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_map_statistic);

        barChart = findViewById(R.id.barchartMap);
        apiInterface = ApiClient.getClient().create(Api.class);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        toolbar.setNavigationIcon(R.drawable.ic_chevron_left);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        barChart.setNoDataText("Ще не має даних");

        barChart.invalidate();
        getData();

    }

    private void getData() {
        apiInterface = ApiClient.getClient().create(Api.class);

        Call<List<Location>> call = apiInterface.getLocation();
        call.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                locationList = response.body();

                setDataByRegion(locationList);
            }


            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {

            }
        });
    }

    private void setDataByRegion(List<Location> list) {
        cleanChart();

        ArrayList<BarEntry> regionBar = new ArrayList<>();
        ArrayList<String> regionList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++){
            String[] reg = list.get(i).getLoc_name().split(",");
            String region = reg[reg.length - 3].trim();
            if(!regionList.contains(region)) {
                regionList.add(region);
                Log.d("REG", "region 1:" + region);
            }
        }

        for (int i = 0; i < regionList.size(); i++) {
            String reg = regionList.get(i);
            int count = 0;
            int num = 0;
            for (int j = 0; j < list.size(); j++) {
                String[] regs = list.get(i).getLoc_name().split(",");
                Log.d("REG", "regions:" + regs[3]);
                String region = regs[regs.length - 3].trim();
                for (int n = 0; n < REGIONS.length; n++) {
                    if(REGIONS[n].equals(region)) {
                        num = n;
                    }
                }
                if (region.equals(reg)) {
                    count += 1;
                }
            }
            Log.d("RESPONSE", "result:" + reg + " " + " n: " + num + " c: " + count);
            BarEntry value = new BarEntry(num, count);
            regionBar.add(value);
        }


        BarDataSet regionData = new BarDataSet(regionBar, "Кількість уражених ділянок");
        regionData.setColor(Color.rgb(93, 166, 158));

        dataSets.add(regionData);

        BarData data = new BarData(dataSets);

        XAxis xl = barChart.getXAxis();
        xl.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return REGIONS[(int) value];
            }

        });

        barChart.setData(data);
        barChart.getAxisLeft().setEnabled(false);
        barChart.getXAxis().setDrawGridLines(false);
        xl.setGranularityEnabled(true);
        barChart.animateXY(2000, 2000);
        barChart.getDescription().setEnabled(false); new ArrayList<>();


    }

    private void cleanChart() {
        dataSets.clear();
        barChart.invalidate();
        barChart.clear();
    }

}
