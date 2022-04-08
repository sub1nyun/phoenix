package com.example.test.my;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.test.AddFragment;
import com.example.test.MainActivity;
import com.example.test.OnBackPressedListenser;
import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.example.test.home.HomeActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoParentFragment extends Fragment implements OnBackPressedListenser {
    RecyclerView rcv_co_parent;
    Button exit_family;
    ImageView family_back;
    List<FamilyInfoVO> list;
    String title;
    Gson gson = new Gson();

    public CoParentFragment(List<FamilyInfoVO> list, String title) {
        this.list = list;
        this.title = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_co_parent, container, false);
        rcv_co_parent = rootView.findViewById(R.id.rcv_co_parent);
        exit_family = rootView.findViewById(R.id.exit_family);
        family_back = rootView.findViewById(R.id.family_back);

        CoParentAdapter adapter = new CoParentAdapter(list, inflater);
        rcv_co_parent.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcv_co_parent.setLayoutManager(manager);

        //공동양육 포기
        exit_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder_exit = new AlertDialog.Builder(getContext()).setIcon(R.drawable.crying_baby).setTitle("공동 양육 포기").setMessage("소중한 우리 아이 기록을 다시는 볼 수 없습니다.\n정말 우리 아이를 포기하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AskTask task = new AskTask(CommonVal.httpip, "exit.family");
                                Log.d("title", "onClick: " + title);
                                task.addParam("title", title);
                                task.addParam("id", CommonVal.curuser.getId());
                                //task.addParam("baby_id", CommonVal.curbaby.getBaby_id());
                                InputStream in = CommonMethod.excuteGet(task);
                                if (gson.fromJson(new InputStreamReader(in), Boolean.class)) { //육아일기 탈퇴 성공
                                    Toast.makeText(getContext(), "성공적으로 탈퇴되었습니다.", Toast.LENGTH_SHORT).show();

                                    if(CommonVal.baby_list.size() == 1){
                                        CommonVal.baby_list.clear();
                                        CommonVal.curbaby = null;
                                        ((MainActivity)getActivity()).changeFrag(new AddFragment());
                                    } else{
                                        AskTask task_re = new AskTask(CommonVal.httpip, "list.bif");
                                        task_re.addParam("id", CommonVal.curuser.getId());
                                        InputStream in_re = CommonMethod.excuteGet(task_re);
                                        CommonVal.baby_list = gson.fromJson(new InputStreamReader(in_re), new TypeToken<List<BabyInfoVO>>(){}.getType());
                                        CommonVal.curbaby = CommonVal.baby_list.get(0);
                                        CommonVal.family_title.remove(title);

                                        ((MainActivity) getActivity()).changeFrag(new MyFragment());
                                    }
                                } else { //육아일기 탈퇴 실패
                                    Toast.makeText(getContext(), "탈퇴를 하지 못했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alertDialog = builder_exit.create();
                alertDialog.show();
            }
        });

        family_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(CoParentFragment.this).commit();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return rootView;
    }

    @Override
    public void onBackPressed() {
        family_back.callOnClick();
    }
}