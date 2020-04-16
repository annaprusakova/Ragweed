package com.prusakova.ragweed.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.prusakova.ragweed.R;


public class ProfileActivity extends AppCompatActivity {
    ImageView    imageProfile;
    LinearLayout layoutMap;
    LinearLayout layoutMessages;
    LinearLayout layoutMedicines;
    LinearLayout layoutTracker;
    LinearLayout layoutArticles;
    LinearLayout layoutSettings;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageProfile = (ImageView) findViewById(R.id.image_profile);
        layoutMap = (LinearLayout) findViewById(R.id.map_layout);
        layoutMessages = (LinearLayout) findViewById(R.id.message_layout);
        layoutMedicines = (LinearLayout) findViewById(R.id.medicines_layout);
        layoutTracker = (LinearLayout) findViewById(R.id.tracker_layout);
        layoutArticles = (LinearLayout) findViewById(R.id.articles_layout);
        layoutSettings = (LinearLayout) findViewById(R.id.setting_layout);




    }

    public void OpenMedicinesPage(View view) {
        startActivity(new Intent(ProfileActivity.this, MedicinesActivity.class));
    }

    public void OpenSettingPage(View view){
        startActivity(new Intent(ProfileActivity.this, SettingActivity.class));
    }

}
