package com.example.test.sns;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.MainActivity;
import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class SnsViewAdapter extends RecyclerView.Adapter<SnsViewAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<GrowthVO> growthVOS;
    MainActivity activity;

    public SnsViewAdapter(LayoutInflater inflater, List<GrowthVO> growthVOS, MainActivity activity) {
        this.inflater = inflater;
        this.growthVOS = growthVOS;
        this.activity = activity;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.sns_growth_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return growthVOS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView baby_name, user_comment, gro_date;
        ImageView sns_more, baby_icon;
        RecyclerView rec_view;
        LinearLayout click;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            baby_name = itemView.findViewById(R.id.baby_name);
            user_comment = itemView.findViewById(R.id.user_comment);
            sns_more = itemView.findViewById(R.id.sns_more);
            rec_view = itemView.findViewById(R.id.rec_view);
            baby_icon = itemView.findViewById(R.id.baby_icon);
            gro_date = itemView.findViewById(R.id.gro_date);
            click = itemView.findViewById(R.id.click);
        }

        public void bind(@NonNull ViewHolder holder, int position) {
            SnsPickAdapter imgAdapter = new SnsPickAdapter(inflater, growthVOS.get(position).getImgList(), activity);
            RecyclerView.LayoutManager imgmanger = new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false);

            holder.rec_view.setAdapter(imgAdapter);
            holder.rec_view.setLayoutManager(imgmanger);

            baby_name.setText(CommonVal.curbaby.getBaby_name());
            user_comment.setText(growthVOS.get(position).getGro_content());
            sns_more.setVisibility(View.VISIBLE);

            if (growthVOS.get(position).getBaby_gender().equals("남아")) {
                baby_icon.setImageResource(R.drawable.tmdwn_boy);
            } else {
                baby_icon.setImageResource(R.drawable.tmdwn_girl);
            }
            gro_date.setText(growthVOS.get(position).getGro_date());

            if (growthVOS.get(position).getImgList().get(0) == null) {
                holder.rec_view.setVisibility(View.GONE);
            }

            holder.click.setOnClickListener(view -> {
                AskTask editTask = new AskTask(CommonVal.httpip, "detail.sn");
                Gson gson = new Gson();
                editTask.addParam("no", growthVOS.get(position).getGro_no()+"");
                InputStream in = CommonMethod.excuteGet(editTask);

                GrowthVO vo = gson.fromJson(new InputStreamReader(in), new TypeToken<GrowthVO>(){}.getType());
                Intent intent = new Intent(activity, GroDetailMainActivity.class);
                intent.putExtra("vo",gson.toJson(vo));
                activity.startActivityForResult(intent, 7);
            });

            holder.sns_more.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("성장일지 관리하기").setMessage("");
                builder.setPositiveButton("수정하기", new DialogInterface.OnClickListener() {
                    @Override
                   public void onClick(DialogInterface dialog, int which) {
                        AskTask editTask = new AskTask(CommonVal.httpip, "groselect.sn");
                        Gson gson = new Gson();
                        editTask.addParam("no", growthVOS.get(position).getGro_no()+"");
                        InputStream in = CommonMethod.excuteGet(editTask);

                        GrowthVO vo = gson.fromJson(new InputStreamReader(in), new TypeToken<GrowthVO>(){}.getType());
                        Intent intent = new Intent(activity, EditActivity.class);
                        intent.putExtra("vo",gson.toJson(vo));
                        activity.startActivityForResult(intent, 7);
                    }
                });
                builder.setNegativeButton("삭제하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                        builder1.setTitle("정말 삭제하실건가요?").setMessage("");
                        builder1.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder1.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AskTask delTask = new AskTask(CommonVal.httpip, "delete.sn");
                                delTask.addParam("no", growthVOS.get(position).getGro_no()+"");
                                CommonMethod.excuteGet(delTask);

                                activity.changeFrag(new SnsFragment(activity));
                            }
                        }).show();
                    }
                }).show();

            });



        }
    }
}


