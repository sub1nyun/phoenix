package com.example.test.sns;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SnsViewAdapter extends RecyclerView.Adapter<SnsViewAdapter.ViewHolder> {

    LayoutInflater inflater;
    Context context;
    List<GrowthVO> GroVoList;

    public SnsViewAdapter(LayoutInflater inflater, Context context, List<GrowthVO> GroVoList) {
        this.inflater = inflater;
        this.context = context;
        this.GroVoList = GroVoList;
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
        AskTask task = new AskTask("http://192.168.0.11", "select.sn");
        Gson gson = new Gson();
        task.addParam("baby_id",CommonVal.curbaby.getBaby_id());
        InputStream in = CommonMethod.excuteGet(task);
        List<GrowthVO> growthVOS = gson.fromJson(new InputStreamReader(in), new TypeToken<List<GrowthVO>>(){}.getType());

        String imgList =  growthVOS.get(position).getImgList().get(0);
        String[] test =  imgList.split(",");
//        growthVOS.get(position).setImgList(Arrays.asList(test));
//        ArrayList<GrowthVO>

//         String test = growthVOS.get(position).getBaby_name();
//         String a = "";





        holder.sns_more.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("수정이나 삭제").setMessage("");
            builder.setPositiveButton("수정하기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(context, SnsNewActivity.class);
                    AskTask editTask = new AskTask("http://192.168.0.11", "edit.sn");

                }
            });
            builder.setNegativeButton("삭제하기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setTitle("정말 삭제하실건가요?").setMessage("");
                    builder1.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder1.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AlertDialog alertDialog = builder1.create();
                            alertDialog.show();
                            AskTask delete = new AskTask("http://192.168.0.11", "del.sn");
                            delete.addParam("no", GroVoList.get(position).getGro_no() + "");
                            CommonMethod.excuteGet(delete);
                        }
                    });
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

//        sns_more.setOnClickListener(view -> {
//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//            builder.setTitle("수정이나 삭제할 게시물").setMessage("");
//            builder.setPositiveButton("수정하기", (dialogInterface, i) -> {
//                Intent intent = new Intent(getContext(), SnsNewActivity.class);
//                startActivity(intent);
//            });
//            builder.setNegativeButton("삭제하기", (dialogInterface, i) -> {
//                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
//                builder1.setCancelable(false);
//                builder1.setTitle("정말 삭제할거임?").setMessage("");
//                builder1.setPositiveButton("취소", (dialogInterface1, i1) -> {
//                });
//                builder1.setNegativeButton("삭제", (dialogInterface1, i1) -> {
//                });
//                AlertDialog alertDialog = builder1.create();
//                alertDialog.show();
//            });
//            AlertDialog alertDialog = builder.create();
//            alertDialog.show();
//        });

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView baby_name, user_comment;
        ImageView sns_more, baby_icon;
        RecyclerView rec_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            baby_name = itemView.findViewById(R.id.baby_name);
            user_comment = itemView.findViewById(R.id.user_comment);
            sns_more = itemView.findViewById(R.id.sns_more);
            rec_view = itemView.findViewById(R.id.rec_view);
            baby_icon = itemView.findViewById(R.id.baby_icon);
        }
        public void bind(@NonNull ViewHolder holder, int position){
            SnsPickAdapter imgAdapter = new SnsPickAdapter(inflater);
            RecyclerView.LayoutManager imgmanger = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);

            holder.rec_view.setAdapter(imgAdapter);
            holder.rec_view.setLayoutManager(imgmanger);

            baby_name.setText(CommonVal.curbaby.getBaby_name());



        }


    }
}
