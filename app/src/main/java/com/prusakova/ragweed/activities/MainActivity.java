package com.prusakova.ragweed.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prusakova.ragweed.R;

public class MainActivity extends AppCompatActivity {

    private ViewPager mSlideViewPage;
    private LinearLayout mDotLayout;

    private TextView[] mDots;

    private SlideAdapter slideAdapter;

    private com.google.android.material.button.MaterialButton mNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSlideViewPage = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);
        mNext = (com.google.android.material.button.MaterialButton) findViewById(R.id.textview_next);


        slideAdapter = new SlideAdapter(this);

        mSlideViewPage.setAdapter(slideAdapter);

        addDotsIndicator(0);
        mSlideViewPage.addOnPageChangeListener(viewListerner);

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, LogInActivity.class);
                startActivity(registerIntent);

            }
        });


    }


    public void addDotsIndicator(int position) {

        mDots = new TextView[3];
        mDotLayout.removeAllViews();

        for (int i = 0; i < mDots.length; i++) {

            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            mDotLayout.addView(mDots[i]);
        }
        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.colorDots));
        }
    }

    ViewPager.OnPageChangeListener viewListerner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


        }

    @Override
    public void onPageSelected(int position) {
        addDotsIndicator(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
};

}
