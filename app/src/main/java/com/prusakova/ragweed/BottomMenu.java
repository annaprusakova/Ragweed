package com.prusakova.ragweed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.prusakova.ragweed.fragments.SettingFragment;

public class BottomMenu extends AppCompatActivity {

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_menu);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }



    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    toolbar.setTitle("Карта");
                    return true;
                case R.id.navigation_messages:
                    toolbar.setTitle("Повідомлення");
                    return true;
                case R.id.navigation_tracker:
                    toolbar.setTitle("Трекер");
                    return true;
                case R.id.navigation_articles:
                    toolbar.setTitle("Статті");
                    return true;
                case R.id.navigation_setting:
                    Intent a = new Intent(BottomMenu.this, SettingFragment.class);
                    startActivity(a);
                    break;

            }
            return false;
        }
    };

}
