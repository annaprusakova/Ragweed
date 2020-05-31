package com.prusakova.ragweed.fragments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.prusakova.ragweed.MedicineAdapter;
import com.prusakova.ragweed.OnBackPressed;
import com.prusakova.ragweed.R;
import com.prusakova.ragweed.activities.IteamMedActivity;
import com.prusakova.ragweed.api.Api;
import com.prusakova.ragweed.api.ApiClient;
import com.prusakova.ragweed.model.Medicine;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MedicinesFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MedicineAdapter adapter;
    private List<Medicine> medList;
    Api apiInterface;
    MedicineAdapter.RecyclerViewClickListener listener;

    ProgressBar progressBar;

    private Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_medicines,container,false);

        apiInterface = ApiClient.getClient().create(Api.class);

        progressBar = view.findViewById(R.id.progress_med);
        recyclerView = view.findViewById(R.id.recyclerView_med);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if(((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Медикаменти");
        }



        listener = new MedicineAdapter.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {

                Intent intent = new Intent(getActivity(), IteamMedActivity.class);
                intent.putExtra("id_med", medList.get(position).getId_med());
                intent.putExtra("med_name", medList.get(position).getMed_name());
                intent.putExtra("photo_med", medList.get(position).getPhoto_med());
                intent.putExtra("cost", medList.get(position).getCost());
                intent.putExtra("active_substance", medList.get(position).getActive_substance());

                startActivity(intent);

            }

        };


        getMed("medicines", "");
        return view;
    }




    public void getMed(String type, String key){
        apiInterface = ApiClient.getClient().create(Api.class);

        Call<List<Medicine>> call = apiInterface.getMed(type,key);
        call.enqueue(new Callback<List<Medicine>>() {
            @Override
            public void onResponse(Call<List<Medicine>> call, Response<List<Medicine>> response) {
                progressBar.setVisibility(View.GONE);
                medList = response.body();
                adapter = new MedicineAdapter(medList,getContext(), listener);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Medicine>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                getMed("medicines", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getMed("medicines", newText);
                return false;
            }

        });
    }

}









