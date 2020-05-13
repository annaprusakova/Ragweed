package com.prusakova.ragweed.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import com.prusakova.ragweed.MedicineAdapter;
import com.prusakova.ragweed.R;
import com.prusakova.ragweed.api.Api;
import com.prusakova.ragweed.api.ApiClient;
import com.prusakova.ragweed.model.Medicine;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MedicinesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MedicineAdapter adapter;
    private List<Medicine> medList;
    Api apiInterface;
    MedicineAdapter.RecyclerViewClickListener listener;

    ProgressBar progressBar;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines);

        apiInterface = ApiClient.getClient().create(Api.class);

        progressBar = findViewById(R.id.progress_med);
        recyclerView = findViewById(R.id.recyclerView_med);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


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


        listener = new MedicineAdapter.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {

                Intent intent = new Intent(MedicinesActivity.this, IteamMedActivity.class);
                intent.putExtra("id_med", medList.get(position).getId_med());
                intent.putExtra("med_name", medList.get(position).getMed_name());
                intent.putExtra("photo_med", medList.get(position).getPhoto_med());
                intent.putExtra("cost", medList.get(position).getCost());
                intent.putExtra("active_substance", medList.get(position).getActive_substance());

                startActivity(intent);

            }

        };


        getMed("medicines", "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    public void getMed(String type, String key){
        apiInterface = ApiClient.getClient().create(Api.class);

        Call<List<Medicine>> call = apiInterface.getMed(type,key);
        call.enqueue(new Callback<List<Medicine>>() {
            @Override
            public void onResponse(Call<List<Medicine>> call, Response<List<Medicine>> response) {
                progressBar.setVisibility(View.GONE);
                medList = response.body();
                adapter = new MedicineAdapter(medList,MedicinesActivity.this, listener);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Medicine>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MedicinesActivity.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


}









