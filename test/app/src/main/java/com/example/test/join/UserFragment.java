package com.example.test.join;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.test.R;

public class UserFragment extends Fragment {
    EditText edt_id ,edt_pw;
    ImageView join_kakao, join_naver;

    String family_id;

    public UserFragment() {

    }

    public UserFragment(String family_id) {
        this.family_id = family_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootVIew = (ViewGroup)  inflater.inflate(R.layout.fragment_user, container, false);
        JoinMainActivity.go = 1;
        if(family_id != null){
            JoinMainActivity.go = 8;
        }

        edt_id = rootVIew.findViewById(R.id.edt_id);
        edt_pw = rootVIew.findViewById(R.id.edt_pw);
        join_kakao = rootVIew.findViewById(R.id.join_kakao);
        join_naver = rootVIew.findViewById(R.id.join_naver);

        join_kakao.setOnClickListener(v -> {
            Toast.makeText(getContext(), "카카오 눌림", Toast.LENGTH_SHORT).show();
        });

        join_naver.setOnClickListener(v -> {
            Toast.makeText(getContext(), "네이버도 눌림", Toast.LENGTH_SHORT).show();
        });







        return rootVIew;
    }
}