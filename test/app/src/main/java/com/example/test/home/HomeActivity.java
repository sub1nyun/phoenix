package com.example.test.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.test.LoginActivity;
import com.example.test.R;
import com.example.test.join.JoinMainActivity;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import me.relex.circleindicator.CircleIndicator3;

public class HomeActivity extends AppCompatActivity {

    ViewPager2 mPager;
    FragmentStateAdapter pagerAdapter;
    int num_page = 5;
    DotsIndicator mIndicator;
    Button btn_login, btn_join;
    Intent intent;
    CircleIndicator3 nIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        btn_join = findViewById(R.id.btn_join);
        btn_login = findViewById(R.id.btn_login);
        mPager = findViewById(R.id.viewpager);

        pagerAdapter = new MyAdapter(this, num_page);
        mPager.setAdapter(pagerAdapter);

        nIndicator = findViewById(R.id.indicator);
        nIndicator.setViewPager(mPager);

        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        mPager.setCurrentItem(1000);
        mPager.setOffscreenPageLimit(5);

        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if(positionOffsetPixels == 0) {
                    mPager.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                nIndicator.animatePageSelected(position%num_page);
            }
        });

        btn_join.setOnClickListener(v -> {
           intent = new Intent(this, JoinMainActivity.class);
           startActivity(intent);
        });

        btn_login.setOnClickListener(v -> {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

    }
}