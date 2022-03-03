package com.example.test.my;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.test.MainActivity;
import com.example.test.R;

public class MyFragment extends Fragment {
    Button my_grow;
    ImageView my_setting, my_detail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_my, container, false);
        my_grow = rootView.findViewById(R.id.my_grow);
        my_setting = rootView.findViewById(R.id.my_setting);
        my_detail = rootView.findViewById(R.id.my_detail);

        my_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFrag(new SettingFragment());
            }
        });

        my_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFrag(new EditFragment());
            }
        });

        return rootView;
    }
}