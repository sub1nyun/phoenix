package com.example.test.sns;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.test.R;

import java.util.ArrayList;

public class Sns_MultiImageAdapter extends RecyclerView.Adapter<Sns_MultiImageAdapter.ViewHoler>{
    ArrayList<Uri> mData = null;
    Context context = null;
    LayoutInflater inflater;

    public Sns_MultiImageAdapter(ArrayList<Uri> mData, Context context, LayoutInflater inflater) {
        this.mData = mData;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View itemView = inflater.inflate(R.layout.sns_image_item, parent, false);
        return new ViewHoler(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        Uri image_uri = mData.get(position);
        Glide.with(context).load(image_uri).into(holder.images);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }




    public class ViewHoler extends RecyclerView.ViewHolder {
        ImageView images;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);

            images = itemView.findViewById(R.id.images);
        }
    }
}
