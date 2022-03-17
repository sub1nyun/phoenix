package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.test.home.HomeActivity;
import com.example.test.join.JoinMainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        moveMain();
        //스플래쉬 디자인 필요

    }

    private void moveMain() {
        runOnUiThread(()->{
            new Handler(Looper.myLooper()).postDelayed(()->{
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
            }, 100);
        });
    }

}