package com.prusakova.ragweed.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.prusakova.ragweed.R;
import com.prusakova.ragweed.api.Api;
import com.prusakova.ragweed.api.ApiClient;
import com.prusakova.ragweed.api.SharedPref;
import com.prusakova.ragweed.model.Article;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddArticleActivity extends AppCompatActivity {

    private EditText nameA;
    private EditText linkA;
    private EditText linkImageA;
    private EditText textA;
    private Toolbar toolbar;
    private Api apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_article);

        nameA = findViewById(R.id.article_add_name);
        linkA = findViewById(R.id.article_add_link);
        linkImageA = findViewById(R.id.article_add_image_link);
        textA = findViewById(R.id.article_add_text);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        toolbar.setNavigationIcon(R.drawable.ic_chevron_left);

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_save_point:


                if (TextUtils.isEmpty(nameA.getText().toString()) || TextUtils.isEmpty(linkA.getText().toString()) || TextUtils.isEmpty(linkImageA.getText().toString())
                || TextUtils.isEmpty(textA.getText().toString())) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                    alertDialog.setMessage("Будь ласка, заповніть поля!");
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                } else {

                    addData();
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void addData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Збереження...");
        progressDialog.show();

        String name = nameA.getText().toString();
        String link = linkA.getText().toString();
        String linkImage = linkImageA.getText().toString();
        String text = textA.getText().toString();

        int userId = SharedPref.getInstance(AddArticleActivity.this).LoggedInUserId();

        apiInterface = ApiClient.getClient().create(Api.class);

        Call<Article> call = apiInterface.insertArticle(userId,name,link,linkImage,text);

        call.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {

                progressDialog.dismiss();


                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                } else {
                    Toast.makeText(AddArticleActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AddArticleActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}
