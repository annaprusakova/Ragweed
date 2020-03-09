package com.prusakova.ragweed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class LogInActivity extends AppCompatActivity {

    EditText TextEmail;
    EditText TextPassword;
    Button ButtonLogin;
    TextView TextViewRegister;
    TextView TextViewForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_log_in);

        TextEmail = (EditText) findViewById(R.id.edittext_email);
        TextPassword = (EditText) findViewById(R.id.edittext_password);
        ButtonLogin = (Button) findViewById(R.id.button_login);
        TextViewRegister = (TextView) findViewById(R.id.textview_singup);
        TextViewForgotPassword = (TextView) findViewById(R.id.textview_forgot_password);

        ButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextEmail.getText().toString().isEmpty()&&(!TextPassword.getText().toString().isEmpty())) {
                    String value = TextEmail.getText().toString().trim();
                    String message = "Будь ласка, введіть коректну пошту";
                    //delete this lately
                    if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
                        TextEmail.setError(message);
                        startActivity(new Intent(LogInActivity.this, ProfileActivity.class));
                        Toast.makeText(LogInActivity.this, "you have entered username" + TextEmail.getText().toString() + "Password " + TextPassword.getText().toString(), Toast.LENGTH_LONG).show();
                    }
                }else {
                    TextEmail.setError("Будь ласка, введіть електронну адресу");
                    TextPassword.setError("Будь ласка, введіть пароль");
                }
            }
        });
    }

    public void OpenSignupPage(View view) {
        startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
    }

    public void OpenForgotPasswordPage(View view) {
        startActivity(new Intent(LogInActivity.this, ForgetPasswordActivity.class));
    }
}
