package com.example.test;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

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

        iot_cctv.setWebViewClient(new WebViewClient());
        iot_cctv.getSettings().setLoadWithOverviewMode(true);
        iot_cctv.getSettings().setUseWideViewPort(true);

        WebSettings webSettings = iot_cctv.getSettings();
        webSettings.setJavaScriptEnabled(true);

        /*iot_cctv.loadData("<html><head><style type='text/css'>body{margin:auto auto;text-align:center;} " +
                        "img{width:100%25;} div{overflow: hidden;} </style></head>" +
                        "<body><div><img src='http://192.168.0.92:8000/stream.mjpeg'/></div></body></html>",
                "text/html", "UTF-8");
        iot_cctv.reload();*/

        iot_cctv.loadUrl("http://192.168.0.92:8000");

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