package com.prusakova.ragweed.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.prusakova.ragweed.Api;
import com.prusakova.ragweed.ApiClient;
import com.prusakova.ragweed.R;
import com.prusakova.ragweed.model.User;
import com.prusakova.ragweed.sql.DatabaseHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText TextUserName;
    private EditText TextEmail;
    private EditText TextPassword;
    private EditText TextPasswordConfirm;
    private  FloatingActionButton ButtonSignUp;
    private TextView TextViewLogIn;
    final String registerUrl = "http://192.168.1.6/api/register.php";

    DatabaseHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_sign_up);


        sqliteHelper = new DatabaseHelper(this);

        TextUserName = (EditText) findViewById(R.id.edittext_username);
        TextEmail = (EditText) findViewById(R.id.edittext_email_signup);
        TextPassword = (EditText) findViewById(R.id.edittext_password_signup);
        TextPasswordConfirm = (EditText) findViewById(R.id.edittext_password_confirm_signup);
        ButtonSignUp = (FloatingActionButton) findViewById(R.id.button_signup);
        TextViewLogIn = (TextView) findViewById(R.id.textview_signin);




        ButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUserData();

            }

        });

        TextViewLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LogInIntent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(LogInIntent);
            }
        });

    }




    private void validateUserData() {

        //find values
        final String reg_name =  TextUserName.getText().toString();
        final String reg_email = TextEmail.getText().toString();
        final String reg_password =  TextPassword.getText().toString();
        final String reg_cpassword = TextPasswordConfirm.getText().toString();


//        checking if username is empty
        if (TextUtils.isEmpty(reg_name)) {
            TextUserName.setError("Please enter username");
            TextUserName.requestFocus();
            return;
        }
        //checking if email is empty
        if (TextUtils.isEmpty(reg_email)) {
            TextEmail.setError("Please enter email");
            TextEmail.requestFocus();
            return;
        }
        //checking if password is empty
        if (TextUtils.isEmpty(reg_password)) {
           TextPassword.setError("Please enter password");
            TextPassword.requestFocus();
            return;
        }
        //validating email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(reg_email).matches()) {
            TextEmail.setError("Enter a valid email");
            TextEmail.requestFocus();
            return;
        }
        //checking if password matches
        if (!reg_password.equals(reg_cpassword)) {
            TextPassword.setError("Password Does not Match");
            TextPassword.requestFocus();
            return;
        }

        //After Validating we register User
        registerUser(reg_name,reg_email,reg_password);

    }

    private void registerUser(String user_name, String user_mail, String user_pass) {


        final String reg_username = TextUserName.getText().toString();
        final String reg_email = TextEmail.getText().toString();
        final String reg_password = TextPassword.getText().toString();

        //call retrofit
        //making api call
        Api api = ApiClient.getClient().create(Api.class);
        Call<User> login = api.register(user_name, user_mail, user_pass);

        login.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.body().getIsSuccess() == 1) {
                    //get username
                    String user = response.body().getName();
                    Toast.makeText(SignUpActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                } else {
                    Toast.makeText(SignUpActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }

            }


            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }



}
