package com.prusakova.ragweed.activities;

import android.app.AlertDialog;
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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prusakova.ragweed.MapsActivity;
import com.prusakova.ragweed.MedicineAdapter;
import com.prusakova.ragweed.R;
import com.prusakova.ragweed.api.Api;
import com.prusakova.ragweed.api.ApiClient;
import com.prusakova.ragweed.api.SharedPref;
import com.prusakova.ragweed.model.Medicine;
import com.prusakova.ragweed.model.User;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private EditText UserName;
    private EditText tEmail;
    private Spinner Gender;
    private Bitmap bitmap;
    private Toolbar toolbar;
    private ImageView Picture;
    Api apiInterface;
    private List<User> listUser;
    private FloatingActionButton fabChoosePic;
    private String user, email, photo;
    private int gender;
    private int mGender = 0;
    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.edit_profile);
        UserName = findViewById(R.id.username);
        tEmail = findViewById(R.id.email);
        Gender = findViewById(R.id.gender);
        fabChoosePic = findViewById(R.id.fabChoosePic);
        Picture = findViewById(R.id.profilephoto);
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

        setupSpinner();

        int id = SharedPref.getInstance(EditProfileActivity.this).LoggedInUserId();

     getUser(id);

     fabChoosePic.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             chooseFile();
         }
     });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_save:


                if (TextUtils.isEmpty(UserName.getText().toString()) && TextUtils.isEmpty(tEmail.getText().toString())) {
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
                    int id = SharedPref.getInstance(EditProfileActivity.this).LoggedInUserId();
                   updateData("update", id);


                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                Picture.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
        return true;
    }

    private void setupSpinner(){
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_gender_options, android.R.layout.simple_spinner_item);
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        Gender.setAdapter(genderSpinnerAdapter);

        Gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = GENDER_MALE;
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = GENDER_FEMALE;
                    } else {
                        mGender = 0;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = 0;
            }
        });
    }

    public void setData(String name, String email, String photo,int gender){
        UserName.setText(name);
        Log.i("name:", name);
        tEmail.setText(email);
        Picasso.with(EditProfileActivity.this)
                .load(photo)
                .into(Picture);
        SharedPref.getInstance(EditProfileActivity.this).storeUserPhoto(photo);
        Log.d("photo up", "up:" + SharedPref.getInstance(EditProfileActivity.this).LoggedInUserPhoto());
        switch (gender) {
            case GENDER_MALE:
               Gender.setSelection(1);
                break;
            case GENDER_FEMALE:
                Gender.setSelection(2);
                break;
            default:
                Gender.setSelection(0);
                break;
        }
    }

    public void getUser(int id){
        apiInterface = ApiClient.getClient().create(Api.class);
        Call<List<User>> call = apiInterface.getUser(id);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    listUser = response.body();
                    for(int i = 0; i < listUser.size();i++){
                        user = listUser.get(i).getName();
                        email = listUser.get(i).getEmail();
                        photo = listUser.get(i).getUser_photo();
                        gender = listUser.get(i).getUser_gender();
                    }
                    setData(user,email,photo,gender);



            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateData(final String key, final int id) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Оновлення...");
        progressDialog.show();


        String name = UserName.getText().toString().trim();
        String email =  tEmail.getText().toString().trim();
        int gender = mGender;
        String picture = null;
        if (bitmap == null) {
            picture = "";
        } else {
            picture = getStringImage(bitmap);
        }

        apiInterface = ApiClient.getClient().create(Api.class);

        Call<User> call = apiInterface.updateUser(key, id, name, email, gender, picture);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                progressDialog.dismiss();

                Log.i(EditProfileActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")){
                    Toast.makeText(EditProfileActivity.this, "Збереженно", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditProfileActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
