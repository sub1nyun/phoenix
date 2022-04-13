package com.example.test.diary;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.test.my.MyFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.List;

public class BodyFragment extends Fragment  implements OnBackPressedListenser {
    TextView tv_name, tv_gender, edit_ok, tv_bodydate;
    EditText edit_weight, edit_height;
    ImageView edit_cancel, view_graph, view_calender;
    Gson gson = new Gson();

    DatePickerDialog.OnDateSetListener callbackMethod;
    //오늘날짜
    Calendar today = Calendar.getInstance();

    BabyStorVO babyStorVO;
    boolean isinfo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_body, container, false);
        edit_cancel = rootView.findViewById(R.id.edit_cancel);
        edit_ok = rootView.findViewById(R.id.edit_ok);
        tv_name = rootView.findViewById(R.id.tv_name);
        tv_gender = rootView.findViewById(R.id.tv_gender);
        tv_bodydate = rootView.findViewById(R.id.tv_bodydate);
        edit_weight = rootView.findViewById(R.id.edit_weight);
        edit_height = rootView.findViewById(R.id.edit_height);
        view_graph = rootView.findViewById(R.id.view_graph);
        view_calender = rootView.findViewById(R.id.view_calender);


        tv_bodydate.setText("("+today.get(Calendar.YEAR) + "년 " + (today.get(Calendar.MONTH)+1) + "월 " + today.get(Calendar.DATE) + "일)");

        //초기 세팅
        tv_name.setText(CommonVal.curbaby.getBaby_name());
        if(CommonVal.curbaby.getBaby_gender().equals("남아")){
            tv_gender.setText("남자");
        } else if(CommonVal.curbaby.getBaby_gender().equals("여아")){
            tv_gender.setText("여자");
        } else{
            tv_gender.setText("성별 모름");
        }

        babyStorVO = setBody();

        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                today.set(year, month, dayOfMonth);
                tv_bodydate.setText("("+year + "년 " + (month+1) + "월 " + dayOfMonth + "일)");
                babyStorVO = setBody();
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
                String weight = edit_weight.getText().toString();
                String height = edit_height.getText().toString();
                if(babyStorVO != null){
                    babyStorVO.setStor_cm(Double.parseDouble(height));
                    babyStorVO.setStor_kg(Double.parseDouble(weight));
                    AskTask task = new AskTask(CommonVal.httpip,"update.stor");
                    String vogson = gson.toJson(babyStorVO);
                    task.addParam("vo", vogson);
                    InputStream in = CommonMethod.excuteGet(task);
                    Boolean isSucc = gson.fromJson(new InputStreamReader(in), Boolean.class);
                    Log.d("", "update: "+isSucc);
                }else{
                    BabyStorVO vo = new BabyStorVO();
                    vo.setBaby_id(CommonVal.curbaby.getBaby_id());
                    vo.setStor_date(today.get(Calendar.YEAR)+"-"+(today.get(Calendar.MONTH)+1)+"-"+today.get(Calendar.DAY_OF_MONTH));
                    vo.setStor_cm(Double.parseDouble(height));
                    vo.setStor_kg(Double.parseDouble(weight));
                    AskTask task = new AskTask(CommonVal.httpip,"insert.stor");
                    String vogson = gson.toJson(vo);
                    task.addParam("vo", vogson);
                    InputStream in = CommonMethod.excuteGet(task);
                    Boolean isSucc = gson.fromJson(new InputStreamReader(in), Boolean.class);
                    Log.d("", "insert: "+isSucc);
                }
                ((MainActivity)getActivity()).changeFrag(new DiaryFragment());
            }
        });

        view_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), callbackMethod, today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
                dialog.show();
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

    public BabyStorVO setBody(){
        AskTask task = new AskTask(CommonVal.httpip,"select.stor");
        task.addParam("date", today.get(Calendar.YEAR) + "-" + (today.get(Calendar.MONTH)+1) + "-" + today.get(Calendar.DAY_OF_MONTH));
        task.addParam("id", CommonVal.curbaby.getBaby_id());
        InputStream in = CommonMethod.excuteGet(task);
        BabyStorVO babyStorVO = gson.fromJson(new InputStreamReader(in), BabyStorVO.class);
        if(babyStorVO != null){
            edit_weight.setText(babyStorVO.getStor_kg()+"");
            edit_height.setText(babyStorVO.getStor_cm()+"");
            return babyStorVO;
        }else{
            edit_weight.setText("");
            edit_height.setText("");
            return babyStorVO;
        }
    }

    //플래그먼트 백버튼 처리
    @Override
    public void onBackPressed() {
        edit_cancel.callOnClick();
    }
}