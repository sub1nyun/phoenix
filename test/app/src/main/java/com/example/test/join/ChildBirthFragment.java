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

public class ChildBirthFragment extends Fragment {
    LinearLayout linear_birth_no, linear_birth_yes;
    TextView tv_birth_no, tv_birth_yes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_child_birth, container, false);
        JoinMainActivity.go = 3;

        linear_birth_yes = rootview.findViewById(R.id.linear_birth_yes);
        linear_birth_no = rootview.findViewById(R.id.linear_birth_no);

        tv_birth_yes = rootview.findViewById(R.id.tv_birth_yes);
        tv_birth_no = rootview.findViewById(R.id.tv_birth_no);

        linear_birth_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_birth_yes.setBackground(getContext().getDrawable(R.drawable.select_btn));
                linear_birth_no.setBackground(getContext().getDrawable(R.drawable.border_round_gray));
                tv_birth_yes.setTextColor(Color.parseColor("#ffffff"));
                tv_birth_no.setTextColor(Color.parseColor("#707070"));
            }
        });
        linear_birth_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_birth_no.setBackground(getContext().getDrawable(R.drawable.select_btn));
                linear_birth_yes.setBackground(getContext().getDrawable(R.drawable.border_round_gray));
                tv_birth_no.setTextColor(Color.parseColor("#ffffff"));
                tv_birth_yes.setTextColor(Color.parseColor("#707070"));
            }
        });
        return rootview;
    }
}