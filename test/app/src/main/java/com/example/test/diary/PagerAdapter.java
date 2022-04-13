package com.example.test.diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends RecyclerView.Adapter<PagerAdapter.ViewHolder>{

    LayoutInflater inflater;
    Context context;
    List<String> temp = new ArrayList<>();

    public PagerAdapter(Context context, List<String> temp) {
        this.context = context;
        this.temp = temp;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.pager_item , parent , false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(temp.get(position)).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return temp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.edit_photo);
        }
    }
}