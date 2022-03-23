package com.example.test.diary;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DiaryFragment extends Fragment {
    ImageView imv_calender, imv_mou, imv_bunu, imv_eat, imv_bath, imv_temp, imv_sleep, imv_toilet, imv_phar, imv_water, imv_danger, imv_backday, imv_forwardday, imv_graph;
    TextView tv_today;
    Intent intent;

    DiaryVO dto;

    final int CODE = 1000;

    DatePickerDialog.OnDateSetListener callbackMethod;

    RecyclerView rcv_diary;

    List<DiaryVO> list = new ArrayList<>();

    Gson gson = new Gson();

    public DiaryFragment() {

    }

//    public DiaryFragment(DiaryVO dto) {
//        this.dto = dto;
//    }

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
        imv_phar = rootview.findViewById(R.id.imv_pills);
        imv_mou = rootview.findViewById(R.id.imv_mou);
        imv_bunu = rootview.findViewById(R.id.imv_bunu);
        imv_water = rootview.findViewById(R.id.imv_water);
        imv_danger = rootview.findViewById(R.id.imv_danger);

        imv_backday = rootview.findViewById(R.id.imv_backday);
        imv_forwardday = rootview.findViewById(R.id.imv_forwardday);

        imv_graph = rootview.findViewById(R.id.imv_graph);

        AskTask task = new AskTask("http://192.168.0.4","list.di");
        InputStream in = CommonMethod.excuteGet(task);
        if(in != null){
            //NetworkOnMainThreadException 에러가 발생해서 추가
            new Thread(() -> {
                list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<DiaryVO>>(){}.getType());
                attachDetail(getContext());
            }).start();


        }

        //그래프
        imv_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), GraphActivity.class);
                startActivity(intent);
            }
        });

        //오늘날짜 받아서
        Calendar today = Calendar.getInstance();
        //세팅함
        tv_today.setText(today.get(Calendar.YEAR) + "년" + (today.get(Calendar.MONTH)+1) + "월" + today.get(Calendar.DATE) + "일");

        //얘가 날짜값을 받아서 세팅해주는 역할
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                today.set(year, month, dayOfMonth);
                tv_today.setText(year + "년" + (month+1) + "월" + dayOfMonth + "일");
            }
        };




        //넘겨받은 dto가 있을때
        /*if(dto != null){
            list.add(dto);

            AskTask task = new AskTask("list.bif");
            InputStream in = CommonMethod.excuteGet(task);
            List<DiaryVO> list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<DiaryVO>>(){}.getType());

            attachDetail(getContext());
        }*/

        imv_backday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                today.add(Calendar.DATE, -1);
                tv_today.setText(today.get(Calendar.YEAR) + "년" + (today.get(Calendar.MONTH)+1) + "월" + today.get(Calendar.DATE) + "일");
            }
        });

        imv_forwardday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                today.add(Calendar.DATE, 1);
                tv_today.setText(today.get(Calendar.YEAR) + "년" + (today.get(Calendar.MONTH)+1) + "월" + today.get(Calendar.DATE) + "일");
            }
        });

        //날짜선택할 다이얼로그 띄워줌
        imv_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), callbackMethod, today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE));
                dialog.show();

            }
        });

        //intent.putExtra("is_info",false);

        //아기상태 버튼 클릭 시
        imv_bath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dto = new DiaryVO();
                intent = new Intent(getContext(), DetailActivity.class);
                dto.setBaby_category("목욕");
                intent.putExtra("dto", dto);
                getActivity().startActivityForResult(intent, CODE);
                //startActivity(intent);
            }
        });

        imv_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dto = new DiaryVO();
                intent = new Intent(getContext(), DetailActivity.class);
                dto.setBaby_category("체온");
                intent.putExtra("dto", dto);
                getActivity().startActivityForResult(intent, CODE);
            }
        });

        imv_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dto = new DiaryVO();
                intent = new Intent(getContext(), DetailActivity.class);
                dto.setBaby_category("수면");
                intent.putExtra("dto", dto);
                getActivity().startActivityForResult(intent, CODE);
            }
        });

        imv_eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dto = new DiaryVO();
                intent = new Intent(getContext(), DetailActivity.class);
                dto.setBaby_category("이유식");
                intent.putExtra("dto", dto);
                getActivity().startActivityForResult(intent, CODE);
            }
        });

        imv_toilet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dto = new DiaryVO();
                intent = new Intent(getContext(), DetailActivity.class);
                dto.setBaby_category("기저귀");
                intent.putExtra("dto", dto);
                getActivity().startActivityForResult(intent, CODE);
            }
        });

        imv_phar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dto = new DiaryVO();
                intent = new Intent(getContext(), DetailActivity.class);
                dto.setBaby_category("투약");
                intent.putExtra("dto", dto);
                getActivity().startActivityForResult(intent, CODE);
            }
        });

        imv_mou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dto = new DiaryVO();
                intent = new Intent(getContext(), DetailActivity.class);
                dto.setBaby_category("모유");
                intent.putExtra("dto", dto);
                getActivity().startActivityForResult(intent, CODE);
            }
        });

        imv_bunu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dto = new DiaryVO();
                intent = new Intent(getContext(), DetailActivity.class);
                dto.setBaby_category("분유");
                intent.putExtra("dto", dto);
                getActivity().startActivityForResult(intent, CODE);
            }
        });

        imv_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dto = new DiaryVO();
                intent = new Intent(getContext(), DetailActivity.class);
                dto.setBaby_category("물");
                intent.putExtra("dto", dto);
                getActivity().startActivityForResult(intent, CODE);
            }
        });

        imv_danger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dto = new DiaryVO();
                intent = new Intent(getContext(), DetailActivity.class);
                dto.setBaby_category("간식");
                intent.putExtra("dto", dto);
                getActivity().startActivityForResult(intent, CODE);
            }
        });

        return rootview;
    }
    public void attachDetail(Context context){

        DiaryAdapter adapter = new DiaryAdapter(list, context);
        rcv_diary.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcv_diary.setLayoutManager(manager);
    }


}
