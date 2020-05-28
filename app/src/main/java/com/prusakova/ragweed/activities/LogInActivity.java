package com.prusakova.ragweed.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prusakova.ragweed.ArticleAdapter;
import com.prusakova.ragweed.MapsActivity;
import com.prusakova.ragweed.api.Api;
import com.prusakova.ragweed.api.ApiClient;
import com.prusakova.ragweed.R;
import com.prusakova.ragweed.api.SharedPref;
import com.prusakova.ragweed.model.User;

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
            TextEmail.setError("Будь ласка, введіть пошту");
            TextEmail.requestFocus();
            return;
        }
        //validating email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            TextEmail.setError("Будь ласка, введіть коректну пошту");
            TextEmail.requestFocus();
            return;
        }
        //checking if password is empty
        if (TextUtils.isEmpty(password)) {
            TextPassword.setError("Будь ласка, введіть пароль");
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
                    String user = response.body().getName();
                    int id = response.body().getId();
                    String photo = response.body().getUser_photo();
                    String email = response.body().getEmail();
                    String password = response.body().getPassword();

                    //storing the user in shared preferences
                    SharedPref.getInstance(LogInActivity.this).storeUserName(user);
                    SharedPref.getInstance(LogInActivity.this).storeUserId(id);
                    SharedPref.getInstance(LogInActivity.this).storeUserPhoto(photo);
                    SharedPref.getInstance(LogInActivity.this).storeUserEmail(email);
                    SharedPref.getInstance(LogInActivity.this).storeUserPassword(password);

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
