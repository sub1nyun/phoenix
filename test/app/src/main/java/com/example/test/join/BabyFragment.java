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

public class BabyFragment extends Fragment {

    EditText edt_name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_baby, container, false);


        edt_name = rootView.findViewById(R.id.edt_name);
        edt_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                JoinMainActivity.babyInfoVO.setBaby_name( edt_name.getText().toString() );
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        edt_name.requestFocus();







        return rootView;
    }
}