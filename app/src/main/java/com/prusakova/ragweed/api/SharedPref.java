package com.prusakova.ragweed.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.prusakova.ragweed.activities.MainActivity;

import java.util.HashMap;

public class SharedPref {


    //Storage File
    public static final String SHARED_PREF_NAME = "api";
    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_ID = "id";
    public static final String USER_PHOTO = "user_photo";
    public static final String LOGGED_IN_PREF = "logged_in_status";

    public static SharedPref mInstance;

    public static Context mCtx;


    public SharedPref(Context context) {
        mCtx = context;
    }


    public static synchronized SharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPref(context);
        }
        return mInstance;
    }


    //method to store user data
    public void storeUserName(String names) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME, names);
        editor.commit();
    }


    //method to store user data
    public void storeUserId(int id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_ID, id);
        editor.commit();
    }

    public void storeUserPhoto(String photo) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_PHOTO, photo);
        editor.apply();
    }
    public void storeUserEmail(String email) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_EMAIL, email);
        editor.apply();
    }
    public void storeUserPassword(String password) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_PASSWORD, password);
        editor.apply();
    }

    //check if user is logged in
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_NAME, null) != null;
    }



    //find logged in user
    public String LoggedInUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_NAME, null);

    }

    public int LoggedInUserId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(USER_ID,0);

    }

    public String  LoggedInUserPhoto() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_PHOTO,null);

    }

    public String  LoggedInUserEmail() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_EMAIL,null);

    }

    public String  LoggedInUserPassword() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_PASSWORD,null);

    }

    //Logout user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, MainActivity.class));
    }




}

