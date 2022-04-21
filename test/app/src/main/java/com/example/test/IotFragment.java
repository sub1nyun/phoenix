package com.example.test;

import static android.Manifest.permission.RECORD_AUDIO;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.test.iot.MusicFragment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IotFragment extends Fragment {
    ImageView iot_capture, iot_recode, iot_white_noise;
    WebView iot_cctv;
    Handler handler = new Handler(Looper.getMainLooper());

    private static final String LOG_TAG = "AudioRecordTest";
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 200;
    private static String fileName;

    private MediaRecorder recorder;

    private boolean isRecording = false;    // 현재 녹음 상태를 확인하기 위함.
    private Uri audioUri = null; // 오디오 파일 uri

    private String recordPermission = Manifest.permission.RECORD_AUDIO;
    private int PERMISSION_CODE = 21;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_iot, container, false);
        thtest thtest;

        thtest = new thtest(handler);
        thtest.start();


        iot_capture = rootView.findViewById(R.id.iot_capture);
        iot_recode = rootView.findViewById(R.id.iot_recode);
        iot_white_noise = rootView.findViewById(R.id.iot_white_noise);
        iot_cctv = rootView.findViewById(R.id.iot_cctv);

        iot_recode.setColorFilter(Color.parseColor("#000000"));

        iot_cctv.setWebViewClient(new WebViewClient());
        iot_cctv.getSettings().setLoadWithOverviewMode(true);
        iot_cctv.getSettings().setUseWideViewPort(true);



        int perissionCheck = ContextCompat.checkSelfPermission(getContext(), RECORD_AUDIO);

        if(perissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "오디오 권한 있음", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(), "이건 없음", Toast.LENGTH_SHORT).show();
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), RECORD_AUDIO)) {
                Toast.makeText(getContext(), "오디오 권한 설명 필요함", Toast.LENGTH_SHORT).show();
            }else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{RECORD_AUDIO}, REQUEST_AUDIO_PERMISSION_CODE);
            }
        }

        /*fileName = getContext().getExternalCacheDir().getAbsolutePath();
        fileName += "/audiorecordtest.mp4";
        Log.d("asd", "onCreateView: " + fileName);*/
        /*File scard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, "recorded.mp3"d);
        fileName = file.getAbsolutePath();*/

        WebSettings webSettings = iot_cctv.getSettings();
        webSettings.setJavaScriptEnabled(true);

        /*iot_cctv.loadData("<html><head><style type='text/css'>body{margin:auto auto;text-align:center;} " +
                        "img{width:100%25;} div{overflow: hidden;} </style></head>" +
                        "<body><div><img src='http://192.168.0.92:8000/stream.mjpeg'/></div></body></html>",
                "text/html", "UTF-8");
        iot_cctv.reload();*/

        iot_cctv.loadUrl("http://192.168.0.18:8000");

        iot_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cctv 캡쳐
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            thtest.send("OFF");
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });


        iot_recode.setOnClickListener(v -> {
            //음성녹화 녹화
            if(isRecording){
                isRecording = false;
                stopRecording();
            }else {
                if(checkAudioPermission()) {
                    isRecording = true;
                    startRecording();
                }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_AUDIO_PERMISSION_CODE:
                if(grantResults.length >0) {
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getContext(), "Audio 권한을 사용자가 승인함", Toast.LENGTH_SHORT).show();
                    }else if(grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(getContext(), "Audio 권한을 사용자가 거부함", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "아 진짜로~~", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
        }
    }

    private boolean checkAudioPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), recordPermission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{recordPermission}, PERMISSION_CODE);
            return false;
        }
    }

    // 녹음 시작
    private void startRecording() {
        //파일의 외부 경로 확인
        String recordPath = getContext().getExternalFilesDir("/").getAbsolutePath();
        // 파일 이름 변수를 현재 날짜가 들어가도록 초기화. 그 이유는 중복된 이름으로 기존에 있던 파일이 덮어 쓰여지는 것을 방지하고자 함.
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        fileName = recordPath + "/" +"RecordExample_" + timeStamp + "_"+"audio.mp4";

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        recorder.setOutputFile(fileName);

        try {
            recorder.prepare();

            Toast.makeText(getContext(), "녹음 시작", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        recorder.start();

    }

    // 녹음 종료
    private void stopRecording() {
        // 녹음 종료 종료

            recorder.stop();
            recorder.release();
            recorder = null;
            audioUri = Uri.parse(fileName);
            Toast.makeText(getContext(), "녹음 종료" + audioUri, Toast.LENGTH_SHORT).show();
            Log.d("asd", "stopRecording: " + audioUri);
        //Toast.makeText(getContext(), "if 안탐", Toast.LENGTH_SHORT).show();
    }
}