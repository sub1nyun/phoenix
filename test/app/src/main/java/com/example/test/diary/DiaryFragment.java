package com.example.test.diary;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.MainActivity;
import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class DiaryFragment extends Fragment {
    ImageView imv_calender, imv_mou, imv_bunu, imv_eat, imv_bath, imv_temp, imv_sleep, imv_toilet, imv_phar, imv_water, imv_danger, imv_backday, imv_forwardday, imv_graph, imv_store;
    TextView tv_today, tv_baby_gender, tv_baby_name, tv_baby_age;
    Intent intent;
    RecyclerView rcv_diary;

    final int CODE = 1000;

    DatePickerDialog.OnDateSetListener callbackMethod;

    List<DiaryVO> list = new ArrayList<>();
    Gson gson = new Gson();
    Calendar today = Calendar.getInstance();//오늘날짜 받기

    String pageDate; //페이지 날짜 저장하기

    public DiaryFragment() {

    }

    public DiaryFragment(String pageDate) {
        this.pageDate = pageDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_dairy, container, false);

        imv_calender = rootview.findViewById(R.id.imv_calender);
        tv_today = rootview.findViewById(R.id.tv_today);
        rcv_diary = rootview.findViewById(R.id.rcv_diary);

        tv_baby_gender = rootview.findViewById(R.id.tv_baby_gender);
        tv_baby_name = rootview.findViewById(R.id.tv_baby_name);
        tv_baby_age = rootview.findViewById(R.id.tv_baby_age);

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
        imv_store = rootview.findViewById(R.id.imv_store);

        //개월수 구하기
        String baby_age_str = CommonVal.curbaby.getBaby_birth();
        String[] baby_age_arr = baby_age_str.substring(0,baby_age_str.indexOf(" ")).split("-");
        LocalDate theDate = LocalDate.of(Integer.parseInt(baby_age_arr[0]),Integer.parseInt(baby_age_arr[1]),Integer.parseInt(baby_age_arr[2]));
        Period age = theDate.until(LocalDate.now());

        tv_baby_name.setText(CommonVal.curbaby.getBaby_name());
        tv_baby_gender.setText(CommonVal.curbaby.getBaby_gender());
        tv_baby_age.setText(age.getYears()*12 + age.getMonths() + "개월 " + age.getDays() + "일");

        //페이지 날짜를 넘겨받았을 때
        if(pageDate != null){
            String[] strDate = pageDate.split("-");
            today.set(Integer.parseInt(strDate[0]), Integer.parseInt(strDate[1]) - 1, Integer.parseInt(strDate[2]));
        }

        //Toast.makeText(getContext(), CommonVal.curbaby.getBaby_name(), Toast.LENGTH_SHORT).show();


        //그래프
        imv_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), GraphActivity.class);
                startActivity(intent);
            }
        });
        //키 / 체중 입력
        imv_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new BodyFragment();
                ((MainActivity)getActivity()).backFrag(new BodyFragment());
                ((MainActivity)getActivity()).changeFrag(fragment);
            }
        });

        //날짜 세팅함
        tv_today.setText(today.get(Calendar.YEAR) + "년 " + (today.get(Calendar.MONTH)+1) + "월 " + today.get(Calendar.DATE) + "일");

        //리스트 불러옴
        chgDateList(today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DATE), getContext());

        //얘가 날짜값을 받아서 세팅해주는 역할
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                today.set(year, month, dayOfMonth);
                tv_today.setText(year + "년 " + (month+1) + "월 " + dayOfMonth + "일");
                chgDateList(year,month,dayOfMonth, getContext());
            }
        };

        //하루전
        imv_backday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                today.add(Calendar.DATE, -1);
                tv_today.setText(today.get(Calendar.YEAR) + "년" + (today.get(Calendar.MONTH)+1) + "월" + today.get(Calendar.DATE) + "일");
                chgDateList(today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DATE), getContext());
            }
        });
        //하루후
        imv_forwardday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                today.add(Calendar.DATE, 1);
                tv_today.setText(today.get(Calendar.YEAR) + "년" + (today.get(Calendar.MONTH)+1) + "월" + today.get(Calendar.DATE) + "일");
                chgDateList(today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DATE), getContext());
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


        //아기상태 버튼 클릭 시
        imv_bath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("dto", setDTO("목욕"));
                getActivity().startActivityForResult(intent, CODE);
                //startActivity(intent);
            }
        });
        imv_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("dto", setDTO("체온"));
                getActivity().startActivityForResult(intent, CODE);
            }
        });
        imv_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("dto", setDTO("수면"));
                getActivity().startActivityForResult(intent, CODE);
            }
        });
        imv_eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("dto", setDTO("이유식"));
                getActivity().startActivityForResult(intent, CODE);
            }
        });
        imv_toilet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("dto", setDTO("기저귀"));
                getActivity().startActivityForResult(intent, CODE);
            }
        });
        imv_phar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("dto", setDTO("투약"));
                getActivity().startActivityForResult(intent, CODE);
            }
        });
        imv_mou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("dto", setDTO("모유"));
                getActivity().startActivityForResult(intent, CODE);
            }
        });
        imv_bunu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("dto", setDTO("분유"));
                getActivity().startActivityForResult(intent, CODE);
            }
        });
        imv_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("dto", setDTO("물"));
                getActivity().startActivityForResult(intent, CODE);
            }
        });
        imv_danger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("dto", setDTO("간식"));
                getActivity().startActivityForResult(intent, CODE);
            }
        });

        return rootview;
    }

    public DiaryVO setDTO(String category){
        DiaryVO vo = new DiaryVO();
        vo.setBaby_id(CommonVal.curbaby.getBaby_id());
        vo.setBaby_category(category);
        String strM = today.get(Calendar.MONTH) < 9 ? "0"+(today.get(Calendar.MONTH)+1) : ""+(today.get(Calendar.MONTH)+1);
        String strD = today.get(Calendar.DATE) < 10 ? "0"+today.get(Calendar.DATE) : ""+today.get(Calendar.DATE);
        vo.setDiary_date(today.get(Calendar.YEAR) + "-" + strM + "-" + strD);
        return vo;
    }

    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if (msg.what == 1){
                DiaryAdapter adapter = new DiaryAdapter(list, (Context) msg.obj );
                rcv_diary.setAdapter(adapter);
                LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                rcv_diary.setLayoutManager(manager);
            }
        }
    };

    public void chgDateList(int y, int m, int d, Context context){
        AskTask task = new AskTask(CommonVal.httpip,"list.di");

        if(m<9){
            task.addParam("date", y + "-0" + (m+1) + "-" + d);
        }else{
            task.addParam("date", y + "-" + (m+1) + "-" + d);
        }
        task.addParam("id", CommonVal.curbaby.getBaby_id());
        InputStream in = CommonMethod.excuteGet(task);
        if(in != null){
            //NetworkOnMainThreadException 에러가 발생해서 추가
            new Thread(() -> {
                list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<DiaryVO>>(){}.getType());
                Message msg = handler.obtainMessage(1, context);

                handler.sendMessage(msg);
            }).start();
        }
    }

}
