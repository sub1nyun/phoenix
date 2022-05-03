package com.example.test.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.test.R;

import me.relex.circleindicator.CircleIndicator3;

public class HomeActivity extends AppCompatActivity {
    ViewPager2 mPager;
    FragmentStateAdapter pagerAdapter;
    int num_page = 5;
    CircleIndicator3 nIndicator;
    public static HomeActivity activity_home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);
        activity_home = this;

        mPager = findViewById(R.id.viewpager);
        Intent intent = getIntent();



        pagerAdapter = new MyAdapter(this, num_page);
        mPager.setAdapter(pagerAdapter);
        if(intent.getStringExtra("restart")!=null){

        mPager.setCurrentItem(4);
        }

//        nIndicator = findViewById(R.id.indicator);
//        nIndicator.setViewPager(mPager);

//        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
//
//        mPager.setCurrentItem(0);
//        mPager.setOffscreenPageLimit(5);
//
//        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
//                if(positionOffsetPixels == 0) {
//                    mPager.setCurrentItem(position);
//                }
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                nIndicator.animatePageSelected(position%num_page);
//            }
//        });



    }
}