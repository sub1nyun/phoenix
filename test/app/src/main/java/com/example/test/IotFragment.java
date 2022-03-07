package com.example.test;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.test.iot.MusicFragment;

public class IotFragment extends Fragment {
    ImageView iot_capture, iot_recode, iot_white_noise;
    WebView iot_cctv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_iot, container, false);
        iot_capture = rootView.findViewById(R.id.iot_capture);
        iot_recode = rootView.findViewById(R.id.iot_recode);
        iot_white_noise = rootView.findViewById(R.id.iot_white_noise);
        iot_cctv = rootView.findViewById(R.id.iot_cctv);

        iot_recode.setColorFilter(Color.parseColor("#000000"));

        iot_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cctv 캡쳐
            }
        });

        iot_recode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cctv 녹화
            }
        });

        iot_white_noise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFrag(new MusicFragment());
            }
        });

        return rootView;
    }
}