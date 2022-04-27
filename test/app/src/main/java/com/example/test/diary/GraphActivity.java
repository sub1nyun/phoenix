package com.example.test.diary;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity{
    TabLayout tab_graph;
    ImageView back_graph;
    Gson gson = new Gson();

    private LineChart lineChart;
    ArrayList<Entry> values = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> diar = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        lineChart = findViewById(R.id.chart1);
        tab_graph = findViewById(R.id.tab_graph);
        back_graph = findViewById(R.id.back_graph);

        lineChart.setNoDataText("2건 이상의 기록이 존재하지 않아 그래프를 그릴 수 없습니다.");
        lineChart.setNoDataTextColor(Color.parseColor("#FFA200"));
        Paint paint = lineChart.getPaint(Chart.PAINT_INFO);
        paint.setTextSize(40);

        ArrayList<Entry> min_heat = new ArrayList<>();
        ArrayList<Entry> max_heat = new ArrayList<>();

        ArrayList<Float> weight = new ArrayList<Float>();
        ArrayList<Float> height = new ArrayList<Float>();

        if(CommonVal.curbaby.getBaby_gender().equals("여아")){
            weight.add((float) 3.2);
            weight.add((float) 4.2);
            weight.add((float) 5.1);
            weight.add((float) 5.8);
            weight.add((float) 6.4);
            weight.add((float) 6.9);
            weight.add((float) 7.3);
            weight.add((float) 7.6);
            weight.add((float) 7.9);
            weight.add((float) 8.2);
            weight.add((float) 8.5);
            weight.add((float) 8.7);
            weight.add((float) 8.9);
            weight.add((float) 9.2);
            weight.add((float) 9.4);
            weight.add((float) 9.6);
            weight.add((float) 9.8);
            weight.add((float) 10.0);
            weight.add((float) 10.2);
            weight.add((float) 10.4);
            weight.add((float) 10.6);
            weight.add((float) 10.9);
            weight.add((float) 11.1);
            weight.add((float) 11.3);
            weight.add((float) 11.5);
            height.add((float) 49.1);
            height.add((float) 53.7);
            height.add((float) 57.1);
            height.add((float) 59.8);
            height.add((float) 62.1);
            height.add((float) 64.0);
            height.add((float) 65.7);
            height.add((float) 67.3);
            height.add((float) 68.7);
            height.add((float) 70.1);
            height.add((float) 71.5);
            height.add((float) 72.8);
            height.add((float) 74.0);
            height.add((float) 75.2);
            height.add((float) 76.4);
            height.add((float) 77.5);
            height.add((float) 78.6);
            height.add((float) 79.7);
            height.add((float) 80.7);
            height.add((float) 81.7);
            height.add((float) 82.7);
            height.add((float) 83.7);
            height.add((float) 84.6);
            height.add((float) 85.5);
            height.add((float) 85.7);
        } else{
            weight.add((float) 3.3);
            weight.add((float) 4.5);
            weight.add((float) 5.6);
            weight.add((float) 6.4);
            weight.add((float) 7.0);
            weight.add((float) 7.5);
            weight.add((float) 7.9);
            weight.add((float) 8.3);
            weight.add((float) 8.6);
            weight.add((float) 8.9);
            weight.add((float) 9.2);
            weight.add((float) 9.4);
            weight.add((float) 9.6);
            weight.add((float) 9.9);
            weight.add((float) 10.1);
            weight.add((float) 10.3);
            weight.add((float) 10.5);
            weight.add((float) 10.7);
            weight.add((float) 10.9);
            weight.add((float) 11.1);
            weight.add((float) 11.3);
            weight.add((float) 11.5);
            weight.add((float) 11.8);
            weight.add((float) 12.0);
            weight.add((float) 12.2);
            height.add((float) 49.9);
            height.add((float) 54.7);
            height.add((float) 58.4);
            height.add((float) 61.4);
            height.add((float) 63.9);
            height.add((float) 65.9);
            height.add((float) 67.6);
            height.add((float) 69.2);
            height.add((float) 70.6);
            height.add((float) 72.0);
            height.add((float) 73.3);
            height.add((float) 74.5);
            height.add((float) 75.7);
            height.add((float) 76.9);
            height.add((float) 78.0);
            height.add((float) 79.1);
            height.add((float) 80.2);
            height.add((float) 81.2);
            height.add((float) 82.3);
            height.add((float) 83.2);
            height.add((float) 84.2);
            height.add((float) 85.1);
            height.add((float) 86.0);
            height.add((float) 86.9);
            height.add((float) 87.1);
        }
        ArrayList<Entry> avg_height = new ArrayList<>();
        ArrayList<Entry> avg_weight = new ArrayList<>();
        for(int i=0; i<25; i++){
            avg_height.add(new Entry(i*30, height.get(i)));
            avg_weight.add(new Entry(i*30, weight.get(i)));
        }

        String baby_age_str = CommonVal.curbaby.getBaby_birth();
        String[] baby_age_arr = baby_age_str.substring(0,baby_age_str.indexOf(" ")).split("-");
        LocalDate theDate = LocalDate.of(Integer.parseInt(baby_age_arr[0]),Integer.parseInt(baby_age_arr[1]),Integer.parseInt(baby_age_arr[2]));

        AskTask task_heat = new AskTask(CommonVal.httpip, "select_heat.stor");
        task_heat.addParam("baby_id", CommonVal.curbaby.getBaby_id());
        InputStream in_heat = CommonMethod.excuteGet(task_heat);
        List<DiaryVO> list_heat = gson.fromJson(new InputStreamReader(in_heat), new TypeToken<List<DiaryVO>>(){}.getType());

        AskTask task_body = new AskTask(CommonVal.httpip, "select_graph.stor");
        task_body.addParam("baby_id", CommonVal.curbaby.getBaby_id());
        InputStream in_body = CommonMethod.excuteGet(task_body);
        List<BabyStorVO> list_body = gson.fromJson(new InputStreamReader(in_body), new TypeToken<List<BabyStorVO>>(){}.getType());

        lineChart.moveViewToX(0f);
        lineChart.clear();
        date.clear();
        values.clear();
        if(list_heat.size() != 0){
            if(list_heat.size() == 1){

            } else {
                for (int i = 0; i < list_heat.size(); i++) {
                    min_heat.add(new Entry(i*30, (float)36.4));
                    max_heat.add(new Entry(i*30, (float)38.0));
                    date.add(list_heat.get(i).getStart_time() + "," + list_heat.get(i).getDiary_date().split("-")[1] + "-" + list_heat.get(i).getDiary_date().split("-")[2]);
                    values.add(new Entry(i, (float) list_heat.get(i).getTemperature()));
                }
                makeChart(avg_weight, avg_height, min_heat, max_heat, values, date, "heat");
            }
        }

        tab_graph.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){ //체온
                    lineChart.moveViewToX(0f);
                    lineChart.clear();
                    date.clear();
                    values.clear();
                    if(list_heat.size() != 0){
                        if(list_heat.size() == 1){

                        } else {
                            for (int i = 0; i < list_heat.size(); i++) {
                                min_heat.add(new Entry(i*30, (float)36.4));
                                max_heat.add(new Entry(i*30, (float)38.0));
                                date.add(list_heat.get(i).getStart_time() + "," + list_heat.get(i).getDiary_date().split("-")[1] + "-" + list_heat.get(i).getDiary_date().split("-")[2]);
                                values.add(new Entry(i, (float) list_heat.get(i).getTemperature()));
                            }
                            makeChart(avg_weight, avg_height, min_heat, max_heat, values, date, "heat");
                        }
                    }
                } else if(tab.getPosition() == 1){ //키
                    lineChart.moveViewToX(0f);
                    lineChart.clear();
                    date.clear();
                    values.clear();
                    if(list_body.size() != 0){
                        if(list_body.size() == 1){
                        } else {
                            for (int i = 0; i < list_body.size(); i++) {
                                //date.add(list_body.get(i).getStor_date().split("-")[1] + "-" + list_body.get(i).getStor_date().split("-")[2]);
                                String baby_str = list_body.get(i).getStor_date();
                                String[] baby_arr = baby_str.split("-");
                                LocalDate thed = LocalDate.of(Integer.parseInt(baby_arr[0]),Integer.parseInt(baby_arr[1]),Integer.parseInt(baby_arr[2]));
                                String predate = Long.toString(theDate.until(thed, ChronoUnit.DAYS));
                                date.add(predate + "일");
                                values.add(new Entry(i, (float) list_body.get(i).getStor_cm()));
                                if(i==0){
                                    for(int j=avg_height.size()-1; j>=0; j--){
                                        if(avg_height.get(j).getX()<Integer.parseInt(predate)){
                                            avg_height.remove(j);
                                        }
                                    }
                                }
                            }
                            makeChart(avg_weight, avg_height, min_heat, max_heat, values, date,"cm");
                        }
                    }
                } else if(tab.getPosition() == 2){ //몸무게
                    lineChart.moveViewToX(0f);
                    lineChart.clear();
                    date.clear();
                    values.clear();
                    if(list_body.size() != 0){
                        if(list_body.size() == 1){
                        } else {
                            for (int i = 0; i < list_body.size(); i++) {
                                //date.add(list_body.get(i).getStor_date().split("-")[1] + "-" + list_body.get(i).getStor_date().split("-")[2]);
                                String baby_str = list_body.get(i).getStor_date();
                                String[] baby_arr = baby_str.split("-");
                                LocalDate thed = LocalDate.of(Integer.parseInt(baby_arr[0]),Integer.parseInt(baby_arr[1]),Integer.parseInt(baby_arr[2]));
                                String predate = Long.toString(theDate.until(thed, ChronoUnit.DAYS));
                                date.add(predate + "일");
                                values.add(new Entry(i, (float) list_body.get(i).getStor_kg()));

                                if(i==0){
                                    for(int j=avg_weight.size()-1; j>=0; j--){
                                        if(avg_weight.get(j).getX()<Integer.parseInt(predate)){
                                            avg_weight.remove(j);
                                        }
                                    }
                                }
                            }
                            makeChart(avg_weight, avg_height, min_heat, max_heat, values, date,"kg");
                        }
                    }
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

    //그래프 생성
    public void makeChart(ArrayList<Entry> avg_weight, ArrayList<Entry> avg_height, ArrayList<Entry> min_heat, ArrayList<Entry> max_heat, ArrayList<Entry> values, ArrayList<String> date, String category){
        lineChart.clear();
        lineChart.invalidate();

        ArrayList<LineDataSet> data_list = new ArrayList<>();
        data_list.add(new LineDataSet(values, "내아이"));
        data_list.add(new LineDataSet(min_heat, "최저체온"));
        data_list.add(new LineDataSet(max_heat, "최고체온"));
        data_list.add(new LineDataSet(avg_height, "키"));
        data_list.add(new LineDataSet(avg_weight, "체중"));

        for(int i=0 ;i<data_list.size(); i++){
            if(i == 0){
                data_list.get(i).setLineWidth(5);
                data_list.get(i).setCircleRadius(8);
            } else{
                data_list.get(i).setLineWidth(3);
                data_list.get(i).setCircleRadius(6);
            }
            data_list.get(i).setDrawCircleHole(true);
            data_list.get(i).setDrawCircles(true);
            data_list.get(i).setDrawHorizontalHighlightIndicator(false);
            data_list.get(i).setDrawHighlightIndicators(false);
            data_list.get(i).setDrawValues(false);
        }

        LineData lineData = new LineData();
        if(category.equals("heat")){
            lineData = new LineData(data_list.get(0), data_list.get(1), data_list.get(2));
        } else if(category.equals("cm")){
            lineData = new LineData(data_list.get(0), data_list.get(3));
        } else if(category.equals("kg")){
            lineData = new LineData(data_list.get(0), data_list.get(4));
        }
        lineChart.setData(lineData);
        XAxis xAxis = lineChart.getXAxis(); //x축 설정
        YAxis yLAxis = lineChart.getAxisLeft(); //y축 설정

        data_list.get(0).setCircleColor(Color.parseColor("#4da442ff"));
        data_list.get(0).setCircleHoleColor(Color.parseColor("#a442ff"));
        data_list.get(0).setColor(Color.parseColor("#99a442ff"));

        if(category.equals("heat")){
            data_list.get(2).setCircleColor(Color.parseColor("#4dff5e5e"));
            data_list.get(2).setCircleHoleColor(Color.parseColor("#ff5e5e"));
            data_list.get(2).setColor(Color.parseColor("#99ff5e5e"));

            data_list.get(1).setCircleColor(Color.parseColor("#4d4f81ff"));
            data_list.get(1).setCircleHoleColor(Color.parseColor("#4f81ff"));
            data_list.get(1).setColor(Color.parseColor("#994f81ff"));

            data_list.get(1).setMode(LineDataSet.Mode.STEPPED);

            String[] diary_date = new String[date.size()];
            String[] time_date = new String[date.size()];

            for(int i=0; i<date.size(); i++){
                diary_date[i] = date.get(i).split(",")[1];
                time_date[i] = date.get(i).split(",")[0];
            }

            String mdate = diary_date[0];
            for(int i=1; i<date.size(); i++){
                if(mdate.equals(diary_date[i])) diary_date[i] = "";
                else mdate = diary_date[i];
            }

            xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED); //x축 위치 설정

            //top x axis 설정
            lineChart.setXAxisRenderer(new DoubleXLabelAxisRenderer(lineChart.getViewPortHandler(), lineChart.getXAxis(), lineChart.getTransformer(YAxis.AxisDependency.LEFT), new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    if(value> diary_date.length-1){
                        return "";
                    }
                    return diary_date[(int)value];
                }
            }));

            //bottom x axis 설정
            xAxis.setValueFormatter(new IndexAxisValueFormatter(time_date));
            yLAxis.setAxisMinimum(33);
            yLAxis.setAxisMaximum(40);
            yLAxis.setGranularity(0.1f);
            xAxis.mAxisRange = 250;
        }else{
            if(category.equals("cm")){
                data_list.get(3).setCircleColor(Color.parseColor("#4d1c5ffc"));
                data_list.get(3).setCircleHoleColor(Color.parseColor("#1c5ffc"));
                data_list.get(3).setColor(Color.parseColor("#b31c5ffc"));
                yLAxis.setAxisMinimum(0);
                yLAxis.setAxisMaximum(150);
                yLAxis.setGranularity(0.1f);
                xAxis.mAxisRange = 250;
            }
            else if(category.equals("kg")){
                data_list.get(4).setCircleColor(Color.parseColor("#4d1cbdfc"));
                data_list.get(4).setCircleHoleColor(Color.parseColor("#1cbdfc"));
                data_list.get(4).setColor(Color.parseColor("#b31cbdfc"));
                yLAxis.setAxisMinimum(0);
                yLAxis.setAxisMaximum(100);
                yLAxis.setGranularity(0.1f);
                xAxis.mAxisRange = 250;
            }

            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(date)); //x축 표현을 string으로 포맷
        }

        xAxis.setTextColor(Color.BLACK);
        xAxis.mAxisMinimum = 0f;
        xAxis.mAxisRange = 250;
        xAxis.setGranularityEnabled(true); //이거 없으면 x축 데이터 반복되고 포인트랑 안맞음!

        xAxis.setLabelCount(values.size());
        xAxis.enableGridDashedLine(8, 24, 0); //수직 격자선
        xAxis.mEntryCount = values.size();
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setTextSize(13);

        yLAxis.setTextColor(Color.BLACK);
        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);
        yLAxis.setTextSize(13);

        MyMarker mm = new MyMarker(this, R.layout.custom_marker, category, date);
        mm.setChartView(lineChart);

        lineChart.setMarker(mm);

        lineChart.setExtraLeftOffset(5);
        lineChart.setExtraRightOffset(25);
        lineChart.setExtraBottomOffset(20);
        lineChart.setExtraTopOffset(20);

        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription(null);
        lineChart.setVisibleXRangeMaximum(2); //드래그
        lineChart.setVisibleXRangeMinimum(2);
        lineChart.animateY(2000, Easing.EaseInBounce);
        lineChart.getLegend().setEnabled(false); //속성표시
        //lineChart.moveViewTo(0f, 0f, YAxis.AxisDependency.LEFT);
        //lineChart.invalidate();
        //lineChart.moveViewTo(data_list.get(0).getEntryCount(), 50f, YAxis.AxisDependency.LEFT);
    }

    //위, 아래 x축에 다른 데이터 넣기 위해 필요한 클래스
    public class DoubleXLabelAxisRenderer extends XAxisRenderer {
        private IAxisValueFormatter topValueFormatter;

        public DoubleXLabelAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer transformer, IAxisValueFormatter topValueFormatter) {
            super(viewPortHandler, xAxis, transformer);
            this.topValueFormatter = topValueFormatter;
        }
        @Override
        public void renderAxisLabels(Canvas c) {
            if (!mXAxis.isEnabled() || !mXAxis.isDrawLabelsEnabled()) return;

            float yoffset = mXAxis.getYOffset();
            mAxisLabelPaint.setTypeface(mXAxis.getTypeface());
            mAxisLabelPaint.setTextSize(mXAxis.getTextSize());
            mAxisLabelPaint.setColor(mXAxis.getTextColor());

            MPPointF pointF = MPPointF.getInstance(0, 0);
            if (mXAxis.getPosition() == XAxis.XAxisPosition.TOP) {
                pointF.x = 0.5f;
                pointF.y = 1.0f;
                drawLabels(c, mViewPortHandler.contentTop() - yoffset, pointF);
            } else if (mXAxis.getPosition() == XAxis.XAxisPosition.TOP_INSIDE) {
                pointF.x = 0.5f;
                pointF.y = 1.0f;
                drawLabels(c, mViewPortHandler.contentTop() + yoffset + mXAxis.mLabelRotatedHeight, pointF);
            } else if (mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM) {
                pointF.x = 0.5f;
                pointF.y = 0.0f;
                drawLabels(c, mViewPortHandler.contentBottom() + yoffset, pointF);
            } else if (mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM_INSIDE) {
                pointF.x = 0.5f;
                pointF.y = 0.0f;
                drawLabels(c, mViewPortHandler.contentBottom() - yoffset - mXAxis.mLabelRotatedHeight, pointF);
            } else { // BOTH SIDED
                pointF.x = 0.5f;
                pointF.y = 1.0f;
                drawLabelsTop(c, mViewPortHandler.contentTop() - yoffset, pointF);
                pointF.x = 0.5f;
                pointF.y = 0.0f;
                drawLabels(c, mViewPortHandler.contentBottom() + yoffset, pointF);
            }
            MPPointF.recycleInstance(pointF);
        }

        private void drawLabelsTop(Canvas c, float pos, MPPointF anchor) {
            final float labelRotationAngleDegrees = mXAxis.getLabelRotationAngle();
            boolean centeringEnabled = mXAxis.isCenterAxisLabelsEnabled();
            float[] positions = new float[mXAxis.mEntryCount * 2];

            for (int i = 0; i < positions.length; i += 2) {
                if (centeringEnabled) positions[i] = mXAxis.mCenteredEntries[i / 2];
                else positions[i] = mXAxis.mEntries[i / 2];
            }

            mTrans.pointValuesToPixel(positions);
            for (int i = 0; i < positions.length; i += 2) {
                float x = positions[i];

                if (mViewPortHandler.isInBoundsX(x)) {
                    String label = topValueFormatter.getFormattedValue(mXAxis.mEntries[i/2], mXAxis);
                    if (mXAxis.isAvoidFirstLastClippingEnabled()) {
                        if (i == mXAxis.mEntryCount - 1 && mXAxis.mEntryCount > 1) {
                            float width = Utils.calcTextWidth(mAxisLabelPaint, label);
                            if (width > mViewPortHandler.offsetRight() * 2 && x + width > mViewPortHandler.getChartWidth()) x -= width / 2;
                        } else if (i == 0) {
                            float width = Utils.calcTextWidth(mAxisLabelPaint, label);
                            x += width / 2;
                        }
                    }
                    drawLabel(c, label, x, pos, anchor, labelRotationAngleDegrees);
                }
            }
        }
    }
}