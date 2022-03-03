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
    Button btn_next;
    ImageView btn_back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootVIew = (ViewGroup)  inflater.inflate(R.layout.fragment_user, container, false);

        edt_id = rootVIew.findViewById(R.id.edt_id);
        edt_pw = rootVIew.findViewById(R.id.edt_pw);
        btn_next = rootVIew.findViewById(R.id.btn_next);
        btn_back = rootVIew.findViewById(R.id.btn_back);
        JoinMainActivity.a = 1;


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder( getContext() );
                builder.setTitle("회원가입을 종료 하시겠습니까?").setMessage("");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });







        return rootVIew;
    }
}