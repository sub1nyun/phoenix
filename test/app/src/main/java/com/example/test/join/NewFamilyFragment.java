package com.example.test.join;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_new_family, container, false) ;

        JoinMainActivity.go = 2;

        edt_title = rootView.findViewById(R.id.edt_title);
        edt_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                JoinMainActivity.vo.setTitle( edt_title.getText().toString() );
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });






        return rootView;
    }
}