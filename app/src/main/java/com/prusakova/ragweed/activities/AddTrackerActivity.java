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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.chip.Chip;
import com.prusakova.ragweed.MedicineAdapter;
import com.prusakova.ragweed.R;
import com.prusakova.ragweed.api.Api;
import com.prusakova.ragweed.api.ApiClient;
import com.prusakova.ragweed.fragments.TrackerFragment;
import com.prusakova.ragweed.model.Medicine;
import com.prusakova.ragweed.model.Tracker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTrackerActivity extends AppCompatActivity {

    private Spinner  medSpinner;
    private Spinner mapSpinner;
    private DatePickerDialog picker;
    private Chip itchyNose;
    private Chip runnyNose;
    private Chip waterEyes;
    private Toolbar toolbar;
    private EditText dateTracker;
    Calendar myCalendar = Calendar.getInstance();

    private Api apiInterface;
    private int mMed = 0;
    private List<Medicine> medList;
    private int tracker_id, med_id,map_id;
    private String tracker_date;
    private Boolean itchy_nose, runny_mose, water_eyes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tracker);

        medSpinner = findViewById(R.id.med_tracker);
        mapSpinner = findViewById(R.id.map_tracker);
        itchyNose = findViewById(R.id.itchy_nose);
        runnyNose = findViewById(R.id.runny_nose);
        waterEyes = findViewById(R.id.runny_nose);
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



      getMed("medicines","");

        medSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               mMed = medList.get(position).getId_med();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
            setBirth();
        }

    };


    private void setBirth() {
        String myFormat = "dd MMMM yyyy"; //In which you need put here
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

    public void getMed(String type, String key){
        //making api call
        apiInterface = ApiClient.getClient().create(Api.class);

        Call<List<Medicine>> call = apiInterface.getMed(type,key);

        call.enqueue(new Callback<List<Medicine>>() {
            @Override
            public void onResponse(Call<List<Medicine>> call, Response<List<Medicine>> response) {
                medList = response.body();
                String[] s = new String[medList.size()];
                for(int i = 0; i < medList.size();i++){
                    s[i]=medList.get(i).getMed_name();
                    final ArrayAdapter a = new ArrayAdapter(AddTrackerActivity.this, android.R.layout.simple_spinner_item, s);
                    a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    medSpinner.setAdapter(a);
                }




            }
            @Override
            public void onFailure(Call<List<Medicine>> call, Throwable t) {
                Toast.makeText(AddTrackerActivity.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }

            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_data_tracker, menu);
        return true;
    }


    private void addData(final String key){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        String date = dateTracker.getText().toString().trim();
        int med = mMed;
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


        apiInterface = ApiClient.getClient().create(Api.class);

        Call<Tracker> call = apiInterface.insertTracker(key,date, med, itchy_nose, water_eyes,runny_nose);

        call.enqueue(new Callback<Tracker>() {
            @Override
            public void onResponse(Call<Tracker> call, Response<Tracker> response) {

                progressDialog.dismiss();

                Log.i(AddTrackerActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    finish();
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
