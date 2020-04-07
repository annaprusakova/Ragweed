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
    LinearLayout layoutLogOut;
    AlertDialog.Builder builder;



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
        layoutLogOut = (LinearLayout) findViewById(R.id.logout_layout);
        builder = new AlertDialog.Builder(this);

        layoutLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Uncomment the below code to Set the message and title from the strings.xml file
                builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                //Setting message manually and performing action on button click
                builder.setMessage("Ви дійсно хочете вийти з профілю?")
                        .setCancelable(false)
                        .setPositiveButton("Так", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }, Snackbar.LENGTH_LONG);
                            }
                        })
                        .setNegativeButton("Ні", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Вихід");
                alert.show();
            }
        });

    }

    public void OpenMedicinesPage(View view) {
        startActivity(new Intent(ProfileActivity.this, MedicinesActivity.class));
    }

}
