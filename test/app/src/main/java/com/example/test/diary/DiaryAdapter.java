package com.example.test.diary;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.ViewHolder>{

    List<DiaryVO> list;
    LayoutInflater inflater;
    Context context;

    Gson gson = new Gson();


    public DiaryAdapter(List<DiaryVO> list, Context context) {
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
        //return  0;
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
            holder.tv_state.setText(list.get(i).getBaby_category());
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            long diffMin = 0;
            try {
                if(list.get(i).getEnd_time() != null){
                    Date date1 = dateFormat.parse(list.get(i).getEnd_time());
                    Date date2 = dateFormat.parse(list.get(i).getStart_time());
                    diffMin = (date1.getTime() - date2.getTime()) / 60000;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(diffMin == 0){
                holder.tv_how.setText("");
            } else if(diffMin >= 61){
                holder.tv_how.setText((diffMin/60)+"시간 " + (diffMin%60)+"분");
            }else{
                holder.tv_how.setText(diffMin+"분");
            }
            holder.tv_start.setText(list.get(i).getStart_time()+"");
            if(list.get(i).getBaby_category().equals("모유")){
                setColor("#e2a2d6",holder);
            } else if(list.get(i).getBaby_category().equals("분유")){
                setColor("#8fd9b9",holder);
            } else if(list.get(i).getBaby_category().equals("이유식")){
                setColor("#ffd857",holder);
            } else if(list.get(i).getBaby_category().equals("기저귀")){
                setColor("#966432",holder);
            } else if(list.get(i).getBaby_category().equals("수면")){
                setColor("#bbb1e5",holder);
            } else if(list.get(i).getBaby_category().equals("목욕")){
                setColor("#6eb5cf",holder);
            } else if(list.get(i).getBaby_category().equals("체온")){
                setColor("#d68684",holder);
            } else if(list.get(i).getBaby_category().equals("물")){
                setColor("#6eb5cf",holder);
            } else if(list.get(i).getBaby_category().equals("투약")){
                setColor("#51c040",holder);
            } else if(list.get(i).getBaby_category().equals("간식")){
                setColor("#fcb860",holder);
            }
            holder.imv_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);

                    AskTask task = new AskTask(CommonVal.httpip,"detail.di");
                    //수정
                    task.addParam("no",list.get(i).getDiary_id()+"");
                    InputStream in = CommonMethod.excuteGet(task);
                    DiaryVO dto = gson.fromJson(new InputStreamReader(in), new TypeToken<DiaryVO>(){}.getType());

                    intent.putExtra("dto",dto);
                    intent.putExtra("is_info",true);
                    ((Activity)context).startActivityForResult(intent, 1000);
                }
            });
        }
    }
    public void setColor(String strColor,ViewHolder holder){
        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.circle);
        drawable.setColor(Color.parseColor(strColor));
        holder.imv_state.setImageDrawable(drawable);
    }
}
