package com.example.test.sns;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.test.R;

import java.util.ArrayList;

public class SnsViewPagerAdapter extends RecyclerView.Adapter<SnsViewPagerAdapter.ViewHolder>{

    LayoutInflater inflater;
    ArrayList<SnsVO> snslist;
    Context context;

    public SnsViewPagerAdapter(LayoutInflater inflater, ArrayList<SnsVO> snslist, Context context) {
        this.inflater = inflater;
        this.snslist = snslist;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.sns_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(snslist.get(position).getSnsImg()+"").into(  holder.sns_item_imgv);
        //holder.sns_item_imgv.setImageResource(snslist.get(position).getSnsImg()); test
        holder.sns_item_text.setText(snslist.get(position).getSnscomment());

    }

    @Override
    public int getItemCount() {
        return snslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView sns_item_imgv;
        TextView sns_item_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sns_item_imgv = itemView.findViewById(R.id.sns_item_imgv);
            sns_item_text = itemView.findViewById(R.id.sns_item_text);
        }

    }



}
