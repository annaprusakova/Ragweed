package com.prusakova.ragweed.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.google.android.material.chip.Chip;
import com.prusakova.ragweed.R;
import com.prusakova.ragweed.api.Api;
import com.prusakova.ragweed.api.ApiClient;
import com.prusakova.ragweed.api.SharedPref;

import com.prusakova.ragweed.model.Tracker;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTrackerActivity extends AppCompatActivity  {

    private DatePickerDialog picker;
    private Chip itchyNose;
    private Chip runnyNose;
    private Chip waterEyes;
    private Chip eyeRedness;
    private Toolbar toolbar;
    private EditText dateTracker;
    Calendar myCalendar = Calendar.getInstance();

    private Api apiInterface;
    private int tracker_id;
    private String tracker_date;
    private Boolean itchy_nose, runny_mose, water_eyes, eye_redness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tracker);

        itchyNose = findViewById(R.id.itchy_nose);
        runnyNose = findViewById(R.id.runny_nose);
        waterEyes = findViewById(R.id.water_eyes);
        eyeRedness = findViewById(R.id.eye_redness);
        dateTracker = findViewById(R.id.date);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        toolbar.setNavigationIcon(R.drawable.ic_chevron_left);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        dateTracker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                 new DatePickerDialog(AddTrackerActivity.this, date,myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });





    }




    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setDate();
        }

    };


    private void setDate() {
        String myFormat = "dd-MM-yyyy";

        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateTracker.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_save:


                    if (TextUtils.isEmpty(dateTracker.getText().toString())) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                        alertDialog.setMessage("Будь ласка, заповніть поля!");
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    } else {

                        addData("insert");


                    }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_data_tracker, menu);
        return true;
    }


    private void addData(final String key){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Збереження...");
        progressDialog.show();

        String day =  dateTracker.getText().toString().trim();

        Log.e("date",day);
        int itchy_nose = 0;
        if(itchyNose.isChecked()){
            itchy_nose = 1;
        }
        int  runny_nose = 0;
        if(runnyNose.isChecked()){
            runny_nose = 1;
        }
        int  water_eyes = 0;
        if(waterEyes.isChecked()){
            water_eyes = 1;
        }
        int eye_redness = 0;
        if(eyeRedness.isChecked()){
            eye_redness = 1;
        }

        int userId = SharedPref.getInstance(AddTrackerActivity.this).LoggedInUserId();

        apiInterface = ApiClient.getClient().create(Api.class);

        Call<Tracker> call = apiInterface.insertTracker(key,day, itchy_nose, water_eyes,runny_nose,userId,eye_redness);

        call.enqueue(new Callback<Tracker>() {
            @Override
            public void onResponse(Call<Tracker> call, Response<Tracker> response) {

                progressDialog.dismiss();

                Log.i(AddTrackerActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                    Toast.makeText(AddTrackerActivity.this, "Дані збережено", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddTrackerActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Tracker> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AddTrackerActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });




    }
}
