package com.prusakova.ragweed.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.prusakova.ragweed.R;
import com.prusakova.ragweed.api.Api;
import com.prusakova.ragweed.api.ApiClient;
import com.prusakova.ragweed.api.SharedPref;
import com.prusakova.ragweed.model.Location;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPointActivity extends AppCompatActivity {

    private DatePickerDialog picker;
    private   EditText pickDate;
    Button getPhoto;
    private RadioGroup point;
    private Toolbar toolbar;
    private Api apiInterface;
    private ImageView img;
    private Bitmap bitmap;
     EditText descPoint;
     String LatLng;
    private EditText locName;
    private  static final int IMAGE = 100;
    private static int RESULT_LOAD_IMAGE = 1;
    int AUTOCOMPLETE_REQUEST_CODE = 100;
    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_point);

        pickDate = findViewById(R.id.date_point);
        getPhoto = findViewById(R.id.download_photo);
        point = findViewById(R.id.point_color);
        descPoint = findViewById(R.id.description);
        locName = findViewById(R.id.loc_name);
        Places.initialize(getApplicationContext(), getString(R.string.google_api_key));
        img = findViewById(R.id.img_loc);

        locName.setFocusable(false);
        locName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY, fields)
                        .build(AddPointActivity.this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });

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


        pickDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                new DatePickerDialog(AddPointActivity.this, date,myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        getPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {

              chooseFile();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_menu, menu);
        return true;
    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 1:
                if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
                    Uri filePath = data.getData();
                    try {

                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                        img.setImageBitmap(bitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                break;
            case 100:
                if (requestCode == AUTOCOMPLETE_REQUEST_CODE && resultCode == RESULT_OK) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    locName.setText(place.getAddress());
                    LatLng = String.valueOf(place.getLatLng());
                    Log.i("add", "Place: " + place.getName() + ", " + place.getId() + ", " + place.getLatLng());
                } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                    Status status = Autocomplete.getStatusFromIntent(data);
                    Log.i("add", status.getStatusMessage());
                } else if (resultCode == RESULT_CANCELED) {
                    // The user canceled the operation.
                }
                break;
        }


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
        String myFormat = "dd MMMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        pickDate.setText(sdf.format(myCalendar.getTime()));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_save_point:


                if (TextUtils.isEmpty(pickDate.getText().toString())) {
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


    private void addData(final String key) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Збереження...");
        progressDialog.show();


        String name = locName.getText().toString();
        String lang = LatLng;
        String date = pickDate.getText().toString().trim();
        String description = descPoint.getText().toString();
        String color = "red";
        switch (point.getCheckedRadioButtonId()) {
            case R.id.red:
                color = "red";
                break;
            case R.id.yellow:
                color = "yellow";
                break;
            case R.id.blue:
                color = "blue";
                break;
        }
        Log.println(Log.INFO,"s",color);
        String picture = null;
        if (bitmap == null) {
            picture = "";
        } else {
            picture = getStringImage(bitmap);
        }



        int userId = SharedPref.getInstance(AddPointActivity.this).LoggedInUserId();

        apiInterface = ApiClient.getClient().create(Api.class);

        Call<Location> call = apiInterface.insertLocation(key,name, date, color,description,userId,lang,picture);

        call.enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {

                progressDialog.dismiss();

                Log.i(AddPointActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    finish();
                } else {
                    Toast.makeText(AddPointActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AddPointActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }






}
