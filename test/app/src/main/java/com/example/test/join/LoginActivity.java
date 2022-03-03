package com.example.test.join;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.R;

public class LoginActivity extends AppCompatActivity {
    Button btn_login,btn_join,btn_forget;
    EditText edt_id , edt_pw;
    CheckBox chk_auto;
    ImageView btn_kakao, btn_naver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String aa = "";

        btn_login = findViewById(R.id.btn_login);
        btn_join = findViewById(R.id.btn_join);
        btn_forget = findViewById(R.id.btn_forget);
        edt_id = findViewById(R.id.edt_id);
        edt_pw = findViewById(R.id.edt_pw);
        chk_auto = findViewById(R.id.chk_auto);
        btn_kakao = findViewById(R.id.btn_kakao);
        btn_naver = findViewById(R.id.btn_naver);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        btn_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        btn_kakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        btn_naver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });



    }


































    public void changeFrag(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}