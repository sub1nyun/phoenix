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

import com.example.test.R;

public class UserFragment extends Fragment {
    EditText edt_id ,edt_pw;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootVIew = (ViewGroup)  inflater.inflate(R.layout.fragment_user, container, false);
        JoinMainActivity.go = 1;
        JoinMainActivity.back = 1;

        edt_id = rootVIew.findViewById(R.id.edt_id);
        edt_pw = rootVIew.findViewById(R.id.edt_pw);














        return rootVIew;
    }
}