package com.example.test.diary;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.test.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity{
    TabLayout tab_graph;
    //TabItem tab_heat, tab_height, tab_weight;
    ImageView back_graph;
    List<DiaryVO> list_heat;

    private LineChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        lineChart = findViewById(R.id.chart1);
        tab_graph = findViewById(R.id.tab_graph);
        /*tab_heat = findViewById(R.id.tab_heat);
        tab_height = findViewById(R.id.tab_height);
        tab_weight = findViewById(R.id.tab_weight);*/
        back_graph = findViewById(R.id.back_graph);

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            float val = (float) (Math.random() * 10);
            values.add(new Entry(i, val));
            Log.d("asd", "onCreate: " + values.get(i));
        }

        LineDataSet lineDataSet = new LineDataSet(values, "속성명1");
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(6);
        lineDataSet.setCircleColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setCircleHoleColor(Color.BLUE);
        lineDataSet.setColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawValues(false);
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        XAxis xAxis = lineChart.getXAxis(); //x축 설정
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //x축 위치 설정
        xAxis.setTextColor(Color.BLACK);
        xAxis.setLabelCount(5, true);
        xAxis.enableGridDashedLine(8, 24, 0); //수직 격자선
        YAxis yLAxis = lineChart.getAxisLeft(); //y축 설정
        yLAxis.setTextColor(Color.BLACK);
        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);
        Description description = new Description();
        description.setText("");
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription(description);
        lineChart.setVisibleXRangeMaximum(2); //드래그
        lineChart.animateY(2000, Easing.EaseInCubic);
        lineChart.invalidate();

        tab_graph.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){ //체온

                } else if(tab.getPosition() == 1){ //키

                } else if(tab.getPosition() == 2){ //몸무게

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //뒤로가기
        back_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}