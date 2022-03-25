package com.example.test.join;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.test.R;

import java.util.ArrayList;

public class RelationFragment extends Fragment {
    ImageView btn_back;
    LinearLayout btn_mother, btn_father, btn_sitter, btn_gmother, btn_gfather, btn_others;
    LinearLayout tv_mother, tv_father, tv_sitter, tv_gmother, tv_gfather, tv_others;
    Button btn_next;
    String btn = "엄마";
    int choose = 10000;
    ArrayList<LinearLayout> linear_list ;
    ArrayList<TextView> tv_list = new ArrayList<>();






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_relation, container, false);

        btn_back = rootView.findViewById(R.id.btn_back);
        btn_next = rootView.findViewById(R.id.btn_next);

        linear_list= new ArrayList<>();

        linear_list.add(rootView.findViewById(R.id.btn_mother));
        linear_list.add(rootView.findViewById(R.id.btn_father));
        linear_list.add(rootView.findViewById(R.id.btn_sitter));
        linear_list.add(rootView.findViewById(R.id.btn_gmother));
        linear_list.add(rootView.findViewById(R.id.btn_gfather));
        linear_list.add(rootView.findViewById(R.id.btn_others));

        tv_list.add(rootView.findViewById(R.id.tv_mother));
        tv_list.add(rootView.findViewById(R.id.tv_father));
        tv_list.add(rootView.findViewById(R.id.tv_sitter));
        tv_list.add(rootView.findViewById(R.id.tv_gmother));
        tv_list.add(rootView.findViewById(R.id.tv_gfather));
        tv_list.add(rootView.findViewById(R.id.tv_others));
        if( JoinMainActivity.vo.getFamily_rels() == null ){
            chg_linear(0);
        }else {
            chg_linear(choose);
        }

        ((JoinMainActivity)getActivity()).btn_back.setVisibility(View.VISIBLE);//뒤로가기버튼 생성


        linear_list.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = 0;
                    chg_linear(choose);
                    btn = "엄마";
            }
        });
        linear_list.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = 1;
                chg_linear(choose);
                btn = "아빠";
            }
        });
        linear_list.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = 2;
                chg_linear(choose);
                btn = "시터";
            }
        });
        linear_list.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = 3;
                chg_linear(choose);
                btn = "할머니";
            }
        });
        linear_list.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = 4;
                chg_linear(choose);
                btn = "할아버지";
            }
        });
        linear_list.get(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = 5;
                chg_linear(choose);
                btn = "가족";
            }
        });


        return rootView;
    }



    public void chg_linear(int num){
        String aa = "" ;
        for(int i=0; i<6; i++){
            if(i==num){
                JoinMainActivity.vo.setFamily_rels(btn);
                choose = num;
                linear_list.get(i).setBackground(getContext().getDrawable(R.drawable.select_btn));
                tv_list.get(i).setTextColor(Color.parseColor("#ffffff"));
            }else{
                linear_list.get(i).setBackground(getContext().getDrawable(R.drawable.border_round_gray));
                tv_list.get(i).setTextColor(Color.parseColor("#707070"));
            }
        }
    }



}