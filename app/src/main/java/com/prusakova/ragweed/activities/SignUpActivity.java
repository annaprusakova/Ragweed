package com.prusakova.ragweed.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.prusakova.ragweed.R;
import com.prusakova.ragweed.model.User;
import com.prusakova.ragweed.sql.DatabaseHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private EditText TextUserName;
    private EditText TextEmail;
    private EditText TextPassword;
    private EditText TextPasswordConfirm;
    private  FloatingActionButton ButtonSignUp;
    private TextView TextViewLogIn;
    private ProgressBar loading;
    private static String URL_REGIST ="http://192.168.1.6/api/register.php";



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
        loading = findViewById(R.id.loading_signup);



        ButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Regist();

//                if (validateUserName() && validateEmail() && validatePassword()) {
//                    String user = TextUserName.getText().toString();
//                    String email = TextEmail.getText().toString();
//                    String password = TextPassword.getText().toString();
//                    if (!sqliteHelper.isEmailExists(email)) {
//                        sqliteHelper.addUser(user, email, password);
//                        Toast.makeText(SignUpActivity.this, "Ви зареєстровані!", Toast.LENGTH_SHORT).show();
//                        Intent moveToLogin = new Intent(SignUpActivity.this, LogInActivity.class);
//                        startActivity(moveToLogin);
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                finish();
//                            }
//                        }, Snackbar.LENGTH_LONG);
//
//                    } else {
//                        Toast.makeText(SignUpActivity.this, "Електронна адреса вже існує", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
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


    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }



    private void Regist(){
        loading.setVisibility(View.VISIBLE);
        ButtonSignUp.setVisibility(View.GONE);

        final String name = TextUserName.getText().toString().trim();
        final String email = TextEmail.getText().toString().trim();
        final String password = TextPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(SignUpActivity.this, "Register success ",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(SignUpActivity.this, "Register fail " + e.toString(),Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            ButtonSignUp.setVisibility(View.VISIBLE);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUpActivity.this, "Register fail " + error.toString(),Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        ButtonSignUp.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
