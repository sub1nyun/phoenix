package com.example.test.join;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.MainActivity;
import com.example.test.R;

import java.util.ArrayList;

public class RelationFragment extends Fragment {
    ImageView btn_back;
    LinearLayout btn_mother, btn_father, btn_sitter, btn_gmother, btn_gfather, btn_others;
    LinearLayout tv_mother, tv_father, tv_sitter, tv_gmother, tv_gfather, tv_others;
    Button btn_next;

    ArrayList<LinearLayout> linear_list = new ArrayList<>();
    ArrayList<TextView> tv_list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_relation, container, false);
        JoinMainActivity.go = 7;

        btn_back = rootView.findViewById(R.id.btn_back);
        btn_next = rootView.findViewById(R.id.btn_next);

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


        linear_list.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chg_linear(0);
            }
        });
        linear_list.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chg_linear(1);
            }
        });
        linear_list.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chg_linear(2);
            }
        });
        linear_list.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chg_linear(3);
            }
        });
        linear_list.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chg_linear(4);
            }
        });
        linear_list.get(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chg_linear(5);
            }
        });


        return rootView;
    }

    public void chg_linear(int num){
        for(int i=0; i<6; i++){
            if(i==num){
                linear_list.get(i).setBackground(getContext().getDrawable(R.drawable.select_btn));
                tv_list.get(i).setTextColor(Color.parseColor("#ffffff"));
            }else{
                linear_list.get(i).setBackground(getContext().getDrawable(R.drawable.border_round_gray));
                tv_list.get(i).setTextColor(Color.parseColor("#707070"));
            }
        }
    }
}