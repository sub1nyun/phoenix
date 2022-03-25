package com.example.test.join;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;

public class UserFragment extends Fragment {
    EditText edt_id ,edt_pw, edt_pwchk;
    ImageView join_kakao, join_naver;
    TextView tv_id_check;
    String family_id;
    Gson gson = new Gson();

    public UserFragment() {

    }

    public UserFragment(String family_id) {
        this.family_id = family_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootVIew = (ViewGroup)  inflater.inflate(R.layout.fragment_user, container, false);
        JoinMainActivity.go = 1;
        if(family_id != null){
            JoinMainActivity.go = 7;
        }

        edt_id = rootVIew.findViewById(R.id.edt_id);
        edt_pw = rootVIew.findViewById(R.id.edt_pw);
        edt_pwchk = rootVIew.findViewById(R.id.edt_pwchk);
        tv_id_check = rootVIew.findViewById(R.id.tv_id_check);

        join_kakao = rootVIew.findViewById(R.id.join_kakao);
        join_naver = rootVIew.findViewById(R.id.join_naver);

        join_kakao.setOnClickListener(v -> {
            Toast.makeText(getContext(), "카카오 눌림", Toast.LENGTH_SHORT).show();
        });

        join_naver.setOnClickListener(v -> {
            Toast.makeText(getContext(), "네이버도 눌림", Toast.LENGTH_SHORT).show();
        });



        edt_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    JoinMainActivity.vo.setId( edt_id.getText().toString() );

                String aa = "";
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                JoinMainActivity.vo.setPw( edt_pw.getText().toString() );
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_pwchk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                JoinMainActivity.vo.setPw_chk( edt_pwchk.getText().toString() );
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_id_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !id_check() ){      //중복확인
                    AlertDialog.Builder builder = new AlertDialog.Builder( getContext() );
                    builder.setTitle("아이디를 사용할 수 없습니다").setMessage("");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {
                            edt_id.setText("");
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder( getContext() );
                    builder.setTitle("사용가능한 아이디입니다.").setMessage("");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    JoinMainActivity.id_chk = 1;
                }
            }
        });







        return rootVIew;
    }//onCreateView

    //중복확인
    public boolean id_check(){
        AskTask task = new AskTask("http://192.168.0.50", "id_check.join");
        task.addParam("id" , JoinMainActivity.vo.getId() );
        String aa = "";
        InputStream in = CommonMethod.excuteGet(task);
        boolean data = gson.fromJson(new InputStreamReader(in), new TypeToken<Boolean>(){}.getType());


        return data ;
    }









}