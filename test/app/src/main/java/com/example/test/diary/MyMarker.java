package com.example.test.diary;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.example.test.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

public class MyMarker extends MarkerView {
    private TextView tv_marker;
    String category;
    ArrayList<String> date;

    public MyMarker(Context context, int layoutResource, String category, ArrayList<String> date) {
        super(context, layoutResource);
        tv_marker = findViewById(R.id.tv_marker);
        this.category = category;
        this.date = date;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        if(e instanceof CandleEntry){
            if(category.equals("heat")){
                String[] diary_date = new String[date.size()];
                String[] time_date = new String[date.size()];

                for(int i=0; i<date.size(); i++){
                    diary_date[i] = date.get(i).split(",")[1];
                    time_date[i] = date.get(i).split(",")[0];
                }

                CandleEntry ce = (CandleEntry) e;
                Log.d("asd", "refreshContent: " + ce.getX());
                tv_marker.setText(diary_date[(int)ce.getX()] + " " + time_date[(int)ce.getX()] + "\n" + ce.getHigh() + "°C");
            } else if(category.equals("cm")){
                CandleEntry ce = (CandleEntry) e;
                tv_marker.setText("" + ce.getHigh() + "cm");
            } else if(category.equals("kg")){
                CandleEntry ce = (CandleEntry) e;
                tv_marker.setText("" + ce.getHigh() + "kg");
            }
            /*CandleEntry ce = (CandleEntry) e;
            tv_marker.setText("" + ce.getHigh());*/
        } else{
            if(category.equals("heat")){
                String[] diary_date = new String[date.size()];
                String[] time_date = new String[date.size()];

                for(int i=0; i<date.size(); i++){
                    diary_date[i] = date.get(i).split(",")[1];
                    time_date[i] = date.get(i).split(",")[0];
                }
                tv_marker.setText(diary_date[(int)e.getX()] + " " + time_date[(int)e.getX()] + "\n" + e.getY() + "°C");
            } else if(category.equals("cm")){
                tv_marker.setText("" + e.getY() + "cm");
            } else if(category.equals("kg")){
                tv_marker.setText("" + e.getY() + "kg");
            }
            //tv_marker.setText("" + e.getY());
        }
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
