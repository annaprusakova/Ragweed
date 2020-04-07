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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prusakova.ragweed.R;

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

    //check correct email
    boolean isEmail (EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }


    // check data
    void checkDataEntered(){
        if (isEmail(TextEmail) == false) {
            TextEmail.setError("Будь ласка, введіть коректну пошту");
        }
    }
}
