package com.prusakova.ragweed.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.prusakova.ragweed.R;
import com.prusakova.ragweed.activities.AddTrackerActivity;
import com.prusakova.ragweed.activities.ArticleActivity;
import com.prusakova.ragweed.activities.MedicinesActivity;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class TrackerFragment  extends Fragment {
    private Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracker, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_tracker);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
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
            case R.id.action_med:
                Intent med = new Intent(getActivity(), MedicinesActivity.class);
                startActivity(med);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
