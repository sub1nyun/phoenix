package com.example.test.diary;

import android.content.Context;
import android.widget.TextView;

import com.example.test.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

public class MyMarker extends MarkerView {
    private TextView tv_marker;

    public MyMarker(Context context, int layoutResource) {
        super(context, layoutResource);
        tv_marker = findViewById(R.id.tv_marker);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        if(e instanceof CandleEntry){
            CandleEntry ce = (CandleEntry) e;
            tv_marker.setText("" + ce.getHigh());
        } else{
            tv_marker.setText("" + e.getY());
        }
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
