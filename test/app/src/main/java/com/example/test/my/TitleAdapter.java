package com.example.test.my;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
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
import java.util.ArrayList;
import java.util.List;

public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.ViewHolder>{
    List<List<BabyInfoVO>> list;
    List<String> title;
    List<List<FamilyInfoVO>> coparent = new ArrayList<>();
    Context context;
    LayoutInflater inflater;
    String temp_title = "";
    Gson gson = new Gson();

    public TitleAdapter(List<String> title, List<List<BabyInfoVO>> list, Context context) {
        this.title = title;
        this.list = list;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_title, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.my_title.setText(title.get(position));
        BabyAdapter adapter = new BabyAdapter(list.get(position), context);
        holder.baby_rcv.setHasFixedSize(true);
        holder.baby_rcv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        holder.baby_rcv.setAdapter(adapter);

        AskTask task = new AskTask(CommonVal.httpip, "coparent.bif");
        task.addParam("title", title.get(position));
        InputStream in = CommonMethod.excuteGet(task);
        coparent.add(gson.fromJson(new InputStreamReader(in), new TypeToken<List<FamilyInfoVO>>(){}.getType()));
    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView my_title;
        RecyclerView baby_rcv;
        ImageView my_diary_title_edit, my_family;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            my_title = itemView.findViewById(R.id.my_title);
            baby_rcv = itemView.findViewById(R.id.baby_rcv);
            my_diary_title_edit = itemView.findViewById(R.id.my_diary_title_edit);
            my_family = itemView.findViewById(R.id.my_family);

            my_family.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)context).backFrag(new CoParentFragment(coparent.get(getAdapterPosition())));
                    ((MainActivity)context).changeFrag(new CoParentFragment(coparent.get(getAdapterPosition())));
                }
            });

            my_diary_title_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DiaryTitleDialog dialog = new DiaryTitleDialog(context, list.get(getAdapterPosition()).get(0).getTitle());
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    dialog.show();
                    Window window = dialog.getWindow();
                    window.setAttributes(lp);
                    temp_title = list.get(getAdapterPosition()).get(0).getTitle();
                    dialog.setDialogListener(new DiaryTitleDialog.DialogListener() {
                        @Override
                        public void onPositiveClick(String name) {
                            AskTask task = new AskTask(CommonVal.httpip, "chTitle.bif");
                            task.addParam("title", name);
                            task.addParam("old_title", CommonVal.family_title.get(getAdapterPosition()));
                            InputStream in = CommonMethod.excuteGet(task);

                            Log.d("asd", "onPositiveClick: " + list.get(getAdapterPosition()).get(0).getTitle());
                            for(int i=0; i<CommonVal.baby_list.size(); i++){
                                if(CommonVal.baby_list.get(i).getTitle().equals(temp_title)){
                                    CommonVal.baby_list.get(i).setTitle(name);
                                }
                            }

                            Log.d("asd", "onPositiveClick: " + list.get(getAdapterPosition()).get(0).getTitle());
                            for(int i=0; i<CommonVal.family_title.size(); i++){
                                if(CommonVal.family_title.get(i).equals(temp_title)){
                                    CommonVal.family_title.set(i, name);
                                }
                            }

                            ((MainActivity)context).changeFrag(new MyFragment());
                        }
                    });
                }
            });
        }
    }
}