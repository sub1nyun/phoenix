package com.example.test;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.diary.DiaryAdapter;
import com.example.test.diary.detailDTO;

import java.util.ArrayList;
import java.util.Calendar;

public class DiaryFragment extends Fragment {
    ImageView imv_calender, imv_bath, imv_temp, imv_sleep, imv_eat, imv_toilet, imv_phar;
    TextView tv_today;

    DatePickerDialog.OnDateSetListener callbackMethod;//

    RecyclerView rcv_diary;

    ArrayList<detailDTO> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_dairy, container, false);

        imv_calender = rootview.findViewById(R.id.imv_calender);
        tv_today = rootview.findViewById(R.id.tv_today);
        rcv_diary = rootview.findViewById(R.id.rcv_diary);
        imv_bath = rootview.findViewById(R.id.imv_bath);
        imv_temp = rootview.findViewById(R.id.imv_temp);
        imv_sleep = rootview.findViewById(R.id.imv_sleep);
        imv_eat = rootview.findViewById(R.id.imv_eat);
        imv_toilet = rootview.findViewById(R.id.imv_toilet);
        imv_phar = rootview.findViewById(R.id.imv_phar);

        //오늘날짜 받아서
        Calendar today = Calendar.getInstance();
        //세팅함
        tv_today.setText(today.get(Calendar.YEAR) + "년" + (today.get(Calendar.MONTH)+1) + "월" + today.get(Calendar.DATE) + "일");

        //얘가 날짜값을 받아서 세팅해주는 역할
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tv_today.setText(year + "년" + (month+1) + "월" + dayOfMonth + "일");
            }
        };

        //날짜선택할 다이얼로그 띄워줌
        imv_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), callbackMethod, today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE));
                dialog.show();
            }
        });

        imv_bath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(new detailDTO("목욕"));
            }
        });

        imv_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(new detailDTO("체온"));
            }
        });

        imv_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(new detailDTO("잠"));

            }
        });

        imv_eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(new detailDTO("밥"));

            }
        });

        imv_toilet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(new detailDTO("변"));

            }
        });

        imv_phar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(new detailDTO("투약"));

            }
        });
        DiaryAdapter adapter = new DiaryAdapter(inflater);
        rcv_diary.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        rcv_diary.setLayoutManager(manager);

        return rootview;
    }
}