package com.example.test.diary;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.test.LoginActivity;
import com.example.test.MainActivity;
import com.example.test.R;
import com.example.test.SplashActivity;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.example.test.join.JoinMainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    Button btn_cancel, btn_save;
    ImageView btn_del;
    TextView tv_start,tv_end, tv_date, tv_state;
    EditText edt_memo,edt_amount, edt_temp;
    TimePickerDialog.OnTimeSetListener callbackMethod1, callbackMethod2;
    LinearLayout linear_start, linear_end, linear_amount, linear_temp, linear_many;
    ArrayList<Button> btns = new ArrayList<>();

    Gson gson = new Gson();
    String[] time_arr1;
    String[] time_arr2;
    int result = 1;

    String[] amtCategoty = {"분유", "이유식", "물", "투약", "간식", "모유"};

    private ActivityResultLauncher<Void> overlayPermissionLauncher;

    NotificationManager manager;

    //하나의 알림당 하나의 채널이 필요함
    String CHANNEL_ID1 = "channer1";
    String CHANNEL_NAME1 = "channer1";
    String CHANNEL_ID2 = "channer2";
    String CHANNEL_NAME2 = "channer2";
    String CHANNEL_ID3 = "channer3";
    String CHANNEL_NAME3 = "channer3";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_save = findViewById(R.id.btn_save);
        btn_del = findViewById(R.id.btn_del);
        tv_start = findViewById(R.id.tv_start);
        tv_end = findViewById(R.id.tv_end);
        tv_state = findViewById(R.id.tv_state);

        btns.add(findViewById(R.id.btn1));
        btns.add(findViewById(R.id.btn2));
        btns.add(findViewById(R.id.btn3));
        btns.add(findViewById(R.id.btn4));

        tv_date = findViewById(R.id.tv_date);
        edt_amount = findViewById(R.id.edt_amount);
        edt_temp = findViewById(R.id.edt_temp);
        edt_memo = findViewById(R.id.edt_memo);

        linear_start = findViewById(R.id.linear_start);
        linear_end = findViewById(R.id.linear_end);
        linear_amount = findViewById(R.id.linear_amount);
        linear_temp = findViewById(R.id.linear_temp);
        linear_many = findViewById(R.id.linear_many);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edt_memo.getWindowToken(), 0);


        //어댑터에서 dto 받기
        Intent intent = getIntent();
        DiaryVO dto = (DiaryVO) intent.getSerializableExtra("dto");
        boolean is_info=false;
        if(intent.getSerializableExtra("is_info")!=null){
            is_info = (Boolean) intent.getSerializableExtra("is_info");
        }



        if((dto.getBaby_category()).equals("모유")){
            //linear_amount.setVisibility(View.GONE);
            linear_temp.setVisibility(View.GONE);
            btns.get(0).setText("왼쪽");
            btns.get(1).setText("오른쪽");
            btns.get(2).setText("둘다");
            btns.get(3).setText("모름");
        }else if((dto.getBaby_category()).equals("분유")){
            linear_temp.setVisibility(View.GONE);
            linear_many.setVisibility(View.GONE);
        }else if((dto.getBaby_category()).equals("이유식")){
            linear_temp.setVisibility(View.GONE);
            linear_many.setVisibility(View.GONE);
        }else if((dto.getBaby_category()).equals("기저귀")){
            linear_amount.setVisibility(View.GONE);
            linear_end.setVisibility(View.GONE);
            linear_temp.setVisibility(View.GONE);
            btns.get(0).setText("대변");
            btns.get(1).setText("소변");
            btns.get(2).setText("둘다");
            btns.get(3).setVisibility(View.GONE);
        }else if((dto.getBaby_category()).equals("수면")){
            linear_amount.setVisibility(View.GONE);
            linear_temp.setVisibility(View.GONE);
            btns.get(0).setText("낮잠");
            btns.get(1).setText("밤잠");
            btns.get(2).setVisibility(View.GONE);
            btns.get(3).setVisibility(View.GONE);
        }else if((dto.getBaby_category()).equals("목욕")){
            linear_amount.setVisibility(View.GONE);
            linear_temp.setVisibility(View.GONE);
            linear_many.setVisibility(View.GONE);
        }else if((dto.getBaby_category()).equals("체온")){
            linear_end.setVisibility(View.GONE);
            linear_amount.setVisibility(View.GONE);
            linear_many.setVisibility(View.GONE);
        }else if((dto.getBaby_category()).equals("물")){
            linear_temp.setVisibility(View.GONE);
            linear_many.setVisibility(View.GONE);
        }else if((dto.getBaby_category()).equals("투약")){
            linear_temp.setVisibility(View.GONE);
            linear_many.setVisibility(View.GONE);
        }else if((dto.getBaby_category()).equals("간식")){
            linear_temp.setVisibility(View.GONE);
            linear_many.setVisibility(View.GONE);
        }

        //초기 세팅
        tv_state.setText(dto.getBaby_category());
        String time = getNowtime();



        if(is_info){
            time_arr1 = (dto.getStart_time()).split(":");
            if(dto.getEnd_time() == null){
                time_arr2 = (time).split(":");
            }else {
                time_arr2 = (dto.getEnd_time()).split(":");
            }
            for(int i=0; i<amtCategoty.length; i++){
                if(dto.getBaby_category().equals(amtCategoty[i])){
                    if(dto.getAmount() == 0){
                        edt_amount.setText("");
                    }else {
                        edt_amount.setText(dto.getAmount() + "");
                    }
                }
            }
            if(dto.getBaby_category().equals("체온")){
                edt_temp.setText(dto.getTemperature()+"");
            }
            edt_memo.setText(dto.getMemo());
        }else{
            time_arr1 = (time).split(":");
            time_arr2 = (time).split(":");
            dto.setStart_time(time);
            dto.setEnd_time(time);
            btn_del.setVisibility(View.GONE);
        }

        tv_date.setText(dto.getDiary_date()+"");
        tv_start.setText(dto.getStart_time()+"");
        tv_end.setText(dto.getEnd_time()+"");

        for(int i=0 ; i<btns.size(); i++){
            if(btns.get(i).getText().equals(dto.getDiary_type())){
                changeBtn(i);
            }
        }



        callbackMethod1 = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                dto.setStart_time(String.format("%02d", hourOfDay ) +":"+ String.format("%02d", minute ));
                tv_start.setText(String.format("%02d", hourOfDay ) +":"+ String.format("%02d", minute ));
                time_arr1[0] = String.format("%02d", hourOfDay );
                time_arr1[1] = String.format("%02d", minute );
            }
        };

        callbackMethod2 = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                dto.setEnd_time(String.format("%02d", hourOfDay ) +":"+ String.format("%02d", minute ));
                tv_end.setText(String.format("%02d", hourOfDay ) +":"+ String.format("%02d", minute ));
                time_arr2[0] = String.format("%02d", hourOfDay );
                time_arr2[1] = String.format("%02d", minute );
            }
        };

        tv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionOverlay(DetailActivity.this);
                if(! Settings.canDrawOverlays(DetailActivity.this)) {
                    Toast.makeText(DetailActivity.this, "알림을 받고싶다면 권한을 허용해주세요.", Toast.LENGTH_SHORT).show();
                }
                TimePickerDialog dialog = new TimePickerDialog(DetailActivity.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar, callbackMethod1, Integer.parseInt(time_arr1[0]), Integer.parseInt(time_arr1[1]), false);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            }
        });

        tv_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(DetailActivity.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar, callbackMethod2, Integer.parseInt(time_arr2[0]), Integer.parseInt(time_arr2[1]), false);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //저장버튼
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isSucc = false;
                dto.setMemo(edt_memo.getText() + "");
                //amount가 필요한 카테고리들을 걸러서 설정
                for (int i = 0; i < amtCategoty.length; i++) {
                    if (dto.getBaby_category().equals(amtCategoty[i])) {
                        if (edt_amount.getText().toString().equals("")) {
                        } else {
                            dto.setAmount(Double.parseDouble(edt_amount.getText() + ""));
                        }
                    }
                }
                //체온일때 빈칸이면 입력해달라 하고 비정상 체온이면 알람가게 정상이면 그대로 진행
                if (dto.getBaby_category().equals("체온")) {
                    if (edt_temp.getText().toString().equals("")) {
                        result = 0;
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                        builder.setTitle("체온을 입력해주세요").setMessage("");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    } else if(35.5>Double.parseDouble(edt_temp.getText().toString()) || 38<Double.parseDouble(edt_temp.getText().toString())){
                        showCheck();
                        result = 1;
                        dto.setTemperature(Double.parseDouble(edt_temp.getText() + ""));
                    }else {
                        result = 1;
                        dto.setTemperature(Double.parseDouble(edt_temp.getText() + ""));
                    }
                    dto.setEnd_time(dto.getStart_time());
                //기저귀일때 종료시간 세팅
                } else if (dto.getBaby_category().equals("기저귀")) {
                    dto.setEnd_time(dto.getStart_time());
                }
                //시작시간 끝시간 차이구하기
                long diffMin = 0;
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                    Date date1 = dateFormat.parse(dto.getEnd_time());
                    Date date2 = dateFormat.parse(dto.getStart_time());
                    diffMin = (date1.getTime() - date2.getTime()) / 60000;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //시작시간보다 끝시간이 전이면 시간설정 잘못됐다고 알리기
                if(diffMin<0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                    builder.setTitle("시간이 잘못 설정되었습니다.").setMessage("");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id) { }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                //시간 설정이 잘되었다면
                }else{
                    //수정일때
                    if (intent.getSerializableExtra("is_info") != null && result == 1) {
                        AskTask task = new AskTask(CommonVal.httpip, "update.di");
                        String dtogson = gson.toJson(dto);
                        task.addParam("dto", dtogson);
                        InputStream in = CommonMethod.excuteGet(task);
                        isSucc = gson.fromJson(new InputStreamReader(in), Boolean.class);
                    //새로운 데이터 저장일때
                    } else if (result == 1) {
                        AskTask task = new AskTask(CommonVal.httpip, "insert.di");
                        String dtogson = gson.toJson(dto);
                        task.addParam("dto", dtogson);
                        InputStream in = CommonMethod.excuteGet(task);
                        isSucc = gson.fromJson(new InputStreamReader(in), Boolean.class);
                    }
                    //수정이나 저장처리가 잘 되었다면
                    if (isSucc) {
                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            //현재 시간보다 설정한 시작시간이 후라면 지정된 시간에 알림 울리게
                            Date now = dateFormat.parse(dateFormat.format(new Date()));
                            Date setTime = dateFormat.parse(dto.getDiary_date()+" "+dto.getStart_time());
                            if(setTime.getTime() > now.getTime()){
                                if(Settings.canDrawOverlays(DetailActivity.this)) {
                                    Calendar c = Calendar.getInstance();
                                    c.setTime(setTime);
                                    setAlarm(c, dto.getBaby_category());
                                }else{
                                    Toast.makeText(DetailActivity.this, "권한을 허용해주세요.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        //현재페이지 날짜 가지고 메인액티로 돌아가자
                        Intent intent = new Intent();
                        intent.putExtra("pageDate", dto.getDiary_date());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }
        });

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder_cancel = new AlertDialog.Builder(DetailActivity.this).setTitle("취소").setMessage("정말 삭제하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AskTask task = new AskTask(CommonVal.httpip,"delete.di");
                                String dtogson = gson.toJson(dto);
                                task.addParam("dto", dtogson);
                                InputStream in = CommonMethod.excuteGet(task);
                                Boolean isSucc = gson.fromJson(new InputStreamReader(in), Boolean.class);
                                //Log.d("isSucc : ", isSucc+"");
                                Intent intent = new Intent();
                                intent.putExtra("pageDate",dto.getDiary_date());
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alertDialog = builder_cancel.create();
                alertDialog.show();

            }
        });

        btns.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBtn(0);
                dto.setDiary_type(btns.get(0).getText()+"");
            }
        });
        btns.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBtn(1);
                dto.setDiary_type(btns.get(1).getText()+"");
            }
        });
        btns.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBtn(2);
                dto.setDiary_type(btns.get(2).getText()+"");
            }
        });
        btns.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBtn(3);
                dto.setDiary_type(btns.get(3).getText()+"");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setAlarm(Calendar calendar, String cate) {
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        for(int i=0; i< amtCategoty.length; i++){
            if(cate.equals(amtCategoty[i]))
                intent.putExtra("cate" , cate.equals("투약") ? "아이에게 투약할 시간입니다." : cate + "를(을) 먹일 시간입니다.");//리시버에 데이터 보내기 테스트
        }
        if(cate.equals("체온"))
            intent.putExtra("cate" , cate + "을 측정할 시간입니다.");//리시버에 데이터 보내기 테스트
        else if(cate.equals("기저귀"))
            intent.putExtra("cate" , cate + "를 확인할 시간입니다.");//리시버에 데이터 보내기 테스트
        else if(cate.equals("수면"))
            intent.putExtra("cate" , "아이를 재울 시간입니다.");//리시버에 데이터 보내기 테스트
        else if(cate.equals("목욕"))
            intent.putExtra("cate" , "아이가 목욕할 시간입니다.");//리시버에 데이터 보내기 테스트
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(),
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT|PendingIntent.FLAG_UPDATE_CURRENT
        );
        // 알람 설정
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                pendingIntent
        );
    }

    private final int OVERRAY_REQ_CODE = 1111;
    public  boolean checkPermissionOverlay(Context context) {
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && Settings.canDrawOverlays(context) ){ //<=권한이 허용되었는지를 체크
            return true;
        }else{
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent,  OVERRAY_REQ_CODE);
        }
        return false;
    }

    private void showCheck() {
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            if(manager.getNotificationChannel(CHANNEL_ID2) == null){
                manager.createNotificationChannel(new NotificationChannel(
                        CHANNEL_ID2,CHANNEL_NAME2,NotificationManager.IMPORTANCE_DEFAULT
                ));
            }
            builder = new NotificationCompat.Builder(this,CHANNEL_ID2);
        }else{
            builder = new NotificationCompat.Builder(this);
        }
        Intent intent = new Intent(DetailActivity.this , LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent
                .getActivity(DetailActivity.this , 1004 ,
                        intent ,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentTitle("베시시 알림");
        builder.setContentText("아기의 체온의 정상번위를 벗어났습니다.");
        builder.setSubText("(정상 범위: 35.5℃ ~ 38℃)");
        builder.setSmallIcon(android.R.drawable.ic_menu_view);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);
        Notification noti = builder.build();

        manager.notify(2,noti);

    }



    public String getNowtime(){
        //현재 시간
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String getTime = dateFormat.format(date);
        return  getTime;
    }

    public void changeBtn(int num){
        for(int i=0; i<btns.size(); i++){
            if(i==num){
                btns.get(i).setBackground(ContextCompat.getDrawable(this, R.drawable.border_round_gray_fill));
//btns.get(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#D1D1D1")));
            }else{
                btns.get(i).setBackground(ContextCompat.getDrawable(this, R.drawable.border_round_gray));
            }
        }
    }
}