package com.prusakova.ragweed.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prusakova.ragweed.Api;
import com.prusakova.ragweed.ApiClient;
import com.prusakova.ragweed.R;
import com.prusakova.ragweed.SharedPref;
import com.prusakova.ragweed.model.User;
import com.prusakova.ragweed.sql.DatabaseHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {

    EditText TextEmail;
    EditText TextPassword;
    FloatingActionButton ButtonLogin;
    TextView TextViewRegister;
    TextView TextViewForgotPassword;

    final String loginURL = "http://192.168.1.6/api/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_log_in);

        TextEmail = (EditText) findViewById(R.id.edittext_email);
        TextPassword = (EditText) findViewById(R.id.edittext_password);
        ButtonLogin = (FloatingActionButton) findViewById(R.id.button_login);
        TextViewRegister = (TextView) findViewById(R.id.textview_singup);
        TextViewForgotPassword = (TextView) findViewById(R.id.textview_forgot_password);

       ButtonLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               validateUserData();
           }
       });
    }



    public void OpenSignupPage(View view) {
        startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
    }

    public void OpenForgotPasswordPage(View view) {
        startActivity(new Intent(LogInActivity.this, ForgetPasswordActivity.class));
    }


    private void validateUserData() {

        //first getting the values
        final String email = TextEmail.getText().toString();
        final String password = TextPassword.getText().toString();

        //checking if username is empty
        if (TextUtils.isEmpty(email)) {
            TextEmail.setError("Please enter your email");
            TextEmail.requestFocus();
            return;
        }
        //checking if password is empty
        if (TextUtils.isEmpty(password)) {
            TextPassword.setError("Please enter your password");
            TextPassword.requestFocus();
            return;
        }

        //Login User if everything is fine
        loginUser(email,password);

    }

    private void loginUser(String email, String password) {

        //making api call
        Api api = ApiClient.getClient().create(Api.class);
        Call<User> login = api.login(email,password);

        login.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.body().getIsSuccess() == 1){
                    //get username
                    String user = response.body().getEmail();

                    //storing the user in shared preferences
                    SharedPref.getInstance(LogInActivity.this).storeUserName(user);
//                    Toast.makeText(MainActivity.this,response.body().getUsername(),Toast.LENGTH_LONG).show();

                    startActivity(new Intent(LogInActivity.this,ProfileActivity.class));
                }else{
                    Toast.makeText(LogInActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                }




            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LogInActivity.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });


    }

}
