package com.example.test.join;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.test.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        moveMain();

    }

    private void moveMain() {
        runOnUiThread(()->{
            new Handler(Looper.myLooper()).postDelayed(()->{
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }, 100);
        });
    }
}