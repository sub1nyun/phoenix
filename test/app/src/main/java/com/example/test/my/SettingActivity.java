package com.example.test.my;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.test.R;

public class SettingActivity extends AppCompatActivity {
    ImageView setting_back;
    TextView set_secession, set_logout;
    Switch set_bell, set_vibration;
    SeekBar set_bell_volume, set_vibration_volume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settimg);

        setting_back = findViewById(R.id.setting_back);
        set_secession = findViewById(R.id.set_secession);
        set_logout = findViewById(R.id.set_logout);
        set_bell = findViewById(R.id.set_bell);
        set_vibration = findViewById(R.id.set_vibration);
        set_bell_volume = findViewById(R.id.set_bell_volume);
        set_vibration_volume = findViewById(R.id.set_vibration_volume);

        setting_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        set_secession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder_secession = new AlertDialog.Builder(SettingActivity.this).setTitle("회원탈퇴").setMessage("저장된 기록이 모두 삭제됩니다.\n정말 탈퇴하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //회원 정보 삭제, 로그인 화면으로 이동
                            }
                        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alertDialog = builder_secession.create();
                alertDialog.show();
            }
        });

        set_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder_logout = new AlertDialog.Builder(SettingActivity.this).setTitle("로그아웃").setMessage("정말 로그아웃 하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //로그인 화면으로 이동
                            }
                        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alertDialog = builder_logout.create();
                alertDialog.show();
            }
        });
    }
}