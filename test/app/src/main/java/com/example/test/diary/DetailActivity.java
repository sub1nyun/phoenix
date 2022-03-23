package com.example.test.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    Button btn_cancel, btn_save, btn_del, btn1, btn2, btn3, btn4;
    TextView tv_start,tv_end, tv_date, tv_state, tv_amount, tv_temp;
    EditText edt_memo;
    TimePickerDialog.OnTimeSetListener callbackMethod1, callbackMethod2;
    LinearLayout linear_start, linear_end, linear_amount, linear_temp, linear_many;

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        btn_cancel = findViewById(R.id.btn_cancel);
        btn_save = findViewById(R.id.btn_save);
        btn_del = findViewById(R.id.btn_del);
        tv_start = findViewById(R.id.tv_start);
        tv_end = findViewById(R.id.tv_end);
        edt_memo = findViewById(R.id.edt_memo);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);

        tv_date = findViewById(R.id.tv_date);
        tv_state = findViewById(R.id.tv_state);
        tv_amount = findViewById(R.id.tv_amount);
        tv_temp = findViewById(R.id.tv_temp);

        linear_start = findViewById(R.id.linear_start);
        linear_end = findViewById(R.id.linear_end);
        linear_amount = findViewById(R.id.linear_amount);
        linear_temp = findViewById(R.id.linear_temp);
        linear_many = findViewById(R.id.linear_many);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edt_memo.getWindowToken(), 0);



        //플래그먼트에서 dto 받기
        Intent intent = getIntent();
        DiaryVO dto = (DiaryVO) intent.getSerializableExtra("dto");
        boolean is_info=false;
        if(intent.getSerializableExtra("is_info")!=null){
            is_info = (Boolean) intent.getSerializableExtra("is_info");
        }

        if((dto.getBaby_category()).equals("모유")){
            linear_amount.setVisibility(View.GONE);
            linear_temp.setVisibility(View.GONE);
            btn1.setText("왼쪽");
            btn2.setText("오른쪽");
            btn3.setText("둘다");
            btn4.setText("모름");
        }else if((dto.getBaby_category()).equals("분유")){
            linear_temp.setVisibility(View.GONE);
            linear_many.setVisibility(View.GONE);

            dto.setAmount(0);
        }else if((dto.getBaby_category()).equals("이유식")){
            linear_temp.setVisibility(View.GONE);
            linear_many.setVisibility(View.GONE);

            dto.setAmount(0);
        }else if((dto.getBaby_category()).equals("기저귀")){
            linear_amount.setVisibility(View.GONE);
            linear_end.setVisibility(View.GONE);
            linear_temp.setVisibility(View.GONE);
            btn1.setText("대변");
            btn2.setText("소변");
            btn3.setText("둘다");
            btn4.setVisibility(View.GONE);
        }else if((dto.getBaby_category()).equals("수면")){
            linear_amount.setVisibility(View.GONE);
            linear_temp.setVisibility(View.GONE);
            btn1.setText("낮잠");
            btn2.setText("밤잠");
            btn3.setVisibility(View.GONE);
            btn4.setVisibility(View.GONE);
        }else if((dto.getBaby_category()).equals("목욕")){
            linear_amount.setVisibility(View.GONE);
            linear_temp.setVisibility(View.GONE);
            linear_many.setVisibility(View.GONE);
        }else if((dto.getBaby_category()).equals("체온")){
            linear_end.setVisibility(View.GONE);
            linear_amount.setVisibility(View.GONE);
            linear_many.setVisibility(View.GONE);
            dto.setTemperature(36.5);
        }else if((dto.getBaby_category()).equals("물")){
            linear_temp.setVisibility(View.GONE);
            linear_many.setVisibility(View.GONE);
            dto.setAmount(0);
        }else if((dto.getBaby_category()).equals("투약")){
            linear_temp.setVisibility(View.GONE);
            linear_many.setVisibility(View.GONE);
            dto.setAmount(0);
        }else if((dto.getBaby_category()).equals("간식")){
            linear_temp.setVisibility(View.GONE);
            linear_many.setVisibility(View.GONE);
            dto.setAmount(0);
        }

        tv_state.setText(dto.getBaby_category());
        String time = getNowtime();
        String[] time_arr1;
        String[] time_arr2;

        if(is_info){
            time_arr1 = (dto.getStart_time()).split(":");
            time_arr2 = (dto.getEnd_time()).split(":");
            tv_temp.setText(dto.getTemperature()+"");
            tv_amount.setText(dto.getAmount()+"");
            edt_memo.setText(dto.getMemo());
        }else{
            time_arr1 = (time).split(":");
            time_arr2 = (time).split(":");
            dto.setDiary_date(getnowDate());
            dto.setStart_time(time);
            dto.setEnd_time(time);
            btn_del.setVisibility(View.GONE);

        }

        tv_date.setText(dto.getDiary_date()+"");
        tv_start.setText(dto.getStart_time()+"");
        tv_end.setText(dto.getEnd_time()+"");




        callbackMethod1 = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                String strdate = hourOfDay + ":" + minute;

                dto.setStart_time(strdate);
                tv_start.setText(strdate);
            }
        };

        callbackMethod2 = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                String strdate = hourOfDay + ":" + minute;

                dto.setEnd_time(strdate);
                tv_end.setText(strdate);
            }
        };

        tv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(DetailActivity.this, callbackMethod1, Integer.parseInt(time_arr1[0]), Integer.parseInt(time_arr1[1]), true);
                dialog.show();
            }
        });

        tv_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(DetailActivity.this, callbackMethod2, Integer.parseInt(time_arr2[0]), Integer.parseInt(time_arr2[1]), true);
                dialog.show();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dto.setMemo(edt_memo.getText()+"");

                if(intent.getSerializableExtra("is_info") != null){
                    AskTask task = new AskTask("http://192.168.0.4","update.di");
                    String dtogson = gson.toJson(dto);
                    task.addParam("dto", dtogson);
                    InputStream in = CommonMethod.excuteGet(task);
                    Boolean isSucc = gson.fromJson(new InputStreamReader(in), Boolean.class);
                }else{
                    AskTask task = new AskTask("http://192.168.0.4","insert.di");
                    String dtogson = gson.toJson(dto);
                    task.addParam("dto", dtogson);
                    InputStream in = CommonMethod.excuteGet(task);
                    Boolean isSucc = gson.fromJson(new InputStreamReader(in), Boolean.class);
                }


                //Log.d("isSucc : ", isSucc+"");
                Intent intent = new Intent();
                //intent.putExtra("dto", dto);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AskTask task = new AskTask("http://192.168.0.4","delete.di");
                String dtogson = gson.toJson(dto);
                task.addParam("dto", dtogson);
                InputStream in = CommonMethod.excuteGet(task);
                Boolean isSucc = gson.fromJson(new InputStreamReader(in), Boolean.class);
                Log.d("isSucc : ", isSucc+"");
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public String getNowtime(){
        //현재 시간
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String getTime = dateFormat.format(date);
        return  getTime;
    }
    public String getnowDate(){
        //현재 시간
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String getTime = dateFormat.format(date);
        //java.sql.Date date1 = java.sql.Date.valueOf(getTime);
        return  getTime;
    }
}