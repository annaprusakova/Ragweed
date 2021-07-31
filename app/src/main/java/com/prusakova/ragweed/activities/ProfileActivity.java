package com.prusakova.ragweed.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.prusakova.ragweed.fragments.ArticleFragment;
import com.prusakova.ragweed.R;
import com.prusakova.ragweed.api.SharedPref;
import com.prusakova.ragweed.fragments.MapOptionFragment;
import com.prusakova.ragweed.fragments.MedicinesFragment;
import com.prusakova.ragweed.fragments.SettingFragment;
import com.prusakova.ragweed.fragments.TrackerFragment;

import java.util.List;


public class ProfileActivity extends AppCompatActivity  implements BottomNavigationView.OnNavigationItemSelectedListener{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        loadFragment(new SettingFragment());

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        navigation.setSelectedItemId(R.id.navigation_setting);

        //check if user is logged in
        if (!SharedPref.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();

        }


    }


    @Override
    public void onBackPressed() {
        tellFragments();

    }

    private void tellFragments(){
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for(Fragment f : fragments){
            if(f instanceof SettingFragment) {
                ((SettingFragment) f).onBackPressed();
            } else {
                f.getFragmentManager().popBackStack();
            }
        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_map:
               fragment = new MapOptionFragment();
                break;

            case R.id.navigation_messages:
                 fragment = new MedicinesFragment();
                break;

            case R.id.navigation_tracker:
                fragment = new TrackerFragment();
                break;

            case R.id.navigation_articles:
                fragment = new ArticleFragment();
                break;
            case R.id.navigation_setting:
                fragment = new SettingFragment();
                break;
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }



}
