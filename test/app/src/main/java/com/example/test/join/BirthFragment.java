package com.example.test.join;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.test.R;

public class BirthFragment extends Fragment {

    EditText edt_pw;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_birth, container, false);
        JoinMainActivity.go = 3;
        JoinMainActivity.back = 3;


        edt_pw = rootView.findViewById(R.id.edt_pw);




        return rootView;
    }
}