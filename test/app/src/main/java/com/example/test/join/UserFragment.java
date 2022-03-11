package com.example.test.join;

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

import com.example.test.R;

public class UserFragment extends Fragment {
    EditText edt_id ,edt_pw;
    SeekBar SeekBar;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootVIew = (ViewGroup)  inflater.inflate(R.layout.fragment_user, container, false);
        JoinMainActivity.go = 1;
        JoinMainActivity.back = 1;

        edt_id = rootVIew.findViewById(R.id.edt_id);
        edt_pw = rootVIew.findViewById(R.id.edt_pw);
        SeekBar = rootVIew.findViewById(R.id.SeekBar);

        SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) {
                //조작중
            }

            @Override
            public void onStartTrackingTouch(android.widget.SeekBar seekBar) {
                //처음
            }

            @Override
            public void onStopTrackingTouch(android.widget.SeekBar seekBar) {
                // 끝끝
           }
        });














        return rootVIew;
    }
}