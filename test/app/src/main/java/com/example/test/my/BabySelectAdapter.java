package com.example.test.my;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class BabySelectAdapter extends ArrayAdapter<String> {
    List<BabyInfoVO> list;
    Context context;
    int flag = 0 ;
    public BabySelectAdapter(@NonNull Context context, List<BabyInfoVO> list) {
        super(context, R.layout.my_spinner_item);
        this.list = list;
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return list.size()+1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.my_spinner_item, parent, false);
            viewHolder.baby_info_photo = convertView.findViewById(R.id.baby_info_photo);
            viewHolder.baby_info_name = convertView.findViewById(R.id.baby_info_name);
            viewHolder.baby_info_birth = convertView.findViewById(R.id.baby_info_birth);
            viewHolder.imv_boy = convertView.findViewById(R.id.imv_boy);
            viewHolder.imv_girl = convertView.findViewById(R.id.imv_girl);
            viewHolder.spinner = convertView.findViewById(R.id.spinner);

            if(flag != 0) {
                viewHolder.baby_info_birth.setTextColor(Color.parseColor("#000000"));
                viewHolder.baby_info_name.setTextColor(Color.parseColor("#000000"));
            } else {
                viewHolder.baby_info_birth.setTextColor(Color.parseColor("#FFFFFF"));
                viewHolder.baby_info_name.setTextColor(Color.parseColor("#FFFFFF"));
            }

            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(position == list.size()){
            viewHolder.imv_boy.setVisibility(View.GONE);
            viewHolder.imv_girl.setVisibility(View.GONE);
            viewHolder.baby_info_photo.setImageResource(R.drawable.plus_baby);
            viewHolder.baby_info_name.setText("아이 추가");
            viewHolder.baby_info_birth.setText("");
        } else{
            if(list.get(position).getBaby_photo() == null){
                if(list.get(position).getBaby_gender().equals("여아")) {
                    viewHolder.baby_info_photo.setVisibility(View.GONE);
                    viewHolder.imv_girl.setVisibility(View.VISIBLE);
                    viewHolder.imv_boy.setVisibility(View.GONE);
                } else {
                    viewHolder.baby_info_photo.setVisibility(View.GONE);
                    viewHolder.imv_boy.setVisibility(View.VISIBLE);
                    viewHolder.imv_girl.setVisibility(View.GONE);
                }
            } else{
                viewHolder.baby_info_photo.setVisibility(View.VISIBLE);
                Glide.with(context).load(list.get(position).getBaby_photo()).into(viewHolder.baby_info_photo);
            }
            viewHolder.baby_info_name.setText(list.get(position).getBaby_name());
            viewHolder.baby_info_birth.setText(list.get(position).getBaby_birth().split(" ")[0].substring(2).replace("-", "/"));
        }

        return convertView;
    }

    public class ViewHolder{
        RoundedImageView baby_info_photo;
        TextView baby_info_name, baby_info_birth;
        ImageView imv_boy, imv_girl;
        LinearLayout spinner;
    }
}