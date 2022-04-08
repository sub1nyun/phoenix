package com.example.test;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.test.common.CommonVal;
import com.example.test.join.JoinMainActivity;

public class AddFragment extends Fragment {
    Button btn_new;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_add, container, false);
        btn_new = rootView.findViewById(R.id.btn_new);

        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JoinMainActivity.familyVO.setId(CommonVal.curuser.getId());
                Intent intent = new Intent(getContext(), JoinMainActivity.class);
                intent.putExtra("category", "new");
                startActivity(intent);
                ((MainActivity)getActivity()).finish();
            }
        });

        return rootView;
    }
}