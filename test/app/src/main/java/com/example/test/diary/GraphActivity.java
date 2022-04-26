package com.example.test.diary;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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

        lineChart.setNoDataText("2건 이상의 기록이 존재하지 않아 그래프를 그릴 수 없습니다.");
        lineChart.setNoDataTextColor(Color.parseColor("#FFA200"));
        Paint paint = lineChart.getPaint(Chart.PAINT_INFO);
        paint.setTextSize(40);

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
        if(list_heat.size() != 0){
            if(list_heat.size() == 1){

            } else {
                for (int i = 0; i < list_heat.size(); i++) {
                    date.add(list_heat.get(i).getStart_time() + "," + list_heat.get(i).getDiary_date().split("-")[1] + "-" + list_heat.get(i).getDiary_date().split("-")[2]);
                    values.add(new Entry(i, (float) list_heat.get(i).getTemperature()));
                }
                makeChart(values, date, "heat");
            }
        }

        tab_graph.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){ //체온
                    lineChart.clear();
                    date.clear();
                    values.clear();
                    if(list_heat.size() != 0){
                        if(list_heat.size() == 1){

                        } else {
                            for (int i = 0; i < list_heat.size(); i++) {
                                date.add(list_heat.get(i).getStart_time() + "," + list_heat.get(i).getDiary_date().split("-")[1] + "-" + list_heat.get(i).getDiary_date().split("-")[2]);
                                //date.add(list_heat.get(i).getStart_time());
                                values.add(new Entry(i, (float) list_heat.get(i).getTemperature()));
                            }
                            makeChart(values, date, "heat");
                        }
                    }
                } else if(tab.getPosition() == 1){ //키
                    lineChart.clear();
                    date.clear();
                    values.clear();
                    if(list_body.size() != 0){
                        if(list_body.size() == 1){
                        } else {
                            for (int i = 0; i < list_body.size(); i++) {
                                date.add(list_body.get(i).getStor_date().split("-")[1] + "-" + list_body.get(i).getStor_date().split("-")[2]);
                                values.add(new Entry(i, (float) list_body.get(i).getStor_cm()));
                            }
                            makeChart(values, date, "cm");
                        }
                    }
                } else if(tab.getPosition() == 2){ //몸무게
                    lineChart.clear();
                    date.clear();
                    values.clear();
                    if(list_body.size() != 0){
                        if(list_body.size() == 1){
                        } else {
                            for (int i = 0; i < list_body.size(); i++) {
                                date.add(list_body.get(i).getStor_date().split("-")[1] + "-" + list_body.get(i).getStor_date().split("-")[2]);
                                values.add(new Entry(i, (float) list_body.get(i).getStor_kg()));
                            }
                            makeChart(values, date, "kg");
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
    public void makeChart(ArrayList<Entry> values, ArrayList<String> date, String category){
        LineDataSet lineDataSet = new LineDataSet(values, "라벨");
        lineDataSet.setLineWidth(3);
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

        if(category.equals("heat")){
            lineDataSet.setCircleColor(Color.parseColor("#4dFF6B6B"));
            lineDataSet.setCircleHoleColor(Color.parseColor("#FF6B6B"));
            lineDataSet.setColor(Color.parseColor("#99FF6B6B"));

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
                    return diary_date[(int)value];
                }
            }));

            //bottom x axis 설정
            xAxis.setValueFormatter(new IndexAxisValueFormatter(time_date));
        }else{
            if(category.equals("cm")){
                lineDataSet.setCircleColor(Color.parseColor("#4d6BCB77"));
                lineDataSet.setCircleHoleColor(Color.parseColor("#6BCB77"));
                lineDataSet.setColor(Color.parseColor("#b36BCB77"));
            }
            else if(category.equals("kg")){
                lineDataSet.setCircleColor(Color.parseColor("#4d4D96FF"));
                lineDataSet.setCircleHoleColor(Color.parseColor("#4D96FF"));
                lineDataSet.setColor(Color.parseColor("#994D96FF"));
            }
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(date)); //x축 표현을 string으로 포맷
        }

        xAxis.setTextColor(Color.BLACK);
        xAxis.mAxisMinimum = 0f;
        xAxis.setGranularityEnabled(true); //이거 없으면 x축 데이터 반복되고 포인트랑 안맞음!
        xAxis.setLabelCount(values.size());
        xAxis.enableGridDashedLine(8, 24, 0); //수직 격자선
        xAxis.mEntryCount = values.size();
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setTextSize(13);

        YAxis yLAxis = lineChart.getAxisLeft(); //y축 설정
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
        lineChart.invalidate(); //그래프 그리기
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