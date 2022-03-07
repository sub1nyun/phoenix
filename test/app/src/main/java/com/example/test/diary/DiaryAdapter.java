package com.example.test.diary;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;

import java.util.ArrayList;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.ViewHolder>{

    ArrayList<detailDTO> list = new ArrayList<>();
    LayoutInflater inflater;
    Context context;

    public DiaryAdapter(ArrayList<detailDTO> list, Context context) {
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_diary, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_state,tv_how,tv_start;
        ImageView imv_detail, imv_state;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imv_detail = itemView.findViewById(R.id.imv_detail);
            tv_state = itemView.findViewById(R.id.tv_state);
            tv_how = itemView.findViewById(R.id.tv_how);
            tv_start = itemView.findViewById(R.id.tv_start);
            imv_state = itemView.findViewById(R.id.imv_state);
        }
        public void bind(@NonNull ViewHolder holder, int i){
            holder.tv_state.setText(list.get(i).getState());
            holder.tv_how.setText("0분");
            holder.tv_start.setText(list.get(i).getStart_time());
            holder.imv_state.setImageResource(list.get(i).getImg());
            holder.imv_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("dto",new detailDTO("dbㅠ","00:00",R.drawable.danger));
                    ((Activity)context).startActivityForResult(intent, 1000);
                }
            });
        }
    }

}
