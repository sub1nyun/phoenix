package com.example.test.my;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.test.MainActivity;
import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MyFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    Button btn_co_parent, delete_baby;
    Spinner my_spinner;
    ImageView my_setting, my_detail, my_main_photo, my_diary_title_edit;
    TextView my_birth_tv, my_name_tv, my_diary_title;
    Gson gson = new Gson();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_my, container, false);
        my_setting = rootView.findViewById(R.id.my_setting);
        my_detail = rootView.findViewById(R.id.my_detail);
        my_spinner = rootView.findViewById(R.id.my_spinner);
        my_main_photo = rootView.findViewById(R.id.my_main_photo);
        my_birth_tv = rootView.findViewById(R.id.my_birth_tv);
        my_name_tv = rootView.findViewById(R.id.my_name_tv);
        btn_co_parent = rootView.findViewById(R.id.btn_co_parent);
        delete_baby = rootView.findViewById(R.id.delete_baby);
        my_diary_title = rootView.findViewById(R.id.my_diary_title);
        my_diary_title_edit = rootView.findViewById(R.id.my_diary_title_edit);

        AskTask task = new AskTask("list.bif");
        InputStream in = CommonMethod.excuteGet(task);
        List<BabyInfoVO> list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<BabyInfoVO>>(){}.getType());

        my_diary_title_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryTitleDialog dialog = new DiaryTitleDialog(getContext());
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.show();
                Window window = dialog.getWindow();
                window.setAttributes(lp);
                dialog.setDialogListener(new DiaryTitleDialog.DialogListener() {
                    @Override
                    public void onPositiveClick(String name) {
                        my_diary_title.setText(name);
                    }
                });
            }
        });

        my_spinner.setOnItemSelectedListener(this);
        BabySelectAdapter babySelectAdapter = new BabySelectAdapter(list, inflater, getContext());
        my_spinner.setAdapter(babySelectAdapter);

        my_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        my_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFrag(new EditFragment());
            }
        });

        btn_co_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFrag(new CoParentFragment());
            }
        });

        delete_baby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder_delete = new AlertDialog.Builder(getContext()).setTitle("아이 정보 삭제").setMessage("현재까지 기록한 아이 기록이 모두 사라집니다.\n정말 삭제하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //어딘가로 이동
                            }
                        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alertDialog = builder_delete.create();
                alertDialog.show();
            }
        });

        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}