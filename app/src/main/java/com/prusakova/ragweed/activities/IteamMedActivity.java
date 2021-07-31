package com.prusakova.ragweed.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.prusakova.ragweed.R;
import com.squareup.picasso.Picasso;

public class IteamMedActivity extends AppCompatActivity {

    private ImageView MedImg;
    private TextView MedName;
    private TextView MedCost;
    private TextView MedActive;
    private LinearLayout toComments;
    private TextView medText;

    public static final String NAME = "IteamMedActivity";
    private int id_med;
    private String med_name, photo_med, active_substance, cost,instruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.medicines_content);

        MedImg = findViewById(R.id.image_med_view);
        MedName = findViewById(R.id.name_med_view);
        MedCost = findViewById(R.id.med_cost);
        MedActive = findViewById(R.id.med_active);
        toComments = findViewById(R.id.to_comments_med);
        medText = findViewById(R.id.med_text);

        Intent intent = getIntent();
        id_med = intent.getIntExtra("id_med", 0);
        med_name = intent.getStringExtra("med_name");
        photo_med = intent.getStringExtra("photo_med");
        cost = intent.getStringExtra("cost");
        active_substance = intent.getStringExtra("active_substance");
        instruction = intent.getStringExtra("instruction");



        toComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(IteamMedActivity.this, CommentsActivity.class);
                i.putExtra("id_med",id_med);
                i.putExtra("Activity", "IteamMedActivity.NAME");
                startActivity(i);
            }
        });

        setDataFromIntentExtra();
    }


    private void setDataFromIntentExtra() {

        if (id_med != 0) {

//            getSupportActionBar().setTitle( article_name.toString());

            MedName.setText(med_name);
            Picasso.with(this).load(photo_med).into(MedImg);
            MedCost.setText("Ціна: " + cost);
            MedActive.setText("Діюча речовина: " + active_substance);
            medText.setText(instruction);



        } else {
            getSupportActionBar().setTitle("Add med");
        }
    }


    public void BackMed(View view) {
        finish();
    }
}
