package com.prusakova.ragweed.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.prusakova.ragweed.R;
import com.prusakova.ragweed.api.Api;
import com.prusakova.ragweed.api.ApiClient;
import com.prusakova.ragweed.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutLocationActivity  extends AppCompatActivity {

    TextView addressLoc;
    TextView typeLoc;
    TextView dateLoc;
    ImageView photoLoc;
    TextView descLoc;
    TextView userName;
    ImageView userPhoto;

    private Toolbar toolbar;
    private Api apiInterface;
    private List<User> userList;

    private int location_id;
    private String loc_name,loc_point,loc_description,loc_date,loc_photo, user_name,user_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.about_location_activity);

        addressLoc = findViewById(R.id.titleAddress);
        typeLoc = findViewById(R.id.type);
        dateLoc = findViewById(R.id.dateLoc);
        photoLoc = findViewById(R.id.photoLoc);
        descLoc = findViewById(R.id.descriptionLoc);
        userName = findViewById(R.id.name_user);
        userPhoto = findViewById(R.id.image_user);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        toolbar.setNavigationIcon(R.drawable.ic_arrow_left);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        location_id = intent.getIntExtra("location_id",0);
        loc_name = intent.getStringExtra("loc_name");
        loc_point = intent.getStringExtra("loc_point");
        loc_description = intent.getStringExtra("loc_description");
        loc_date = intent.getStringExtra("loc_date");
        loc_photo = intent.getStringExtra("loc_photo");

        fetchLocation(location_id);



    }


    private void setDataFromIntentExtra() {

        if(location_id != 0){

            addressLoc.setText("Адреса: "+loc_name);
            typeLoc.setText(loc_point);
            descLoc.setText(loc_description);
            dateLoc.setText(loc_date);
            Picasso.with(this).load(loc_photo).into(photoLoc);
            userName.setText(user_name);
            Log.i("u1",user_name);
            Picasso.with(this).load(user_photo).into(userPhoto);

        }

    }

    public void fetchLocation(int key){

        apiInterface = ApiClient.getClient().create(Api.class);

        Call<List<User>> call = apiInterface.getUserLoc(key);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                userList = response.body();
                for (int i  = 0; i < userList.size();i++){
                    user_name = userList.get(i).getName();
                    Log.i("u",user_name);
                    user_photo = userList.get(i).getUser_photo();
                    setDataFromIntentExtra();
                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(AboutLocationActivity.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
