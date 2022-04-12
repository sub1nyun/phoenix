package com.example.test.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.test.LoginActivity;
import com.example.test.R;
import com.example.test.join.JoinMainActivity;


public class Home_SnsFragment extends Fragment {

    Button btn_login, btn_join;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.test5, container, false);
       btn_join = rootView.findViewById(R.id.btn_join);
       btn_login = rootView.findViewById(R.id.btn_login);

       btn_login.setOnClickListener(v -> {
           Intent intent = new Intent(getContext(), LoginActivity.class);
           startActivity(intent);
       });

       btn_join.setOnClickListener(v -> {
           Intent intent = new Intent(getContext(), JoinMainActivity.class);
           startActivity(intent);
       });


        return rootView;
    }






}