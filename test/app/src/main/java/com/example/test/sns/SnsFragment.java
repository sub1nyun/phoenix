package com.example.test.sns;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ScrollCaptureCallback;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.MainActivity;
import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;


public class SnsFragment extends Fragment {


    ImageView sns_plus;
    Intent intent;
    Activity activity;
    View sns_none;
    RecyclerView sns_view_rec;
    //FloatingActionButton up_arrow;
    ScrollView scrollView;


    LayoutInflater inflater ;
    public SnsFragment(Activity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_sns, container, false);
        this.inflater = inflater;
        sns_none = rootView.findViewById(R.id.sns_none);

        sns_view_rec = rootView.findViewById(R.id.sns_view_rec);
        sns_plus = rootView.findViewById(R.id.sns_plus);
       // up_arrow = (FloatingActionButton) rootView.findViewById(R.id.up_arrow);
        scrollView =rootView.findViewById(R.id.scrollView);

        return rootView;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        AskTask task = new AskTask(CommonVal.httpip, "select.sn");
        Gson gson = new Gson();
        task.addParam("baby_id",CommonVal.curbaby.getBaby_id());
        InputStream in = CommonMethod.excuteGet(task);
        List<GrowthVO> growthVOS = gson.fromJson(new InputStreamReader(in), new TypeToken<List<GrowthVO>>(){}.getType());

        if(isEmpty(growthVOS)) {
            sns_none.setVisibility(View.VISIBLE);
            sns_view_rec.setVisibility(View.GONE);
            sns_none.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), SnsNewActivity.class);
                startActivity(intent);
            });
        } else {
            sns_none.setVisibility(View.GONE);
            SnsViewAdapter adapter = new SnsViewAdapter(inflater,growthVOS, (MainActivity)getActivity());
            RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            sns_view_rec.setAdapter(adapter);
            sns_view_rec.setLayoutManager(manager);
            sns_view_rec.setVisibility(View.VISIBLE);
        }
        sns_plus.setOnClickListener(v -> {
            intent = new Intent(getContext(), SnsNewActivity.class);
            startActivity(intent);
        });

//        up_arrow.setOnClickListener(v -> {
//            scrollView.smoothScrollTo(0,0);
//        });
    }

    // null값이라면 true를 반환
    public boolean isEmpty(Object obj) {
        if(obj == null) return true;
        if ((obj instanceof String) && (((String)obj).trim().length() == 0)) { return true; }
        if (obj instanceof Map) { return ((Map<?, ?>) obj).isEmpty(); }
        if (obj instanceof Map) { return ((Map<?, ?>)obj).isEmpty(); }
        if (obj instanceof List) { return ((List<?>)obj).isEmpty(); }
        if (obj instanceof Object[]) { return (((Object[])obj).length == 0); }
        return false;
    }

}