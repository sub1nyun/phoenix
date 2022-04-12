package com.example.test.my;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.test.AddFragment;
import com.example.test.MainActivity;
import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.example.test.diary.BabyStorVO;
import com.example.test.diary.GraphActivity;
import com.example.test.join.JoinMainActivity;
import com.example.test.join.NewFamilyFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit2.http.GET;

public class MyFragment extends Fragment{
    ImageView my_grow, my_setting;
    RecyclerView my_rcv1;
    public static Spinner my_spinner;

    String[] titlelist = new String[CommonVal.family_title.size() + 1];
    int select = 0;
    Gson gson = new Gson();
    int press = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_my, container, false);

        my_grow = rootView.findViewById(R.id.my_grow);
        my_setting = rootView.findViewById(R.id.my_setting);
        my_rcv1 = rootView.findViewById(R.id.my_rcv1);
        my_spinner = rootView.findViewById(R.id.my_spinner);

        for(int i=0; i<titlelist.length; i++){
            if(i == titlelist.length-1) titlelist[i] = "새로운 육아일기";
            else titlelist[i] = CommonVal.family_title.get(i);
        }

        //아기 선택
        BabySelectAdapter babySelectAdapter = new BabySelectAdapter(getContext(), CommonVal.baby_list);
        my_spinner.setAdapter(babySelectAdapter);

        int index = 0;
        for(int i=0; i<CommonVal.baby_list.size(); i++){
            if(CommonVal.baby_list.get(i).getBaby_id().equals(CommonVal.curbaby.getBaby_id())){
                index = i;
                break;
            }
        }

        my_spinner.setSelection(index);
        my_spinner.setTag("0");
        my_spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP){
                    babySelectAdapter.flag = 1;
                }else{
                    babySelectAdapter.flag = 0;
                }
                return false;
            }
        });


        my_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == CommonVal.baby_list.size()){ //아기 추가 아이콘
                    InsertDialog(view);
                } else {
                    CommonVal.curbaby = CommonVal.baby_list.get(position);
                    for(int i = 0 ;  i < my_spinner.getChildCount();  i++){
                        View v = my_spinner.getChildAt(i);
                        TextView birth = v.findViewById(R.id.baby_info_birth);
                        TextView name = v.findViewById(R.id.baby_info_name);
                        birth.setTextColor(Color.parseColor("#FFFFFF"));
                        name.setTextColor(Color.parseColor("#FFFFFF"));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //성장그래프 이동
        my_grow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GraphActivity.class);
                startActivity(intent);
            }
        });

        //알림, 진동 설정
        my_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        //아이마다 키, 몸무게 기록 세팅
        AskTask body_task = new AskTask(CommonVal.httpip, "cntbody.stor");
        InputStream in = CommonMethod.excuteGet(body_task);
        List<BabyStorVO> cntbody = gson.fromJson(new InputStreamReader(in), new TypeToken<List<BabyStorVO>>(){}.getType());

        for(int i = 0; i<CommonVal.baby_list.size(); i++){
            for(int j=0; j<cntbody.size(); j++){
                if(CommonVal.baby_list.get(i).getBaby_id().equals(cntbody.get(j).getBaby_id())){
                    CommonVal.baby_list.get(i).setBody(cntbody.get(j).getStor_cm() + "cm, " + cntbody.get(j).getStor_kg() + "kg");
                    break;
                } else{
                    CommonVal.baby_list.get(i).setBody("키, 몸무게 기록이 없습니다.");
                }
            }
        }

        Log.d("asd", "onCreateView: " + CommonVal.family_title.size());

        List<List<BabyInfoVO>> temp = new ArrayList<>();
        for(int i=0; i<CommonVal.family_title.size(); i++){
            List<BabyInfoVO> list = new ArrayList<>();
            for(int j=0; j<CommonVal.baby_list.size(); j++){
                if(CommonVal.family_title.get(i).equals(CommonVal.baby_list.get(j).getTitle())){
                    list.add(CommonVal.baby_list.get(j));
                }
            }
            temp.add(list);
        }

        TitleAdapter adapter = new TitleAdapter(CommonVal.family_title, temp, getContext());
        my_rcv1.setHasFixedSize(true);
        my_rcv1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        my_rcv1.setAdapter(adapter);

        return rootView;
    }

    //스피너에서 아기 추가 선택 시
    public void InsertDialog(View view){
        ContextThemeWrapper cw = new ContextThemeWrapper(getContext(), R.style.AlertDialogTheme);
        AlertDialog dialog = new AlertDialog.Builder(cw).setTitle("육아일기선택").setSingleChoiceItems(titlelist, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), titlelist[which], Toast.LENGTH_SHORT).show();
                select = which;
            }
        }).setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //아기 정보 입력으로 이동
                JoinMainActivity.familyVO.setId(CommonVal.curuser.getId());
                Intent intent = new Intent(getContext(), JoinMainActivity.class);
                if(select == titlelist.length-1){ //새로운 육아일기
                    intent.putExtra("category", "new");
                } else { //기존 육아일기
                    JoinMainActivity.familyVO.setTitle(titlelist[select]);
                    intent.putExtra("category", "old");
                }
                startActivity(intent);
            }
        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                my_spinner.setSelection(0);
            }
        }).setCancelable(false).show();

        /*TextView tv = dialog.findViewById(R.id.title);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "@font/aa");
        tv.setTypeface(typeface);*/
    }
}