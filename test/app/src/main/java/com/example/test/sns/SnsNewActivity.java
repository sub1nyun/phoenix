package com.example.test.sns;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.R;

public class SnsNewActivity extends AppCompatActivity {

    ImageView sns_new_back;
    TextView sns_new_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sns_new);

        sns_new_back = findViewById(R.id.sns_new_back);
        sns_new_share = findViewById(R.id.sns_new_share);

        sns_new_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        sns_new_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });



    }
}