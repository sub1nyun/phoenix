package com.example.test;

import static android.Manifest.permission.RECORD_AUDIO;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class IotFragment extends Fragment {
    ImageView iot_capture, iot_recode, iot_white_noise, iot_biking;
    WebView iot_cctv;
    Gson gson = new Gson();

    MediaRecorder recorder;
    String filename;
    String[] checkList = {"음성녹음", "파일선택"};
    int select = 0;
    int cnt = 0;

    private static final String LOG_TAG = "AudioRecordTest";
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 200;

    private String recordPermission = Manifest.permission.RECORD_AUDIO;
    private int PERMISSION_CODE = 21;

    private boolean isRecording = true;    // 현재 녹음 상태를 확인하기 위함.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_iot, container, false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        checkDangerousPermissions();

        iot_capture = rootView.findViewById(R.id.iot_capture);
        iot_recode = rootView.findViewById(R.id.iot_recode);
        iot_white_noise = rootView.findViewById(R.id.iot_white_noise);
        iot_cctv = rootView.findViewById(R.id.iot_cctv);
        iot_biking = rootView.findViewById(R.id.iot_biking);



        iot_capture.setColorFilter(getResources().getColor(R.color.main));
        iot_white_noise.setColorFilter(getResources().getColor(R.color.main));

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

        /*if(Build.VERSION.SDK_INT >= 19){
            iot_cctv.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else{
            iot_cctv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        WebSettings webSettings = iot_cctv.getSettings();
        webSettings.setJavaScriptEnabled(true);*/

        File sdcard = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            sdcard = ((MainActivity) getActivity()).getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        }else{
            sdcard = Environment.getExternalStorageDirectory();
        }
        String uuid = UUID.randomUUID().toString();
        File file = new File(sdcard, uuid + ".mp3");
        filename = file.getAbsolutePath();
        Log.d("태그","filename"+filename);

            ///storage/emulated/0/Android/data/com.example.test/files/Music/aaceea38-0a51-4f8f-a584-370d277f3690.mp3

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
                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AskTask askTask = new AskTask(CommonVal.httpip, "iot_cap.io");
                        askTask.addParam("code", "pic");
                        CommonMethod.excuteGet(askTask);

                        AskTask img_task = new AskTask(CommonVal.httpip, "base_to_img.io");
                        InputStream in = CommonMethod.excuteGet(img_task);
                        String precode = gson.fromJson(new InputStreamReader(in), String.class);

                        byte[] encode = Base64.decode(precode, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(encode, 0, encode.length);
                        Log.d("asd", "run: " + bitmap);
                        //iot_white_noise.setImageBitmap(bitmap);
                    }
                }).start();*/
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                Date time = new Date();
                String current_tile = sdf.format(time);

                Request_Capture(iot_cctv, current_tile+"_capture");
            }
        });

        iot_recode.setOnClickListener(v -> {
            //음성 녹음
            /*if(isRecording) {
                isRecording = false;
                recordAudio();
                iot_recode.setImageResource(R.drawable.iot_mic_stop);
            }else {
                if(checkAudioPermission()) {
                    isRecording = true;
                    stopRecording();
                    sendMpeg(filename);
                    iot_recode.setImageResource(R.drawable.icon_rec);
                }
            }*/
            if(cnt == 0){
                ChoseDialog(rootView);
            } else{
                if(checkAudioPermission()){
                    isRecording = true;
                    stopRecording();
                    sendMpeg(filename);
                }
                iot_recode.setImageResource(R.drawable.play);
                cnt = 0;
            }
        });

        iot_white_noise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent_upload = new Intent();
                intent_upload.setType("audio/mpeg");
                intent_upload.setAction(Intent.ACTION_GET_CONTENT);
                getActivity().startActivityForResult(intent_upload, 1001);*/
                AskTask task = new AskTask(CommonVal.httpip, "biking.io");
                task.addParam("biking", "biking");
                CommonMethod.excuteGet(task);
            }
        });

        iot_biking.setOnClickListener(v -> {
            AskTask bikingtask = new AskTask(CommonVal.httpip,"biking.io");
            bikingtask.addParam("biking", "biking");
            CommonMethod.excuteGet(bikingtask);


        });


        return rootView;
    }

    public void sendMpeg(String realPath) {

        File file = new File(realPath);
        byte[] data = new byte[(int) file.length()];

        try (FileInputStream stream = new FileInputStream(file)) {
            stream.read(data, 0, data.length);

        } catch (Throwable e) {
            e.printStackTrace();
        }
        AskTask askTask = new AskTask(CommonVal.httpip,"music");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            askTask.addParam("musicfile", Base64.getUrlEncoder().encodeToString(data));
            //askTask.fileData = java.util.Base64.getUrlEncoder().encodeToString(data);
            askTask.execute();
        }
    }

    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_MEDIA_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(getActivity(), permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissions[0])) {
                //Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(getActivity(), permissions, 1);
            }
        }
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

    public void recordAudio(){
        recorder = new MediaRecorder();

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

        recorder.setOutputFile(filename);

        try {
            recorder.prepare();
        }catch (Exception e) {
            e.printStackTrace();
        }
        recorder.start();
        Toast.makeText(getActivity(), "녹음 시작", Toast.LENGTH_SHORT).show();

    }

    public void stopRecording(){
        if(recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;

            Toast.makeText(getContext(), "녹음 중지임", Toast.LENGTH_SHORT).show();
        }
    }

    public void Request_Capture(WebView webView, String title){
        if(webView == null){
            return;
        }
        webView.setDrawingCacheEnabled(true);
        webView.buildDrawingCache();
        Bitmap bitmap = webView.getDrawingCache();
        FileOutputStream fos;

        File uploadFolder = Environment.getExternalStoragePublicDirectory("/DCIM/Camera/");

        if(!uploadFolder.exists()) uploadFolder.mkdir();

        String str_path = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/DCIM/Camera";
        try{
            fos = new FileOutputStream(str_path + title + ".jpg");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
        }catch (Exception e){
            e.printStackTrace();
            //bitmap.recycle();
        }finally {
            webView.setDrawingCacheEnabled(false);
            bitmap = null;
        }
    }

    public void ChoseDialog(View view){
        ContextThemeWrapper cw = new ContextThemeWrapper(getContext(), R.style.AlertDialogTheme);
        AlertDialog dialog = new AlertDialog.Builder(cw).setTitle("선택").setSingleChoiceItems(checkList, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                select = which;
            }
        }).setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(select == 0){
                    if(isRecording) {
                        isRecording = false;
                        recordAudio();
                        iot_recode.setImageResource(R.drawable.iot_mic_stop);
                        iot_recode.setColorFilter(getResources().getColor(R.color.main));
                    }
                    cnt = 1;
                } else{
                    Intent intent_upload = new Intent();
                    intent_upload.setType("audio/mpeg");
                    intent_upload.setAction(Intent.ACTION_GET_CONTENT);
                    getActivity().startActivityForResult(intent_upload, 1001);
                }
            }
        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setCancelable(false).show();
    }
}