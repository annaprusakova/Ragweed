package com.prusakova.ragweed.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.prusakova.ragweed.R;
import com.prusakova.ragweed.api.Api;
import com.prusakova.ragweed.api.ApiClient;
import com.prusakova.ragweed.api.SharedPref;
import com.prusakova.ragweed.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPasswordActivity  extends AppCompatActivity {

    private TextView oldPassword;
    private TextView newPassword;
    private TextView confirmPassword;
    private Toolbar toolbar;
    Api apiInterface;
    String oldpassword;
    String newpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.edit_password);

        oldPassword = findViewById(R.id.old_password);
        newPassword = findViewById(R.id.new_password);
        confirmPassword = findViewById(R.id.confirm_password);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        toolbar.setNavigationIcon(R.drawable.ic_arrow_left);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
        return true;
    }


    public void checkPassword(){
        if (TextUtils.isEmpty(oldPassword.getText().toString()) || TextUtils.isEmpty(newPassword.getText().toString())
                || TextUtils.isEmpty(confirmPassword.getText().toString())) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("Будь ласка, заповніть поля!");
            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
            return;

        }  if (!newPassword.getText().toString().equals(confirmPassword.getText().toString())) {
            newPassword.setError("Паролі не співпадають");
            newPassword.requestFocus();
            return;
        }

            int id = SharedPref.getInstance(EditPasswordActivity.this).LoggedInUserId();
            newpassword = newPassword.getText().toString();
            oldpassword = oldPassword.getText().toString();
            updatePassword(id, oldpassword,newpassword);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_save:

             checkPassword();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void updatePassword(final int id, final String oldpassword, final String newpassword) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Оновлення...");
        progressDialog.show();


        apiInterface = ApiClient.getClient().create(Api.class);

        Call<User> call = apiInterface.updatePassword(id,oldpassword,newpassword);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                progressDialog.dismiss();


                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")){
                    Toast.makeText(EditPasswordActivity.this, "Збереженно", Toast.LENGTH_SHORT).show();
                    oldPassword.setText("");
                    newPassword.setText("");
                    confirmPassword.setText("");
                } else {
                    Toast.makeText(EditPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditPasswordActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
