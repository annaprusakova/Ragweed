package com.prusakova.ragweed.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.prusakova.ragweed.R;
import com.squareup.picasso.Picasso;

public class ChatActivity  extends AppCompatActivity {

    private com.google.android.material.button.MaterialButton bSend;

    private int chat_id;
    private String chat_name;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.chat_room);

        bSend = findViewById(R.id.btn_send);

        Intent intent = getIntent();
        chat_id = intent.getIntExtra("chat_id", 0);
        chat_name = intent.getStringExtra("chat_name");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_chevron_left);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setDataFromIntentExtra();
    }


    private void setDataFromIntentExtra() {

        if (chat_id != 0) {


            if(getSupportActionBar() != null){
                getSupportActionBar().setTitle(chat_name);
            }


        } else {
            getSupportActionBar().setTitle("Add an chat");
        }
    }

}
