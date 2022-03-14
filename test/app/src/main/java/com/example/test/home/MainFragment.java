package com.example.test.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.test.R;


public class MainFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.test1, container, false);
        return rootView;
    }
}