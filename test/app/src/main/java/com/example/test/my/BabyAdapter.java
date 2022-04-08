package com.example.test.my;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.test.MainActivity;
import com.example.test.R;
import com.example.test.common.CommonVal;
import com.google.gson.Gson;

import java.util.List;

public class BabyAdapter extends RecyclerView.Adapter<BabyAdapter.ViewHolder>{
    List<BabyInfoVO> list;
    Context context;
    LayoutInflater inflater;
    Gson gson = new Gson();

    public BabyAdapter(List<BabyInfoVO> list, Context context) {
        this.list = list;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_baby, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_name.setText(list.get(position).getBaby_name());
        holder.tv_gender.setText(list.get(position).getBaby_gender());
        holder.tv_my_birth.setText(list.get(position).getBaby_birth());
        holder.tv_body.setText(list.get(position).getBody());

        if(list.get(position).getBaby_photo() == null){
            holder.baby_imgv.setImageResource(R.drawable.bss_logo);
        } else{
            Glide.with(context).load(CommonVal.curbaby.getBaby_photo()).into(holder.baby_imgv);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name, tv_gender, tv_my_birth, tv_body;
        ImageView baby_imgv;
        LinearLayout baby_edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_gender = itemView.findViewById(R.id.tv_gender);
            tv_my_birth = itemView.findViewById(R.id.tv_my_birth);
            tv_body = itemView.findViewById(R.id.tv_body);
            baby_imgv = itemView.findViewById(R.id.baby_imgv);
            baby_edit = itemView.findViewById(R.id.baby_edit);

            baby_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new EditFragment(list.get(getAdapterPosition()));
                    ((MainActivity)context).backFrag(new EditFragment(list.get(getAdapterPosition())));
                    ((MainActivity)context).changeFrag(fragment);
                }
            });
        }
    }
}
