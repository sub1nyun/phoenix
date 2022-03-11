package com.example.test.join;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.test.MainActivity;
import com.example.test.R;

public class RelationFragment extends Fragment {
    ImageView btn_back;
    Button btn_mother, btn_father, btn_family, btn_gmother, btn_gfather, btn_next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_relation, container, false);
        JoinMainActivity.go = 7;

        btn_back = rootView.findViewById(R.id.btn_back);
        btn_mother = rootView.findViewById(R.id.btn_mother);
        btn_father = rootView.findViewById(R.id.btn_father);
        btn_family = rootView.findViewById(R.id.btn_family);
        btn_gmother = rootView.findViewById(R.id.btn_gmother);
        btn_gfather = rootView.findViewById(R.id.btn_gfather);
        btn_next = rootView.findViewById(R.id.btn_next);






        btn_mother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_father.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_gmother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_gfather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        return rootView;
    }
}