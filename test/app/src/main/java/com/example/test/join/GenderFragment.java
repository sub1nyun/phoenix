package com.example.test.join;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.test.R;

import java.util.ArrayList;


public class GenderFragment extends Fragment {
    LinearLayout linear_woman, linear_man, linear_random;
    ArrayList<LinearLayout> linear_list;
    ArrayList<TextView> tv_list = new ArrayList<>();
    TextView tv_woman, tv_man, tv_random;
    String btn = "남자";
    int choose;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_gender, container, false);


        linear_man = rootView.findViewById(R.id.linear_man);
        linear_woman = rootView.findViewById(R.id.linear_woman);
        linear_random = rootView.findViewById(R.id.linear_random);

        tv_man = rootView.findViewById(R.id.tv_man);
        tv_woman = rootView.findViewById(R.id.tv_woman);
        tv_random = rootView.findViewById(R.id.tv_random);

        linear_list= new ArrayList<>();

        linear_list.add(rootView.findViewById(R.id.linear_man));
        linear_list.add(rootView.findViewById(R.id.linear_woman));
        linear_list.add(rootView.findViewById(R.id.linear_random));
        tv_list.add(rootView.findViewById(R.id.tv_man));
        tv_list.add(rootView.findViewById(R.id.tv_woman));
        tv_list.add(rootView.findViewById(R.id.tv_random));


        if( JoinMainActivity.vo.getFamily_rels() == null ){
            chg_linear(0);
        }else {
            chg_linear(choose);
        }

        JoinMainActivity.babyInfoVO.setBaby_gender("남아");
        linear_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_man.setBackground(getContext().getDrawable(R.drawable.select_btn));
                linear_woman.setBackground(getContext().getDrawable(R.drawable.border_round_gray));
                linear_random.setBackground(getContext().getDrawable(R.drawable.border_round_gray));
                tv_man.setTextColor(Color.parseColor("#ffffff"));
                tv_woman.setTextColor(Color.parseColor("#707070"));
                tv_random.setTextColor(Color.parseColor("#707070"));
                btn = "남아";
                choose = 0;
                chg_linear(choose);
                JoinMainActivity.babyInfoVO.setBaby_gender("남아");
            }
        });
        linear_woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_man.setBackground(getContext().getDrawable(R.drawable.border_round_gray));
                linear_woman.setBackground(getContext().getDrawable(R.drawable.select_btn));
                linear_random.setBackground(getContext().getDrawable(R.drawable.border_round_gray));
                tv_man.setTextColor(Color.parseColor("#707070"));
                tv_woman.setTextColor(Color.parseColor("#ffffff"));
                tv_random.setTextColor(Color.parseColor("#707070"));
                btn = "여아";
                choose = 1;
                chg_linear(choose);
                JoinMainActivity.babyInfoVO.setBaby_gender("여아");
            }
        });
        linear_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_man.setBackground(getContext().getDrawable(R.drawable.border_round_gray));
                linear_woman.setBackground(getContext().getDrawable(R.drawable.border_round_gray));
                linear_random.setBackground(getContext().getDrawable(R.drawable.select_btn));
                tv_man.setTextColor(Color.parseColor("#707070"));
                tv_woman.setTextColor(Color.parseColor("#707070"));
                tv_random.setTextColor(Color.parseColor("#ffffff"));
                btn = "아직모름";
                choose = 2;
                chg_linear(choose);
                JoinMainActivity.babyInfoVO.setBaby_gender("아직모름");

            }
        });



        return rootView;
    }//onCreateView()


    public void chg_linear(int num){
        String aa = "" ;
        for(int i=0; i<2; i++){
            if(i==num){
                //int rtn_index = num;
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