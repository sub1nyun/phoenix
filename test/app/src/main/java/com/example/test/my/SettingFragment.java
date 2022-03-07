package com.example.test.my;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.test.MainActivity;
import com.example.test.R;
import com.example.test.my.MyFragment;

public class SettingFragment extends Fragment {
    ImageView setting_back;
    TextView set_secession, set_logout;
    Switch set_bell, set_vibration;
    SeekBar set_bell_volume, set_vibration_volume;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_setting, container, false);
        setting_back = rootView.findViewById(R.id.setting_back);
        set_secession = rootView.findViewById(R.id.set_secession);
        set_logout = rootView.findViewById(R.id.set_logout);
        set_bell = rootView.findViewById(R.id.set_bell);
        set_vibration = rootView.findViewById(R.id.set_vibration);
        set_bell_volume = rootView.findViewById(R.id.set_bell_volume);
        set_vibration_volume = rootView.findViewById(R.id.set_vibration_volume);

        setting_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFrag(new MyFragment());
            }
        });

        set_secession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder_secession = new AlertDialog.Builder(getContext()).setTitle("회원탈퇴").setMessage("저장된 기록이 모두 삭제됩니다.\n정말 탈퇴하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //회원 정보 삭제, 로그인 화면으로 이동
                            }
                        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alertDialog = builder_secession.create();
                alertDialog.show();
            }
        });

        set_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder_logout = new AlertDialog.Builder(getContext()).setTitle("로그아웃").setMessage("정말 로그아웃 하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //로그인 화면으로 이동
                            }
                        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alertDialog = builder_logout.create();
                alertDialog.show();
            }
        });

        return rootView;
    }
}