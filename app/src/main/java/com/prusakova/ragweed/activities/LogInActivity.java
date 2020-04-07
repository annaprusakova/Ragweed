package com.prusakova.ragweed.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class LogInActivity extends AppCompatActivity {

    EditText TextEmail;
    EditText TextPassword;
    FloatingActionButton ButtonLogin;
    TextView TextViewRegister;
    TextView TextViewForgotPassword;

    DatabaseHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_log_in);

        sqliteHelper = new DatabaseHelper(this);
        TextEmail = (EditText) findViewById(R.id.edittext_email);
        TextPassword = (EditText) findViewById(R.id.edittext_password);
        ButtonLogin = (FloatingActionButton) findViewById(R.id.button_login);
        TextViewRegister = (TextView) findViewById(R.id.textview_singup);
        TextViewForgotPassword = (TextView) findViewById(R.id.textview_forgot_password);

       ButtonLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String email = TextEmail.getText().toString().trim();
               String password = TextPassword.getText().toString().trim();
               boolean res = sqliteHelper.Authenticate(email, password);
               if (validateEmail() && validatePassword()) {
                   if (res) {
                       Intent Profile = new Intent(LogInActivity.this, ProfileActivity.class);
                       startActivity(Profile);
                       finish();
                   } else {
                       Toast.makeText(LogInActivity.this, "Невірна пошта чи пароль", Toast.LENGTH_SHORT).show();

                   }
               }
           }
       });
    }

    //This method is used to validate input given by user
    public boolean validateEmail() {
        boolean valid = false;

        String Email = TextEmail.getText().toString();

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

    public void OpenSignupPage(View view) {
        startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
    }

    public void OpenForgotPasswordPage(View view) {
        startActivity(new Intent(LogInActivity.this, ForgetPasswordActivity.class));
    }



}
