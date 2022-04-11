package com.example.test.sns;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.test.R;

import java.util.List;

public class SnsPickAdapter extends RecyclerView.Adapter<SnsPickAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<String> growthVOS;
    Activity activity;

    public SnsPickAdapter(LayoutInflater inflater, List<String> growthVOS, Activity activity) {
        this.inflater = inflater;
        this.growthVOS = growthVOS;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.sns_item_pick, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (growthVOS.size() > 0) {
            Glide.with(activity).load(growthVOS.get(position)).into(holder.gro_rec);
        } else {
            holder.gro_rec.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return growthVOS.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView gro_rec;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gro_rec = itemView.findViewById(R.id.gro_rec);

        }

    }
}
