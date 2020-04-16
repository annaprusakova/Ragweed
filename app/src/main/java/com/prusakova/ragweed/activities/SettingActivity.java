package com.prusakova.ragweed.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.prusakova.ragweed.R;

public class SettingActivity extends AppCompatActivity {

    TextView textViewExit;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        textViewExit = (TextView) findViewById(R.id.logout_app);

        builder = new AlertDialog.Builder(this);



        textViewExit.setOnClickListener(new View.OnClickListener() {
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
                                        Intent launchNextActivity = new Intent(SettingActivity.this, LogInActivity.class);
                                        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        startActivity(launchNextActivity);

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
}
