package com.prusakova.ragweed.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.prusakova.ragweed.model.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final  String DATABASE_NAME = "ragweed1.db";
    private static final  String DATABASE_PATH = "/data/data/com.prusakova.ragweed/databases/";

    private static final  String TABLE_USERS = "user";
    private static final  String COLUMN_USER_ID = "user_id";
    private static final  String COLUMN_USER_NAME = "username";
    private static final  String COLUMN_USER_EMAIL = "email";
    private static final  String COLUMN_USER_PASSWORD = "password";
    private static final  String COLUMN_USER_TRACKER_ID = "tracker_id";
    private static final  String COL_USER_MESSAGE_ID = "message_id";

    //SQL for creating users table
    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
            + " ( "
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_NAME + " TEXT, "
            + COLUMN_USER_EMAIL + " TEXT, "
            + COLUMN_USER_PASSWORD + " TEXT"
            + " ) ";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }


    public long addUser(String username, String email, String password) {
        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();
        //create content values to insert
        ContentValues values = new ContentValues();
        //Put username in  @values
        values.put(COLUMN_USER_NAME, username);
        //Put email in  @values
        values.put(COLUMN_USER_EMAIL, email);
        //Put password in  @values
        values.put(COLUMN_USER_PASSWORD, password);
        // insert row
        long res = db.insert(TABLE_USERS,null, values);
        db.close();
        return res;
    }


    public boolean Authenticate(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{COLUMN_USER_ID, COLUMN_USER_NAME, COLUMN_USER_EMAIL, COLUMN_USER_PASSWORD},//Selecting columns want to query
                COLUMN_USER_EMAIL + "=?" + " and " + COLUMN_USER_PASSWORD + "=?",
                new String[]{email,password},//Where clause
                null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if(count > 0)
            return true;
        else
            return false;

    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{COLUMN_USER_ID, COLUMN_USER_NAME, COLUMN_USER_EMAIL, COLUMN_USER_PASSWORD},//Selecting columns want to query
                COLUMN_USER_EMAIL + "=?",
                new String[]{email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            //if cursor has value then in user database there is user associated with this given email so return true
            return true;
        }
        //if email does not exist return false
        return false;
    }

}
