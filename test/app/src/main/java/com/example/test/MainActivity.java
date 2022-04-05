package com.example.test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.test.diary.DiaryFragment;
import com.example.test.home.HomeActivity;
import com.example.test.diary.BodyFragment;
import com.example.test.my.EditFragment;
import com.example.test.my.MyFragment;
import com.example.test.sns.GrowthVO;
import com.example.test.sns.SnsFragment;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    FrameLayout container;
    TabLayout tab_main;
    TabItem tab_diary, tab_map, tab_iot, tab_sns, tab_my;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private static final int GRO_CODE = 7;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(HomeActivity.activity_home != null){
            HomeActivity.activity_home.finish();
        }






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
                    position = 0;
                } else if(tab.getPosition()==1){
                    if(!checkLocationServicesStatus()){
                        showDialogForLocationServiceSetting();
                    } else{
                        checkRunTimePermission();
                    }
                } else if(tab.getPosition()==2){
                    fragment = new IotFragment();
                    changeFrag(fragment);
                    position = 2;
                } else if(tab.getPosition()==3){
                    fragment = new SnsFragment(MainActivity.this);
                    changeFrag(fragment);
                    position = 3;
                } else if(tab.getPosition()==4){
                    fragment = new MyFragment();
                    changeFrag(fragment);
                    position = 4;
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

    public void backFrag(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000 && resultCode == Activity.RESULT_OK){// 한 화면에서 액티비티 또는 인텐트로 여러 기능을 사용했을때
            //DiaryVO dto = (DiaryVO) data.getSerializableExtra("dto");
            String pageDate = data.getStringExtra("pageDate");
            changeFrag(new DiaryFragment(pageDate));
            //Log.d("asd", "onActivityResult: "+dto.getStart_time());
        }else if(requestCode == 1001){

        }else if (requestCode == GRO_CODE){
            changeFrag(new SnsFragment(MainActivity.this));
            //탭을 강제 처리
        }
        switch (requestCode){
            case GPS_ENABLE_REQUEST_CODE:
                if(checkLocationServicesStatus()){
                    if(checkLocationServicesStatus()){
                        checkRunTimePermission();
                        return;
                    }
                }
                break;
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
        }else if(fragment.getClass() == BodyFragment.class){
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSIONS_REQUEST_CODE && grantResults.length == REQUIRED_PERMISSIONS.length){
            boolean check_result = true;

            for(int result : grantResults){
                if(result != PackageManager.PERMISSION_GRANTED){
                    check_result = false;
                    break;
                }
            }
            if(check_result){
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
                TabLayout.Tab tab = tab_main.getTabAt(position);
                tab.select();
            }
        } else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])){
                Toast.makeText(this, "권한이 거부되어 현재 위치를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, "퍼미션이 거부되었습니다. 설정에서 퍼미션을 다시 설정해주세요", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void checkRunTimePermission(){
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(hasFineLocationPermission == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            startActivity(intent);
            TabLayout.Tab tab = tab_main.getTabAt(position);
            tab.select();
        }
        else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])){
                Toast.makeText(this, "맵을 보기 위해 위치 접근 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            } else{
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    private void showDialogForLocationServiceSetting(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화").setMessage("현재 위치 중심으로 검색하기 위해 위치 서비스가 필요합니다\n위처 설정을 수정하시겠습니까?").setCancelable(true).setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    public boolean checkLocationServicesStatus(){
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}