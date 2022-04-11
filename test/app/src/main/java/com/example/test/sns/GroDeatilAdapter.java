package com.example.test.sns;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.test.R;

import java.util.ArrayList;

public class GroDeatilAdapter extends RecyclerView.Adapter<GroDeatilAdapter.ViewHolder>{
    LayoutInflater inflater;
    ArrayList<String> list;
    Context context;

    public GroDeatilAdapter(LayoutInflater inflater, ArrayList<String> list, Context context) {
        this.inflater = inflater;
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.gro_detail_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position)).into(holder.gro_detail_view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView gro_detail_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gro_detail_view = itemView.findViewById(R.id.gro_detail_view);
        }
    }
}
