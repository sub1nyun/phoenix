package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.mapmodel.Document;
import com.example.test.utils.IntentKey;

public class PlaceDetailActivity extends AppCompatActivity {
    TextView placeNameText, addressText, categoryText, urlText, phoneText;
    ImageView back_map;

    private long backTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        placeNameText = findViewById(R.id.placedetail_tv_name);
        addressText = findViewById(R.id.placedetail_tv_address);
        categoryText = findViewById(R.id.placedetail_tv_category);
        urlText = findViewById(R.id.placedetail_tv_url);
        phoneText = findViewById(R.id.placedetail_tv_phone);
        back_map = findViewById(R.id.back_map);

        back_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        processIntent();
    }

    private void processIntent(){
        Intent processIntent = getIntent();
        Document document = processIntent.getParcelableExtra(IntentKey.PLACE_SEARCH_DETAIL_EXTRA);
        placeNameText.setText(document.getPlaceName());
        addressText.setText(document.getAddressName());
        categoryText.setText(document.getCategoryName());
        urlText.setText(document.getPlaceUrl());
        phoneText.setText(document.getPhone());
    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() > backTime + 2000){
            finish();
        }
    }
}