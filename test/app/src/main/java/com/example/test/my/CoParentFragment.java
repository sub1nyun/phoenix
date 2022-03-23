package com.example.test.my;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;

import java.io.InputStream;
import java.util.List;

public class CoParentFragment extends Fragment {
    RecyclerView rcv_co_parent;
    Button exit_family;
    ImageView family_back;
    List<FamilyInfoVO> list;

    public CoParentFragment(List<FamilyInfoVO> list) {
        this.list = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_co_parent, container, false);
        rcv_co_parent = rootView.findViewById(R.id.rcv_co_parent);
        exit_family = rootView.findViewById(R.id.exit_family);
        family_back = rootView.findViewById(R.id.family_back);

        //ArrayList<CoParentVO> list = new ArrayList<>(); //데이터 받아오는걸로 변경
        /*list.add(new CoParentDTO("엄마다", "엄마")); //테스트용
        list.add(new CoParentDTO("아빠다", "아빠"));
        list.add(new CoParentDTO("시터다", "시터"));
        list.add(new CoParentDTO("다른사람이다", "기타"));
        list.add(new CoParentDTO("할머니다", "할머니"));
        list.add(new CoParentDTO("할아버지다", "할아버지"));*/
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
                                //어디론가 이동
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
}