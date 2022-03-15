package com.example.test.join;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.R;


public class NewFamilyFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        JoinMainActivity.go = 2;
        return inflater.inflate(R.layout.fragment_new_family, container, false);
    }
}