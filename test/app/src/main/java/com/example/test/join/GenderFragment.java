package com.example.test.join;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.R;

import java.util.ArrayList;


public class GenderFragment extends Fragment {
    LinearLayout linear_woman, linear_man, linear_random;
    TextView tv_woman, tv_man, tv_random;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_gender, container, false);
        JoinMainActivity.go = 6;

        linear_man = rootView.findViewById(R.id.linear_man);
        linear_woman = rootView.findViewById(R.id.linear_woman);
        linear_random = rootView.findViewById(R.id.linear_random);

        tv_man = rootView.findViewById(R.id.tv_man);
        tv_woman = rootView.findViewById(R.id.tv_woman);
        tv_random = rootView.findViewById(R.id.tv_random);

        linear_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_man.setBackground(getContext().getDrawable(R.drawable.select_btn));
                linear_woman.setBackground(getContext().getDrawable(R.drawable.border_round_gray));
                linear_random.setBackground(getContext().getDrawable(R.drawable.border_round_gray));
                tv_man.setTextColor(Color.parseColor("#ffffff"));
                tv_woman.setTextColor(Color.parseColor("#707070"));
                tv_random.setTextColor(Color.parseColor("#707070"));
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
            }
        });



        return rootView;
    }
}