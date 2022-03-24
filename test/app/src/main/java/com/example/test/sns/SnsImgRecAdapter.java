package com.example.test.sns;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.bumptech.glide.Glide;
import com.example.test.R;

import java.util.ArrayList;

public class SnsImgRecAdapter extends RecyclerView.Adapter<SnsImgRecAdapter.Viewholder>{
    ArrayList<String> imgFilePath;
    ArrayList<SnsVO> list;
    LayoutInflater inflater;
    Context context;
    Activity activity;
    int size;

    public SnsImgRecAdapter(ArrayList<SnsVO> list, LayoutInflater inflater, Context context, int size) {
        this.list = list;
        this.inflater = inflater;
        this.context = context;
        this.size = size;
    }

    public SnsImgRecAdapter(ArrayList<String> imgFilePath, LayoutInflater inflater, Activity activity, int size) {
        this.imgFilePath = imgFilePath;
        this.inflater = inflater;
        this.activity = activity;
        this.size = size;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.sns_img_rec_item, parent, false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Glide.with(context).load(list.get(position).getSns_img()).into(holder.sns_new_img_rec_imv);

    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        ImageView sns_new_img_rec_imv;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            sns_new_img_rec_imv =itemView.findViewById(R.id.sns_new_img_rec_imv);
        }
    }
}
