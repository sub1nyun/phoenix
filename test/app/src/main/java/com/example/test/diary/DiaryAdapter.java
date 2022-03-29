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
            holder.tv_how.setText(diffMin+"분");
            holder.tv_start.setText(list.get(i).getStart_time()+"");
            if(list.get(i).getBaby_category().equals("모유")){
                holder.imv_state.setImageResource(R.drawable.mou);
            } else if(list.get(i).getBaby_category().equals("분유")){
                holder.imv_state.setImageResource(R.drawable.bunu);
            } else if(list.get(i).getBaby_category().equals("이유식")){
                holder.imv_state.setImageResource(R.drawable.eat);
            } else if(list.get(i).getBaby_category().equals("기저귀")){
                holder.imv_state.setImageResource(R.drawable.toilet);
            } else if(list.get(i).getBaby_category().equals("수면")){
                holder.imv_state.setImageResource(R.drawable.sleep);
            } else if(list.get(i).getBaby_category().equals("목욕")){
                holder.imv_state.setImageResource(R.drawable.bath);
            } else if(list.get(i).getBaby_category().equals("체온")){
                holder.imv_state.setImageResource(R.drawable.temp);
            } else if(list.get(i).getBaby_category().equals("물")){
                holder.imv_state.setImageResource(R.drawable.water);
            } else if(list.get(i).getBaby_category().equals("투약")){
                holder.imv_state.setImageResource(R.drawable.pills);
            } else if(list.get(i).getBaby_category().equals("간식")){
                holder.imv_state.setImageResource(R.drawable.danger);
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

}
