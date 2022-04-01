package com.example.test.sns;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.test.MainActivity;
import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.example.test.my.MyFragment;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class SnsFragment extends Fragment {


    ArrayList<SnsVO> snslist = new ArrayList<>();
    ImageView sns_plus;
    Intent intent;
    ArrayList<GrowthVO> grolist = new ArrayList<>();
    Activity activity;


    public SnsFragment(Activity activity) {
        this.activity = activity;
    }

    public static ArrayList<String> img_list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_sns, container, false);

        RecyclerView sns_view_rec = rootView.findViewById(R.id.sns_view_rec);
        sns_plus = rootView.findViewById(R.id.sns_plus);

        AskTask task = new AskTask(CommonVal.httpip, "select.sn");
        Gson gson = new Gson();
        task.addParam("baby_id",CommonVal.curbaby.getBaby_id());
        InputStream in = CommonMethod.excuteGet(task);
        List<GrowthVO> growthVOS = gson.fromJson(new InputStreamReader(in), new TypeToken<List<GrowthVO>>(){}.getType());
        growthVOS.get(0).getImgList();
        String a = "";



        SnsViewAdapter adapter = new SnsViewAdapter(inflater,growthVOS, activity);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        sns_view_rec.setAdapter(adapter);
        sns_view_rec.setLayoutManager(manager);

       sns_plus.setOnClickListener(v -> {
           intent = new Intent(getContext(), SnsNewActivity.class);
           startActivity(intent);
       });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        for(int i = 0 ; i<img_list.size() ; i++){
            snslist.add(new SnsVO( "테스트1"));
        }

//        AskTask task = new AskTask("http://192.168.0.11", "list.sn");
//        Gson gson = new Gson();
//        CommonVal.curuser.setId("a");
//        CommonVal.curuser.setPw("a");
//        vo.setId(CommonVal.curuser.getId());
//        sns_user_id.setText(vo.getId()+"");
//        SnsImgRecAdapter = new SnsImgRecAdapter(img_list, (Activity) getContext());
//        sns_view_rec.setAdapter((RecyclerView.Adapter) SnsImgRecAdapter);
//        sns_view_rec.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,true));
//        vo.setTitle("test");
//        vo.setSns_content("testText");
//        sns_user_content.setText(vo.getSns_content()+"");

//        String testvo = gson.toJson(vo);
//        task.addParam("vo", testvo);
//        InputStream in = CommonMethod.excuteGet(task);
//        //SnsVO andVO = gson.fromJson(new InputStreamReader(in), new TypeToken<SnsVO>(){}.getType());
//        //쿼리 작성할 부분
//        img_list = new ArrayList<>();
//        snsadapter.notifyDataSetChanged();
    }
}