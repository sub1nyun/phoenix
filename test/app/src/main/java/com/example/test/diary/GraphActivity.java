package com.example.test.diary;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.renderer.LineChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity{
    TabLayout tab_graph;
    ImageView back_graph;
    Gson gson = new Gson();

    private LineChart lineChart;
    ArrayList<Entry> values = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        lineChart = findViewById(R.id.chart1);
        tab_graph = findViewById(R.id.tab_graph);
        back_graph = findViewById(R.id.back_graph);

        AskTask task_heat = new AskTask(CommonVal.httpip, "select_heat.stor");
        task_heat.addParam("baby_id", CommonVal.curbaby.getBaby_id());
        InputStream in_heat = CommonMethod.excuteGet(task_heat);
        List<DiaryVO> list_heat = gson.fromJson(new InputStreamReader(in_heat), new TypeToken<List<DiaryVO>>(){}.getType());

        AskTask task_body = new AskTask(CommonVal.httpip, "select_graph.stor");
        task_body.addParam("baby_id", CommonVal.curbaby.getBaby_id());
        InputStream in_body = CommonMethod.excuteGet(task_body);
        List<BabyStorVO> list_body = gson.fromJson(new InputStreamReader(in_body), new TypeToken<List<BabyStorVO>>(){}.getType());

        lineChart.clear();
        date.clear();
        values.clear();
        for (int i = 0; i < list_heat.size(); i++) {
            date.add(list_heat.get(i).getStart_time());
            values.add(new Entry(i, (float) list_heat.get(i).getTemperature()));
        }
        makeChart(values, date);

        tab_graph.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){ //체온
                    date.clear();
                    values.clear();
                    for (int i = 0; i < list_heat.size(); i++) {
                        date.add(list_heat.get(i).getStart_time());
                        values.add(new Entry(i, (float) list_heat.get(i).getTemperature()));
                    }
                    makeChart(values, date);
                } else if(tab.getPosition() == 1){ //키
                    date.clear();
                    values.clear();
                    for (int i = 0; i < list_body.size(); i++) {
                        date.add(list_body.get(i).getStor_date());
                        values.add(new Entry(i, (float) list_body.get(i).getStor_cm()));
                    }
                    makeChart(values, date);
                } else if(tab.getPosition() == 2){ //몸무게
                    date.clear();
                    values.clear();
                    for (int i = 0; i < list_body.size(); i++){
                        date.add(list_body.get(i).getStor_date());
                        values.add(new Entry(i, (float)list_body.get(i).getStor_kg()));
                    }
                    makeChart(values, date);
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

    public void makeChart(ArrayList<Entry> values, ArrayList<String> date){
        LineDataSet lineDataSet = new LineDataSet(values, "라벨 줘야돼?");
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
        xAxis.mAxisMinimum = 0f;
        xAxis.setLabelCount(values.size(), false);
        xAxis.enableGridDashedLine(8, 24, 0); //수직 격자선
        xAxis.mEntryCount = values.size();
        xAxis.setAvoidFirstLastClipping(true);

        xAxis.setValueFormatter(new IndexAxisValueFormatter(date));

        /*xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String a="";
                return date.get((int)value);
            }
        });*/

        YAxis yLAxis = lineChart.getAxisLeft(); //y축 설정
        yLAxis.setTextColor(Color.BLACK);
        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);
        /*Description description = new Description();
        description.setText("");*/

        MyMarker mm = new MyMarker(this, R.layout.custom_marker);
        mm.setChartView(lineChart);
        lineChart.setMarker(mm);

        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription(null);
        lineChart.setVisibleXRangeMaximum(2); //드래그
        lineChart.animateY(2000, Easing.EaseInCubic);
        lineChart.getLegend().setEnabled(false);
        lineChart.invalidate();
    }
}