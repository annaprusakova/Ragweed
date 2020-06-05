package com.prusakova.ragweed.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.prusakova.ragweed.OnBackPressed;
import com.prusakova.ragweed.R;
import com.prusakova.ragweed.activities.EditPasswordActivity;
import com.prusakova.ragweed.activities.EditProfileActivity;
import com.prusakova.ragweed.api.SharedPref;
import com.prusakova.ragweed.activities.LogInActivity;
import com.squareup.picasso.Picasso;

public class SettingFragment extends Fragment implements OnBackPressed {

    TextView textViewExit;
    TextView textViewUserName;
    private ImageView profilePhoto;
    private TextView toEditProfile;
    private TextView toEditPassword;
    AlertDialog.Builder builder;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       View view =  inflater.inflate(R.layout.fragment_setting, null);
        textViewExit = (TextView)  view.findViewById(R.id.logout_app);
        toEditProfile = view.findViewById(R.id.edit_profile);
        toEditPassword  = view.findViewById(R.id.edit_password);
        profilePhoto = view.findViewById(R.id.profileCircleImageView);
        textViewUserName = (TextView) view.findViewById(R.id.usernameTextView);
        builder = new AlertDialog.Builder(view.getContext());
        toolbar = view.findViewById(R.id.settingToolBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


        //getting logged in user name
        String loggedUsename = SharedPref.getInstance(getActivity()).LoggedInUser();
        textViewUserName.setText(loggedUsename);
        String userPhoto = SharedPref.getInstance(getActivity()).LoggedInUserPhoto();
        Picasso.with(getActivity())
                .load(userPhoto)
                .into(profilePhoto);




        textViewExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setMessage("Ви дійсно хочете вийти з профілю?")
                        .setCancelable(false)
                        .setPositiveButton("Так", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        getActivity().finish();
                                        SharedPref.getInstance(getActivity().getApplicationContext()).logout();
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
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Вихід");
                alert.show();
            }
        });
        toEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        toEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditPasswordActivity.class);
                startActivity(intent);
            }

        });



        return view;
    }


    public void onBackPressed() {
        getActivity().finishAffinity();
    }


}
