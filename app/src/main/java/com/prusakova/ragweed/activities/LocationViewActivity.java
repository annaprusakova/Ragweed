package com.prusakova.ragweed.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prusakova.ragweed.LocationAdapter;
import com.prusakova.ragweed.R;
import com.prusakova.ragweed.api.Api;
import com.prusakova.ragweed.api.ApiClient;
import com.prusakova.ragweed.api.SharedPref;
import com.prusakova.ragweed.model.Location;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    LocationAdapter.RecyclerViewClickListener listener;
    private List<Location> locationList;
    private LocationAdapter locationAdapter;
    ProgressBar progressBar;
    private Toolbar toolbar;
    private Api apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_loc);

        apiInterface= ApiClient.getClient().create(Api.class);
        progressBar = findViewById(R.id.prograss_loc);
        recyclerView = findViewById(R.id.recyclerView_loc);
        layoutManager = new LinearLayoutManager(LocationViewActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu);
        int userId = SharedPref.getInstance(LocationViewActivity.this).LoggedInUserId();


        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        toolbar.setNavigationIcon(R.drawable.ic_chevron_left);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fetchLocation(userId);
    }

    public void fetchLocation(int key){

        apiInterface = ApiClient.getClient().create(Api.class);

        Call<List<Location>> call = apiInterface.getLocationByUser(key);
        call.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                progressBar.setVisibility(View.GONE);
                locationList = response.body();
                locationAdapter = new LocationAdapter(locationList,LocationViewActivity.this, listener);
                recyclerView.setAdapter(locationAdapter);
                locationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LocationViewActivity.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

}
