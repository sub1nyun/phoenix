package com.example.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.FrameLayout;

import com.example.test.diary.detailDTO;
import com.example.test.my.EditFragment;
import com.example.test.my.MyFragment;
import com.example.test.sns.SnsFragment;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    FrameLayout container;
    TabLayout tab_main;
    TabItem tab_diary, tab_map, tab_iot, tab_sns, tab_my;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getHashKey();

        container = findViewById(R.id.container);
        tab_main = findViewById(R.id.tab_main);
        tab_diary = findViewById(R.id.tab_diary);
        tab_map = findViewById(R.id.tab_map);
        tab_iot = findViewById(R.id.tab_iot);
        tab_sns = findViewById(R.id.tab_sns);
        tab_my = findViewById(R.id.tab_my);

        changeFrag(new DiaryFragment());

        tab_main.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    fragment = new DiaryFragment();
                    changeFrag(fragment);
                } else if(tab.getPosition()==1){
                    Intent intent = new Intent(MainActivity.this, MapActivity.class);
                    startActivity(intent);
                    finish();
                } else if(tab.getPosition()==2){
                    fragment = new IotFragment();
                    changeFrag(fragment);
                } else if(tab.getPosition()==3){
                    fragment = new SnsFragment();
                    changeFrag(fragment);
                } else if(tab.getPosition()==4){
                    fragment = new MyFragment();
                    changeFrag(fragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void changeFrag(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String a ="";
        if(requestCode == 1000 && resultCode == Activity.RESULT_OK){// 한 화면에서 액티비티 또는 인텐트로 여러 기능을 사용했을때
            detailDTO dto = (detailDTO) data.getSerializableExtra("dto");
            changeFrag(new DiaryFragment(dto));
            Log.d("asd", "onActivityResult: "+dto.getStart_time());
        }else if(requestCode == 1001){//<- ex) 카메라 기능을 사용하고나서의 결과를 처리.

        }
    }
    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }


    //백버튼 처리
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    //플래그먼트마다의 백버튼 처리
    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if(fragment.getClass() == EditFragment.class){
            ((OnBackPressedListenser)fragment).onBackPressed();
        }else{
            if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
            {
                finish();
            }
            else
            {
                backPressedTime = tempTime;
                Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}