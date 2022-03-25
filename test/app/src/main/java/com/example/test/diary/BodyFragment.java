package com.example.test.diary;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.test.MainActivity;
import com.example.test.OnBackPressedListenser;
import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.example.test.my.BabyInfoVO;
import com.example.test.my.EditFragment;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;

public class BodyFragment extends Fragment  implements OnBackPressedListenser {
    TextView tv_name, tv_gender, edit_ok;
    EditText edit_weight, edit_height;
    ImageView edit_cancel, view_graph, view_calender;
    Gson gson = new Gson();

    DatePickerDialog.OnDateSetListener callbackMethod;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_body, container, false);
        edit_cancel = rootView.findViewById(R.id.edit_cancel);
        edit_ok = rootView.findViewById(R.id.edit_ok);
        tv_name = rootView.findViewById(R.id.tv_name);
        tv_gender = rootView.findViewById(R.id.tv_gender);
        edit_weight = rootView.findViewById(R.id.edit_weight);
        edit_height = rootView.findViewById(R.id.edit_height);
        view_graph = rootView.findViewById(R.id.view_graph);
        view_calender = rootView.findViewById(R.id.view_calender);

        //초기 세팅
        tv_name.setText(CommonVal.curbaby.getBaby_name());
        if(CommonVal.curbaby.getBaby_gender().equals("남아")){
            tv_gender.setText("남자");
        } else if(CommonVal.curbaby.getBaby_gender().equals("여아")){
            tv_gender.setText("여자");
        } else{
            tv_gender.setText("성별 모름");
        }

        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            }
        };

        //뒤로가기
        edit_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder_cancel = new AlertDialog.Builder(getContext()).setTitle("취소").setMessage("현재 수정하신 내용이 저장되지 않습니다.\n정말 취소하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().getSupportFragmentManager().beginTransaction().remove(BodyFragment.this).commit();
                                getActivity().getSupportFragmentManager().popBackStack();
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

        //저장
       edit_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*BabyStorVO vo = new BabyStorVO();
                vo.setBaby_id(CommonVal.curbaby.getBaby_id());
                vo.setStor_date();
                String weight = edit_weight.getText().toString() != "" ? edit_weight.getText().toString() : "0.0";
                String height = edit_height.getText().toString() != "" ? edit_height.getText().toString() : "0.0";
                AskTask task = new AskTask(CommonVal.httpip,"insert.stor");
                String dtogson = gson.toJson(dto);
                task.addParam("dto", dtogson);
                InputStream in = CommonMethod.excuteGet(task);
                Boolean isSucc = gson.fromJson(new InputStreamReader(in), Boolean.class);*/
            }
        });

        view_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
       //그래프 보기
        view_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GraphActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    //플래그먼트 백버튼 처리
    @Override
    public void onBackPressed() {
        edit_cancel.callOnClick();
    }
}