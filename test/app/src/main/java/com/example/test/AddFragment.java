package com.example.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.test.common.CommonVal;
import com.example.test.home.HomeActivity;
import com.example.test.join.JoinMainActivity;
import com.example.test.join.UserVO;
import com.example.test.my.BabyInfoVO;
import com.example.test.my.SettingActivity;

public class AddFragment extends Fragment {
    Button btn_new;
    TextView tv_logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_add, container, false);
        btn_new = rootView.findViewById(R.id.btn_new);
        tv_logout = rootView.findViewById(R.id.tv_logout);

        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JoinMainActivity.familyVO.setId(CommonVal.curuser.getId());
                Intent intent = new Intent(getContext(), JoinMainActivity.class);
                intent.putExtra("category", "new");
                startActivity(intent);
                ((MainActivity)getActivity()).finish();
            }
        });

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder_logout = new AlertDialog.Builder(getContext()).setTitle("로그아웃").setMessage("정말 로그아웃 하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //로그인 화면으로 이동
                                CommonVal.curuser = new UserVO();
                                CommonVal.baby_list.clear();
                                CommonVal.curbaby = new BabyInfoVO();
                                CommonVal.family_title.clear();
                                if(LoginActivity.editor != null) {
                                    LoginActivity.editor.remove("autologin");
                                    LoginActivity.editor.remove("id");
                                    LoginActivity.editor.remove("pw");
                                    LoginActivity.editor.apply();
                                }
                                Intent intent = new Intent(getContext(), HomeActivity.class);
                                startActivity(intent);
                                ((MainActivity) getActivity()).finish();
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
        return rootView;
    }
}