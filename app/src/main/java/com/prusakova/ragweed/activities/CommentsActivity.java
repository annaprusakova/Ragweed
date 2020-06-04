package com.prusakova.ragweed.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prusakova.ragweed.CommentAdapter;
import com.prusakova.ragweed.R;
import com.prusakova.ragweed.api.Api;
import com.prusakova.ragweed.api.ApiClient;
import com.prusakova.ragweed.api.SharedPref;
import com.prusakova.ragweed.model.Comment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    CommentAdapter.RecyclerViewClickListener listener;
    private List<Comment> comments;
    private CommentAdapter commentAdapter;
    private Api apiInterface;
    ProgressBar progressBar;
    private Toolbar toolbar;
    private ImageView back;
    private TextView mTextEmpty;
    private TextView mComment;
    private ImageView mSentComment;
    int article_id, id_med;
    private  String topicA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.fragment_view_comments);
        back = findViewById(R.id.backArrow);

        apiInterface= ApiClient.getClient().create(Api.class);
        progressBar = findViewById(R.id.progress_com);
        recyclerView = findViewById(R.id.recyclerView_comments);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mTextEmpty = findViewById(R.id.text_empty);
        mComment = findViewById(R.id.comment);
        mSentComment = findViewById(R.id.postComment);
        toolbar = findViewById(R.id.commentToolBar);
        this.setSupportActionBar(toolbar);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        article_id = intent.getIntExtra("article_id", 0);
        id_med = intent.getIntExtra("id_med",0);
        topicA = intent.getStringExtra("Activity");


        String topic = null;
        int id = 0;
        if(!topicA.isEmpty() && topicA.equals("ArticleActivity.NAME")){
            fetchComments("article",article_id);
            Log.e("ac", "true");
        } else if(!topicA.isEmpty() && topicA.equals("IteamMedActivity.NAME")){
            fetchComments("medicine",id_med);
        }







        if(layoutManager.getItemCount() == 0){
            mTextEmpty.setVisibility(View.VISIBLE);
            mTextEmpty.setText("Ще немає коментарів. Додайте перший!");
        }

        mSentComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               addData("insert");
            }
        });

    }

    public void fetchComments(String type, int key){

        apiInterface = ApiClient.getClient().create(Api.class);

        Call<List<Comment>> call = apiInterface.getComments(type, key);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                progressBar.setVisibility(View.GONE);
                comments = response.body();
                commentAdapter = new CommentAdapter(comments, CommentsActivity.this, listener);
                recyclerView.setAdapter(commentAdapter);
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CommentsActivity.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void addData(final String key) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Відправлення...");
        progressDialog.show();


        int userId = SharedPref.getInstance(CommentsActivity.this).LoggedInUserId();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String text = mComment.getText().toString();


        apiInterface = ApiClient.getClient().create(Api.class);

        Call<Comment> call = apiInterface.addComment(key,currentDate,text,userId,article_id);

        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {

                progressDialog.dismiss();

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                    Toast.makeText(CommentsActivity.this, "Комментар додано", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CommentsActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CommentsActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        });


    }


}
