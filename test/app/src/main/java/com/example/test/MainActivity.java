package com.example.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

<<<<<<< HEAD
import com.example.test.diary.detailDTO;
=======
<<<<<<< HEAD
import com.example.test.my.MyFragment;
=======
import com.example.test.sns.SnsFragment;
>>>>>>> 11ce8cc82f734e0efc8976304699dcf153c481b2
>>>>>>> ce11e49193ecf2fcaa055554376bb3254fce9e34
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    FrameLayout container;
    TabLayout tab_main;
    TabItem tab_diary, tab_map, tab_iot, tab_sns, tab_my;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
                } else if(tab.getPosition()==1){
                    fragment = new MapFragment();
                } else if(tab.getPosition()==2){
                    fragment = new IotFragment();
                } else if(tab.getPosition()==3){
                    fragment = new SnsFragment();
                } else if(tab.getPosition()==4){
                    fragment = new MyFragment();
                }
                changeFrag(fragment);
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
}