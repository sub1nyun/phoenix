package com.example.test.my;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.test.MainActivity;
import com.example.test.R;
import com.example.test.my.MyFragment;

public class SettingFragment extends Fragment {
    ImageView setting_back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_setting, container, false);
        setting_back = rootView.findViewById(R.id.setting_back);

        setting_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFrag(new MyFragment());
            }
        });

        return rootView;
    }
}