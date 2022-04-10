package com.example.test.sns;

import android.app.Activity;
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

public class SnsImgRecAdapter extends RecyclerView.Adapter<SnsImgRecAdapter.Viewholder>{
    ArrayList<String> imgFilePathList;
    LayoutInflater inflater;
    Activity activity;

    public SnsImgRecAdapter(ArrayList<String> imgFilePathList, Activity activity) {
        this.imgFilePathList = imgFilePathList;
        this.activity = activity;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.sns_img_rec_item, parent, false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        if(imgFilePathList.size() !=0) {
            Glide.with(activity).load(imgFilePathList.get(position)).into(holder.sns_new_img_rec_imv);
        }


    }

    @Override
    public int getItemCount() {
        if(imgFilePathList.size() ==0) {
           return 1;
        }
        return imgFilePathList.size();
     }

    public class Viewholder extends RecyclerView.ViewHolder{
        ImageView sns_new_img_rec_imv;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            sns_new_img_rec_imv =itemView.findViewById(R.id.sns_new_img_rec_imv);

        }
    }
}
