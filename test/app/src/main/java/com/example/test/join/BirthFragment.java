package com.example.test.join;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;

import com.example.test.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BirthFragment extends Fragment {

    TextView tv_bir;
    LinearLayout linear_bir;
    DatePickerDialog.OnDateSetListener callbackMethod;
    private TimePickerDialog.OnTimeSetListener callbackTime;

    int y, m, d, h, mi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_birth, container, false);



        tv_bir = rootView.findViewById(R.id.tv_bir);
        linear_bir = rootView.findViewById(R.id.linear_bir);


        //오늘날짜 받아서
        Calendar today = Calendar.getInstance();
        //세팅함
        //today.set(Integer.parseInt("2022") , Integer.parseInt("03") , Integer.parseInt("22"));
        if(JoinMainActivity.babyInfoVO.getBaby_birth() != null ){
//            tv_bir.setText( ""+     new Date().getYear() + new Date().getMonth() + new Date().getDay());
            String[] strDate = JoinMainActivity.babyInfoVO.getBaby_birth().substring(0,JoinMainActivity.babyInfoVO.getBaby_birth().indexOf(" ")).split("-");
            String[] strTime = JoinMainActivity.babyInfoVO.getBaby_birth().substring(JoinMainActivity.babyInfoVO.getBaby_birth().indexOf(" ")+1).split(":");
            today.set(Integer.parseInt(strDate[0]), Integer.parseInt(strDate[1])-1, Integer.parseInt(strDate[2]), Integer.parseInt(strTime[0]), Integer.parseInt(strTime[1]));
        }else {
            JoinMainActivity.babyInfoVO.setBaby_birth( today.get(Calendar.YEAR) + "-" + String.format("%02d",(today.get(Calendar.MONTH)+1)) + "-" + String.format("%02d",today.get(Calendar.DATE)) + " "
                    + String.format("%02d",today.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d",today.get(Calendar.MINUTE))   );
        }
        //JoinMainActivity.babyInfoVO.setBaby_birth(tv_bir.getText().toString());
        tv_bir.setText(today.get(Calendar.YEAR) + "년 " + String.format("%02d",today.get(Calendar.MONTH)+1) + "월 " + String.format("%02d",today.get(Calendar.DATE)) + "일 " + String.format("%02d",today.get(Calendar.HOUR_OF_DAY)) + "시 " + String.format("%02d",today.get(Calendar.MINUTE)) + "분");



        //얘가 날짜값을 받아서 세팅해주는 역할
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                y = year;
                m = monthOfYear;
                d = dayOfMonth;


                TimePickerDialog dialog = new TimePickerDialog(getContext(),android.R.style.Theme_Holo_Light_Dialog_NoActionBar, callbackTime, today.get(Calendar.HOUR_OF_DAY), today.get(Calendar.MINUTE), false);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            }
        };

        callbackTime = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                h = hourOfDay;
                mi = minute;
                today.set(y, m, d, h, mi);
                tv_bir.setText(y + "년 " + String.format("%02d",m + 1) + "월 " + String.format("%02d",d) + "일 " + String.format("%02d",h) + "시 " + String.format("%02d",mi) + "분 ");
                //JoinMainActivity.vo.setBirth();
                JoinMainActivity.babyInfoVO.setBaby_birth( y +"-" +String.format("%02d", m+1 )+"-" + String.format("%02d", d) + " " + String.format("%02d", h) + ":" + String.format("%02d", mi));
            }
        };


        linear_bir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), callbackMethod, today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE));
                dialog.show();
            }
        });

        return rootView;
    }
}