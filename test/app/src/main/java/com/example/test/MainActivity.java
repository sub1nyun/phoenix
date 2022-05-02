package com.example.test;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.test.common.AskTask;
import com.example.test.common.CommonVal;
import com.example.test.diary.BodyFragment;
import com.example.test.diary.DiaryFragment;
import com.example.test.home.HomeActivity;
import com.example.test.iot.MyFirebaseMessaging;
import com.example.test.my.CoParentFragment;
import com.example.test.my.EditFragment;
import com.example.test.my.MyFragment;
import com.example.test.sns.SnsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    FrameLayout container;
    TabLayout tab_main;
    TabItem tab_diary, tab_map, tab_iot, tab_sns, tab_my;

    public static Activity mainActivity;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;

    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION};
    int position = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;

        if (HomeActivity.activity_home != null) {
            HomeActivity.activity_home.finish();
        }

        //fcm 서비스 실행
        Intent fcm = new Intent(getApplicationContext(), MyFirebaseMessaging.class);
        startService(fcm);



        //getHashKey();

        container = findViewById(R.id.container);
        tab_main = findViewById(R.id.tab_main);
        tab_diary = findViewById(R.id.tab_diary);
        tab_map = findViewById(R.id.tab_map);
        tab_iot = findViewById(R.id.tab_iot);
        tab_sns = findViewById(R.id.tab_sns);
        tab_my = findViewById(R.id.tab_my);

        Intent intent = getIntent();
        intent.getStringExtra("vo");

        if (CommonVal.baby_list.size() == 0) {
            changeFrag(new AddFragment());
        }else if(intent.getStringExtra("vo") != null){
            changeFrag(new SnsFragment(this));
        }
        else {
            changeFrag(new DiaryFragment());
        }




        tab_main.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    if (CommonVal.baby_list.size() == 0) {
                        changeFrag(new AddFragment());
                    } else {
                        fragment = new DiaryFragment();
                        changeFrag(fragment);
                        position = 0;
                    }
                } else if (tab.getPosition() == 1) {
                    if (!checkLocationServicesStatus()) {
                        showDialogForLocationServiceSetting();
                    } else {
                        checkRunTimePermission();
                    }
                } else if (tab.getPosition() == 2) {
                    fragment = new IotFragment();
                    changeFrag(fragment);
                    position = 2;
                } else if (tab.getPosition() == 3) {
                    if (CommonVal.baby_list.size() == 0) {
                        changeFrag(new AddFragment());
                    } else {
                        fragment = new SnsFragment(MainActivity.this);
                        changeFrag(fragment);
                        position = 3;
                    }
                } else if (tab.getPosition() == 4) {
                    if (CommonVal.baby_list.size() == 0) {
                        changeFrag(new AddFragment());
                    } else {
                        fragment = new MyFragment();
                        changeFrag(fragment);
                        position = 4;
                    }
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

    public void changeFrag(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    public void backFrag(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {// 한 화면에서 액티비티 또는 인텐트로 여러 기능을 사용했을때
            String pageDate = data.getStringExtra("pageDate");
            changeFrag(new DiaryFragment(pageDate));
        } else if (requestCode == 1001 && resultCode == RESULT_OK) {
            String a = "";
            Uri fileUri = data.getData();
            sendMpeg(getRealPath(MainActivity.this ,fileUri));
            Log.d("TAG", "onActivityResult: ");
        }
        switch (requestCode) {
            case GPS_ENABLE_REQUEST_CODE:
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        checkRunTimePermission();
                        return;
                    }
                }
                break;
        }
    }

    @Nullable
    public static String getRealPath(Context context, Uri uri) {
        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {
            // MediaProvider
            final String docId = DocumentsContract.getDocumentId(uri);
            final String[] split = docId.split(":");
            final String type = split[0];
            Uri contentUri = null;
            if ("image".equals(type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(type)) {
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(type)) {
                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            } else if ("primary".equals(type)) {
                Log.d("filename", "getRealPath: "+Environment.getExternalStorageDirectory() + "/" + split[1]);
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            }


            final String selection = "_id=?";
            final String[] selectionArgs = new String[]{split[1]};

            Cursor cursor = null;
            String column = "_data";
            String[] projection = {column};
            try {
                cursor = context.getContentResolver().query(contentUri, projection, selection, selectionArgs, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndexOrThrow(column);
                    return cursor.getString(index);
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        }
        return null;
    }

    public void sendMpeg(String realPath) {

        File file = new File(realPath);
        byte[] data = new byte[(int) file.length()];

        try (FileInputStream stream = new FileInputStream(file)) {
            stream.read(data, 0, data.length);

        } catch (Throwable e) {
            e.printStackTrace();
        }
        AskTask askTask = new AskTask(CommonVal.httpip,"music");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            askTask.addParam("musicfile", Base64.getUrlEncoder().encodeToString(data));
            //askTask.fileData = java.util.Base64.getUrlEncoder().encodeToString(data);
            askTask.execute();
        }
    }

    /*private void getHashKey() {
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
    }*/

    //백버튼 처리
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    //플래그먼트마다의 백버튼 처리
    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment.getClass() == EditFragment.class) {
            ((OnBackPressedListenser) fragment).onBackPressed();
        } else if (fragment.getClass() == BodyFragment.class) {
            ((OnBackPressedListenser) fragment).onBackPressed();
        } else if (fragment.getClass() == CoParentFragment.class) {
            ((OnBackPressedListenser) fragment).onBackPressed();
        } else {
            if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
                moveTaskToBack(true);
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            } else {
                backPressedTime = tempTime;
                Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE && grantResults.length == REQUIRED_PERMISSIONS.length) {
            boolean check_result = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }
            if (check_result) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
                TabLayout.Tab tab = tab_main.getTabAt(position);
                tab.select();
            }
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
                Toast.makeText(this, "권한이 거부되어 현재 위치를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "퍼미션이 거부되었습니다. 설정에서 퍼미션을 다시 설정해주세요", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void checkRunTimePermission() {
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            startActivity(intent);
            TabLayout.Tab tab = tab_main.getTabAt(position);
            tab.select();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {
                Toast.makeText(this, "맵을 보기 위해 위치 접근 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    private void showDialogForLocationServiceSetting() {
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

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void changeTab(){
        TabLayout.Tab tab = tab_main.getTabAt(4);
        tab.select();
    }
}