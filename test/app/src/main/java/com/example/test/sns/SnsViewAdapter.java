package com.example.test.sns;

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
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.google.gson.Gson;

import java.util.List;

public class SnsViewAdapter extends RecyclerView.Adapter<SnsViewAdapter.ViewHolder>{

    LayoutInflater inflater;
    Context context;
    List<SnsVO> SnsVoList;
    List<SnsReplyVO> ReList;

    public SnsViewAdapter(LayoutInflater inflater, Context context, List<SnsVO> snsVoList, List<SnsReplyVO> reList) {
        this.inflater = inflater;
        this.context = context;
        this.SnsVoList = snsVoList;
        this.ReList = reList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.sns_instar_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        AskTask snslist = new AskTask("http://192.168.0.11", "list.sn");
//        Gson gson = new Gson();
//        String testVO = gson.toJson(SnsVoList);
//        String testRE = gson.toJson(ReList);
//        snslist.addParam("snsVO", testVO);
//        snslist.addParam("ReVO", testRE);

        //holder.writer.setText();






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
                            delete.addParam("no", SnsVoList.get(position).getSns_no()+"");
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
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView writer, writer_comment, user_id, user_comment, writer_re, writer_re_comment;
        ImageView sns_more, reply_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            writer = itemView.findViewById(R.id.writer);
            writer_comment = itemView.findViewById(R.id.writer_comment);
            user_id = itemView.findViewById(R.id.user_id);
            user_comment = itemView.findViewById(R.id.user_comment);
            writer_re = itemView.findViewById(R.id.writer_re);
            writer_re_comment = itemView.findViewById(R.id.writer_re_comment);
            sns_more = itemView.findViewById(R.id.sns_more);
            reply_btn = itemView.findViewById(R.id.reply_btn);
        }
    }










}
