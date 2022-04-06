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
            today.set(Integer.parseInt(strDate[0]), Integer.parseInt(strDate[1]), Integer.parseInt(strDate[2]));
            tv_bir.setText(today.get(Calendar.YEAR) + "년" + (today.get(Calendar.MONTH)) + "월" + today.get(Calendar.DATE) + "일" + today.get(Calendar.HOUR) + "시" + today.get(Calendar.MINUTE) + "분");
        }else {
            JoinMainActivity.babyInfoVO.setBaby_birth( today.get(Calendar.YEAR) + "-" + String.format("%02d",(today.get(Calendar.MONTH)+1)) + "-" + String.format("%02d",today.get(Calendar.DATE)) + " "
                    + String.format("%02d",today.get(Calendar.HOUR)) + ":" + String.format("%02d",today.get(Calendar.MINUTE))   );
            tv_bir.setText(today.get(Calendar.YEAR) + "년" + (today.get(Calendar.MONTH)+1) + "월" + today.get(Calendar.DATE) + "일" + today.get(Calendar.HOUR) + "시" + today.get(Calendar.MINUTE) + "분");
        }
        //JoinMainActivity.babyInfoVO.setBaby_birth(tv_bir.getText().toString());



        //얘가 날짜값을 받아서 세팅해주는 역할
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                y = year;
                m = monthOfYear;
                d = dayOfMonth;

                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                String time = sdf.format(new Date(System.currentTimeMillis()));
                String times[] = new String[2];
                times = time.split(":");
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), callbackTime, Integer.parseInt(times[0]), Integer.parseInt(times[1]), true);
                timePickerDialog.show();
            }
        };

        callbackTime = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                h = hourOfDay;
                mi = minute;
                today.set(y, m, d);
                tv_bir.setText(y + "년" + (m + 1) + "월" + d + "일" + h + "시" + mi + "분");
                //JoinMainActivity.vo.setBirth();
                JoinMainActivity.babyInfoVO.setBaby_birth( y +"-" +String.format("%02d", m )+"-" + String.format("%02d", d) + " " + String.format("%02d", h) + ":" + String.format("%02d", mi));
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