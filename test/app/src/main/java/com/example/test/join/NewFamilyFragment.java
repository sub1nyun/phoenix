package com.example.test.join;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;


public class NewFamilyFragment extends Fragment {
    EditText edt_title;
    TextView title_check;
    Activity activity;
    int result = 0;
    Gson gson = new Gson();


    public NewFamilyFragment(Activity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_new_family, container, false) ;


        //((JoinMainActivity)getActivity()).btn_back.setVisibility(View.INVISIBLE);//뒤로가기버튼 숨김


        edt_title = rootView.findViewById(R.id.edt_title);
        title_check = rootView.findViewById(R.id.title_check);


        title_nospace();
        edt_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                JoinMainActivity.vo.setTitle( edt_title.getText().toString() );
                JoinMainActivity.familyVO.setTitle(edt_title.getText().toString());
                JoinMainActivity.babyInfoVO.setTitle( edt_title.getText().toString() );
                title_valid();
                valid_result();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edt_title.requestFocus();







        return rootView;
    }//onCreateView

    public boolean valid_result(){
        if( title_valid() ){
            JoinMainActivity.title_result = 1;
            result = 1;
            return true;
        }else {
            JoinMainActivity.title_result = 0;
            result = 0;
        }
        return false;
    }


    //육아일기 유효성
    public boolean title_valid() {
        if (edt_title.getText().toString().equals("")) {
            edt_title.requestFocus();
            title_check.setText("육아일기제목을 입력해주세요.");
            title_check.setTextColor(Color.parseColor("#FF7575"));
            return false;
        } else if (title_check()) {
            title_check.setText("사용 가능한 육아일기제목 입니다.");
            title_check.setTextColor(Color.parseColor("#3a539f"));
            return true;
        } else {
            title_check.setText("이미 사용중인 육아일기제목 입니다.");
            title_check.setTextColor(Color.parseColor("#FF7575"));
            return false;
        }
    }



    //육아일기 제목 중복확인
    public boolean title_check() {
        AskTask task = new AskTask(CommonVal.httpip, "title_check.join");
        task.addParam("title", JoinMainActivity.vo.getTitle() );
        String aa = "";
        InputStream in = CommonMethod.excuteGet(task);
        boolean data = gson.fromJson(new InputStreamReader(in), new TypeToken<Boolean>() {
        }.getType());
        return data;
    }
 /*   //중복확인
    public boolean id_check() {
        AskTask task = new AskTask(CommonVal.httpip, "id_check.join");
        task.addParam("id", JoinMainActivity.vo.getId());
        String aa = "";
        InputStream in = CommonMethod.excuteGet(task);
        boolean data = gson.fromJson(new InputStreamReader(in), new TypeToken<Boolean>() {
        }.getType());


        return data;
    }*/



    public void title_nospace(){
        edt_title.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_SPACE || keyCode == event.KEYCODE_ENTER){
                    String aa= "";
                    Toast.makeText(getContext(), "공백은 입력할수 없습니다.", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        JoinMainActivity.id_chk = 0;
    }



}