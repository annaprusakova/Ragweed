package com.prusakova.ragweed;

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

public class SignUpActivity extends AppCompatActivity {

    EditText TextUserName;
    EditText TextEmail;
    EditText TextPassword;
    Button ButtonSignUp;
    TextView TextViewLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_sign_up);

        TextUserName = (EditText) findViewById(R.id.edittext_username);
        TextEmail = (EditText) findViewById(R.id.edittext_email);
        TextPassword = (EditText) findViewById(R.id.edittext_password);
        ButtonSignUp = (Button) findViewById(R.id.button_signup);
        TextViewLogIn = (TextView) findViewById(R.id.textview_signin);

        TextViewLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LogInIntent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(LogInIntent);
            }
        });

        ButtonSignUp.setOnClickListener(new View.OnClickListener() {
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

    boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    //check data
    void checkDataEntered(){
        String passOne = TextPassword.getText().toString();
        if (isEmpty(TextUserName)) {
            Toast t = Toast.makeText(this, "Введіть ім'я користувача!", Toast.LENGTH_SHORT);
            t.show();
        }
        if (isEmail(TextEmail) == false) {
            TextEmail.setError("Невірний тип електронної адреси!");
        }
        if(isEmpty(TextPassword)){
            TextPassword.setError("Будь ласка, введіть пароль");
        }


    }
}
