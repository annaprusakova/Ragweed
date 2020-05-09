package com.prusakova.ragweed.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.prusakova.ragweed.R;
import com.prusakova.ragweed.fragments.ArticleFragment;
import com.prusakova.ragweed.model.Article;
import com.squareup.picasso.Picasso;

public class ArticleActivity extends AppCompatActivity {

    private ImageView ArticleImg;
    private TextView ArticleName;
    private TextView ArticleLink;
    private TextView ArticleText;


    private int article_id;
    private String article_name,article_link, article_text, article_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.article_conent);

        ArticleImg = (ImageView) findViewById(R.id.image_article_view);
        ArticleName = (TextView) findViewById(R.id.name_article_view);
        ArticleText = findViewById(R.id.article_text);
        ArticleLink = (TextView) findViewById(R.id.article_link);


        Intent intent = getIntent();
        article_id = intent.getIntExtra("article_id", 0);
        article_name = intent.getStringExtra("article_name");
        article_img = intent.getStringExtra("article_img");
        article_link = intent.getStringExtra("article_link");
        article_text = intent.getStringExtra("article_text");

        setDataFromIntentExtra();
    }

    private void setDataFromIntentExtra() {

        if (article_id != 0) {

//            getSupportActionBar().setTitle( article_name.toString());

            ArticleName.setText(article_name);
            Picasso.with(this).load(article_img).into(ArticleImg);
            ArticleText.setText(article_text);
            ArticleLink.setText(article_link);


        } else {
            getSupportActionBar().setTitle("Add an article");
        }
    }

    public void BackArticles(View view) {
       finish();
    }

}
