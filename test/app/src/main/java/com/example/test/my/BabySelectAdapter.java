package com.example.test.my;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.R;

import java.util.List;

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
        return list.size();
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
        ImageView baby_info_photo = convertView.findViewById(R.id.baby_info_photo);
        TextView baby_info_name = convertView.findViewById(R.id.baby_info_name);
        TextView baby_info_title = convertView.findViewById(R.id.baby_info_title);

        if(position == list.size()-1){
            baby_info_photo.setImageResource(R.drawable.plus_baby);
            baby_info_name.setText("아이 추가");
            baby_info_title.setText("");
        } else {
            if(list.get(position).getBaby_photo() == null){
                baby_info_photo.setImageResource(R.drawable.bss_logo);
            } else{
                baby_info_photo.setImageBitmap(BitmapFactory.decodeFile(list.get(position).getBaby_photo()));
            }
            if(list.get(position).getTitle().length() < 8){
                baby_info_title.setText(list.get(position).getTitle());
            } else{
                baby_info_title.setText(list.get(position).getTitle().substring(0, 8));
            }
            baby_info_name.setText(list.get(position).getBaby_name());
        }

        return convertView;
    }
}