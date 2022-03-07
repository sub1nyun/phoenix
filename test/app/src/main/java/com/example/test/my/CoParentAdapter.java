package com.example.test.my;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;

import java.util.ArrayList;

public class CoParentAdapter extends RecyclerView.Adapter<CoParentAdapter.ViewHolder> {
    ArrayList<CoParentDTO> list;
    LayoutInflater inflater;

    public CoParentAdapter(ArrayList<CoParentDTO> list, LayoutInflater inflater) {
        this.list = list;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.co_parent_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.co_parent_name.setText(list.get(position).getCo_name());
        holder.co_parent_rels.setText(list.get(position).getCo_rels());
        if(list.get(position).getCo_rels().equals("엄마")){
            holder.co_parent_img.setImageResource(R.drawable.mother);
        } else if(list.get(position).getCo_rels().equals("아빠")){
            holder.co_parent_img.setImageResource(R.drawable.father);
        } else if(list.get(position).getCo_rels().equals("할머니")){
            holder.co_parent_img.setImageResource(R.drawable.grandmother);
        } else if(list.get(position).getCo_rels().equals("할아버지")){
            holder.co_parent_img.setImageResource(R.drawable.grandfather);
        } else if(list.get(position).getCo_rels().equals("시터")){
            holder.co_parent_img.setImageResource(R.drawable.sitter);
        } else{
            holder.co_parent_img.setImageResource(R.drawable.other_people);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView co_parent_img;
        TextView co_parent_name, co_parent_rels;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            co_parent_img = itemView.findViewById(R.id.co_parent_img);
            co_parent_name = itemView.findViewById(R.id.co_parent_name);
            co_parent_rels = itemView.findViewById(R.id.co_parent_rels);
        }
    }
}
