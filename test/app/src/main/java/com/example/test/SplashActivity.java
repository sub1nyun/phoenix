package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

<<<<<<< HEAD:test/app/src/main/java/com/example/test/join/SplashActivity.java
=======


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this , LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },100);
>>>>>>> a1b0d83a72f64e933541337235a7b5e7f6870de1:test/app/src/main/java/com/example/test/SplashActivity.java

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