package com.example.test.iot;

import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {
    ArrayList<MusicDTO> list;
    LayoutInflater inflater;

    public MusicAdapter(ArrayList<MusicDTO> list, LayoutInflater inflater) {
        this.list = list;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.music_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.music_title.setText(list.get(position).music_title);
        //음악이 플레이 중이면 일시정지로, 정지 중이면 재생으로 이미지 변경 (isPlaying)
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView music_title;
        ImageView music_status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            music_title = itemView.findViewById(R.id.music_title);
            music_status = itemView.findViewById(R.id.music_status);
        }
    }
}