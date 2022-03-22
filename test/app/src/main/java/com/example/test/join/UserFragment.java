package com.example.test.join;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.test.R;

public class UserFragment extends Fragment {
    EditText edt_id ,edt_pw, edt_pwchk;
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
            JoinMainActivity.go = 7;
        }

        edt_id = rootVIew.findViewById(R.id.edt_id);
        edt_pw = rootVIew.findViewById(R.id.edt_pw);
        edt_pwchk = rootVIew.findViewById(R.id.edt_pwchk);

        join_kakao = rootVIew.findViewById(R.id.join_kakao);
        join_naver = rootVIew.findViewById(R.id.join_naver);

        join_kakao.setOnClickListener(v -> {
            Toast.makeText(getContext(), "카카오 눌림", Toast.LENGTH_SHORT).show();
        });

        join_naver.setOnClickListener(v -> {
            Toast.makeText(getContext(), "네이버도 눌림", Toast.LENGTH_SHORT).show();
        });



        edt_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                JoinMainActivity.vo.setId( edt_id.getText().toString() );
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                JoinMainActivity.vo.setPw( edt_pw.getText().toString() );
            }
        });
        edt_pwchk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                JoinMainActivity.vo.setPw_chk( edt_pwchk.getText().toString() );
            }
        });







        return rootVIew;
    }//onCreateView











}