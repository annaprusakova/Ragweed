package com.prusakova.ragweed.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prusakova.ragweed.R;
import com.prusakova.ragweed.api.Api;
import com.prusakova.ragweed.api.ApiClient;
import com.prusakova.ragweed.api.SharedPref;
import com.prusakova.ragweed.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {

    TextView TextViewLogIn;
    EditText TextEmail;
    FloatingActionButton ButtonFPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_forget_password);

        TextViewLogIn = (TextView) findViewById(R.id.textview_signin_fpassword);
        TextEmail = (EditText) findViewById(R.id.edittext_email_fp);
        ButtonFPassword = (FloatingActionButton) findViewById(R.id.button_fpassword);

        //go to login
        TextViewLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(ForgetPasswordActivity.this, LogInActivity.class);
                startActivity(registerIntent);
            }
        });


        ButtonFPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkDataEntered();
            }
        });
    }

//    //check correct email
//    boolean isEmail (EditText text) {
//        CharSequence email = text.getText().toString();
//        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
//    }


    // check data
    void checkDataEntered(){
//        if (isEmail(TextEmail) == false) {
//            TextEmail.setError("Будь ласка, введіть коректну пошту");
//        }'
        final String forgot_email = TextEmail.getText().toString();

        if (TextUtils.isEmpty(forgot_email)) {
            TextEmail.setError("Будь ласка, введіть пошту");
            TextEmail.requestFocus();
            return;
        }

        //validating email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(forgot_email).matches()) {
            TextEmail.setError("Будь ласка, введіть коректну пошту");
            TextEmail.requestFocus();
            return;
        }

        forgotPassword(forgot_email);
    }

    private void forgotPassword(String email) {

        //making api call
        Api api = ApiClient.getClient().create(Api.class);
        Call<User>  forgotpassword = api.forgotpassword(email);

        forgotpassword.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.body().getIsSuccess() == 1) {
                    //get username
                    String user = response.body().getEmail();


                    Toast.makeText(ForgetPasswordActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(ForgetPasswordActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ForgetPasswordActivity.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });
        }

    }
