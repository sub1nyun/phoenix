package com.example.test.join;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.test.R;


public class NewFamilyFragment extends Fragment {
    EditText edt_title;
    Activity activity;

    public NewFamilyFragment(Activity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_new_family, container, false) ;


        //((JoinMainActivity)getActivity()).btn_back.setVisibility(View.INVISIBLE);//뒤로가기버튼 숨김


        edt_title = rootView.findViewById(R.id.edt_title);
        edt_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                JoinMainActivity.vo.setTitle( edt_title.getText().toString() );
                JoinMainActivity.babyInfoVO.setTitle( edt_title.getText().toString() );
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_title.requestFocus();







        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        JoinMainActivity.id_chk = 0;
    }
}