package com.example.test.iot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.test.IotFragment;
import com.example.test.MainActivity;
import com.example.test.R;

import java.util.ArrayList;

public class MusicFragment extends Fragment {
    ImageView back_music;
    RecyclerView rcv_music;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_music, container, false);
        back_music = rootView.findViewById(R.id.back_music);
        rcv_music = rootView.findViewById(R.id.rcv_music);

        back_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFrag(new IotFragment());
            }
        });

        ArrayList<MusicDTO> list = new ArrayList<>();
        //음악 저장해서 리스트 추가
        MusicAdapter adapter = new MusicAdapter(list, inflater);
        rcv_music.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcv_music.setLayoutManager(manager);

        return rootView;
    }
}