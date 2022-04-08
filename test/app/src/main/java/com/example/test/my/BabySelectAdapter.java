package com.example.test.my;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BabySelectAdapter extends BaseAdapter {
    List<BabyInfoVO> list;
    LayoutInflater inflater;
    Context context;

    public BabySelectAdapter(List<BabyInfoVO> list, LayoutInflater inflater, Context context) {
        this.list = list;
        this.inflater = inflater;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.my_spinner_item, parent, false);
//        CircleImageView baby_info_photo = convertView.findViewById(R.id.baby_info_photo);
        RoundedImageView baby_info_photo = convertView.findViewById(R.id.baby_info_photo);
        TextView baby_info_name = convertView.findViewById(R.id.baby_info_name);
        TextView baby_info_title = convertView.findViewById(R.id.baby_info_title);
        ImageView imv_baby = convertView.findViewById(R.id.imv_baby);


        if(position == list.size()){
            imv_baby.setVisibility(View.GONE);
            baby_info_photo.setImageResource(R.drawable.plus_baby);
            baby_info_name.setText("아이 추가");
            baby_info_title.setText("");
        } else {
            if(list.get(position).getBaby_photo() == null){
                baby_info_photo.setVisibility(View.GONE);
            } else{
                baby_info_photo.setVisibility(View.VISIBLE);
                Glide.with(context).load(list.get(position).getBaby_photo()).into(baby_info_photo);
            }

            baby_info_name.setText(list.get(position).getBaby_name());
            baby_info_title.setText(list.get(position).getBaby_birth().split(" ")[0].substring(2).replace("-", "/"));
        }

        return convertView;
    }
}