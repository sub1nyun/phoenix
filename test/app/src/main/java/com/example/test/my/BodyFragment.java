package com.example.test.my;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.test.MainActivity;
import com.example.test.R;

public class BodyFragment extends Fragment {
    TextView tv_name, tv_gender, edit_ok;
    EditText edit_weight, edit_height;
    ImageView edit_cancel, view_graph;
    BabyInfoVO vo;

    public BodyFragment(BabyInfoVO vo) {
        this.vo = vo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_body, container, false);
        edit_cancel = rootView.findViewById(R.id.edit_cancel);
        edit_ok = rootView.findViewById(R.id.edit_ok);
        tv_name = rootView.findViewById(R.id.tv_name);
        tv_gender = rootView.findViewById(R.id.tv_gender);
        edit_weight = rootView.findViewById(R.id.edit_weight);
        edit_height = rootView.findViewById(R.id.edit_height);
        view_graph = rootView.findViewById(R.id.view_graph);

        //초기 세팅
        tv_name.setText(vo.getBaby_name());
        if(vo.getBaby_gender().equals("남아")){
            tv_gender.setText("남자");
        } else if(vo.getBaby_gender().equals("여아")){
            tv_gender.setText("여자");
        } else{
            tv_gender.setText("성별 모름");
        }
        edit_weight.setText(vo.getBaby_kg()+"");
        edit_height.setText(vo.getBaby_cm()+"");

        //뒤로가기
        edit_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder_cancel = new AlertDialog.Builder(getContext()).setTitle("취소").setMessage("현재 수정하신 내용이 저장되지 않습니다.\n정말 취소하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().getSupportFragmentManager().beginTransaction().remove(BodyFragment.this).commit();
                                getActivity().getSupportFragmentManager().popBackStack();
                            }
                        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alertDialog = builder_cancel.create();
                alertDialog.show();
            }
        });

        //저장
       edit_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putDouble("kg", Double.parseDouble(edit_weight.getText().toString() != "" ? edit_weight.getText().toString() : "0.0"));
                bundle.putDouble("cm", Double.parseDouble(edit_height.getText().toString() != "" ? edit_height.getText().toString() : "0.0"));
                Fragment fragment = new EditFragment(vo);
                fragment.setArguments(bundle);
                ((MainActivity)getActivity()).changeFrag(fragment);
            }
        });

        return rootView;
    }
}