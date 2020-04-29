package com.prusakova.ragweed.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.prusakova.ragweed.R;
import com.prusakova.ragweed.activities.LogInActivity;

public class SettingFragment extends Fragment {

    TextView textViewExit;

    AlertDialog.Builder builder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
       View view =  inflater.inflate(R.layout.setting_fragment, null);
        textViewExit = (TextView)  view.findViewById(R.id.logout_app);
        builder = new AlertDialog.Builder(view.getContext());

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
                                        getActivity().finish();
                                        Intent launchNextActivity = new Intent(getActivity(), LogInActivity.class);
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

        return view;
    }


}
