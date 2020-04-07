package com.prusakova.ragweed.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.prusakova.ragweed.R;
import com.prusakova.ragweed.model.User;
import com.prusakova.ragweed.sql.DatabaseHelper;

public class SignUpActivity extends AppCompatActivity {

    EditText TextUserName;
    EditText TextEmail;
    EditText TextPassword;
    FloatingActionButton ButtonSignUp;
    TextView TextViewLogIn;


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
        ButtonSignUp = (FloatingActionButton) findViewById(R.id.button_signup);
        TextViewLogIn = (TextView) findViewById(R.id.textview_signin);

        ButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateUserName() && validateEmail() && validatePassword()) {
                    String user = TextUserName.getText().toString();
                    String email = TextEmail.getText().toString();
                    String password = TextPassword.getText().toString();
                    if (!sqliteHelper.isEmailExists(email)) {
                        sqliteHelper.addUser(user, email, password);
                        Toast.makeText(SignUpActivity.this, "Ви зареєстровані!", Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(SignUpActivity.this, LogInActivity.class);
                        startActivity(moveToLogin);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, Snackbar.LENGTH_LONG);

                    } else {
                        Toast.makeText(SignUpActivity.this, "Електронна адреса вже існує", Toast.LENGTH_SHORT).show();
                    }

                }
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

    //This method is used to validate input given by user
    public boolean validateUserName() {
        boolean valid = false;

        String UserName = TextUserName.getText().toString();

        //Handling validation for UserName field
        if (UserName.isEmpty()) {
            valid = false;
            TextUserName.setError("Будь ласка, введіть ім'я користувача!");
        } else {
            if (UserName.length() > 5) {
                valid = true;
                TextUserName.setError(null);
            } else {
                valid = false;
                TextUserName.setError("ім'я користувача дуже коротке!");
            }

        }
        return valid;
    }
    public boolean validateEmail() {
        boolean valid = false;

        String Email = TextEmail.getText().toString();

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            TextEmail.setError("Будь ласка, введіть коректну адресу електронної пошти!");
        } else {
            valid = true;
            TextEmail.setError(null);
        }
        return valid;
    }
    public boolean validatePassword() {
        boolean valid = false;

        String Password = TextPassword.getText().toString();
        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            TextPassword.setError("Будь ласка, введіть пароль!");
        } else {
            if (Password.length() > 5) {
                valid = true;
                TextPassword.setError(null);
            } else {
                valid = false;
                TextPassword.setError("Пароль дуже короткий!");
            }
        }
        return valid;
    }





}
