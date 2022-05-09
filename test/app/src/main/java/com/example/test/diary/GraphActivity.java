package com.example.test.diary;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.test.MainActivity;
import com.example.test.R;
import com.example.test.common.AskTask;
import com.example.test.common.CommonMethod;
import com.example.test.common.CommonVal;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity{
    TabLayout tab_graph;
    ImageView back_graph;
    LinearLayout empty_data;
    Button add_stor;
    ImageView gender;
    Gson gson = new Gson();

    private LineChart lineChart;
    ArrayList<Entry> values = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();

    ArrayList<Float> weight = new ArrayList<>();
    ArrayList<Float> height = new ArrayList<>();

    float cnt_weight = 0f, cnt_height = 0f;
    LinearLayout chart_ln ;
    LinearLayout.LayoutParams param;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        chart_ln = findViewById(R.id.chart_ln);
        lineChart = new LineChart(this);
        param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT,1);
        lineChart.setLayoutParams(param);
        chart_ln.addView(lineChart);
        tab_graph = findViewById(R.id.tab_graph);
        back_graph = findViewById(R.id.back_graph);
        empty_data = findViewById(R.id.empty_data);
        add_stor = findViewById(R.id.add_stor);
        gender = findViewById(R.id.gender);

        set_avg();

        if(CommonVal.curbaby.getBaby_gender().equals("여아")){
            gender.setImageResource(R.drawable.tmdwn_girl);
        } else {
            gender.setImageResource(R.drawable.tmdwn_boy);
        }

        add_stor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

        String baby_age_str = CommonVal.curbaby.getBaby_birth();
        String[] baby_age_arr = baby_age_str.substring(0,baby_age_str.indexOf(" ")).split("-");
        LocalDate theDate = LocalDate.of(Integer.parseInt(baby_age_arr[0]),Integer.parseInt(baby_age_arr[1]),Integer.parseInt(baby_age_arr[2]));
        String predate = Long.toString(theDate.until(LocalDate.now(), ChronoUnit.DAYS));

        for(int i=0 ;i<weight.size(); i++){
            if(Math.floorDiv(Integer.parseInt(predate), 30) == i){
                cnt_height = height.get(i);
                cnt_weight = weight.get(i);
            }
        }

        lineChart.clear();
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
        date.clear();
        values.clear();
        if(list_heat.size() != 0){
            if(list_heat.size() == 1){
                chart_ln.setVisibility(View.GONE);
                empty_data.setVisibility(View.VISIBLE);
            } else{
                chart_ln.setVisibility(View.VISIBLE);
                empty_data.setVisibility(View.GONE);
                for (int i = 0; i < list_heat.size(); i++) {
                    date.add(list_heat.get(i).getStart_time() + "," + list_heat.get(i).getDiary_date().split("-")[1] + "-" + list_heat.get(i).getDiary_date().split("-")[2]);
                    values.add(new Entry(i, (float) list_heat.get(i).getTemperature()));
                }
                makeChart(values, date, cnt_height, cnt_weight, predate, "heat");
            }
        } else{
            chart_ln.setVisibility(View.GONE);
            empty_data.setVisibility(View.VISIBLE);
        }


        tab_graph.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                chart_ln.removeAllViews();
                lineChart = new LineChart(GraphActivity.this);
                lineChart.setLayoutParams(param);
                chart_ln.addView(lineChart);
                if(tab.getPosition() == 0){ //체온
                    date.clear();
                    values.clear();
                    if(list_heat.size() != 0){
                        if(list_heat.size() == 1){
                            chart_ln.setVisibility(View.GONE);
                            empty_data.setVisibility(View.VISIBLE);
                        } else{
                            chart_ln.setVisibility(View.VISIBLE);
                            empty_data.setVisibility(View.GONE);
                            for (int i = 0; i < list_heat.size(); i++) {
                                date.add(list_heat.get(i).getStart_time() + "," + list_heat.get(i).getDiary_date().split("-")[1] + "-" + list_heat.get(i).getDiary_date().split("-")[2]);
                                values.add(new Entry(i, (float) list_heat.get(i).getTemperature()));
                            }
                            makeChart(values, date, cnt_height, cnt_weight, predate, "heat");
                        }
                    } else{
                        chart_ln.setVisibility(View.GONE);
                        empty_data.setVisibility(View.VISIBLE);
                    }
                } else if(tab.getPosition() == 1){ //키

                    date.clear();
                    values.clear();
                    if(list_body.size() != 0){
                        chart_ln.setVisibility(View.VISIBLE);
                        empty_data.setVisibility(View.GONE);
                        for (int i = 0; i < list_body.size(); i++) {
                            date.add(list_body.get(i).getStor_date().split("-")[1] + "-" + list_body.get(i).getStor_date().split("-")[2]);
                            values.add(new Entry(i, (float) list_body.get(i).getStor_cm()));
                        }
                        makeChart(values, date, cnt_height, cnt_weight, predate, "cm");
                    } else{
                        chart_ln.setVisibility(View.GONE);
                        empty_data.setVisibility(View.VISIBLE);
                    }
                } else if(tab.getPosition() == 2){ //몸무게

                    date.clear();
                    values.clear();
                    if(list_body.size() != 0){
                        chart_ln.setVisibility(View.VISIBLE);
                        empty_data.setVisibility(View.GONE);
                        for (int i = 0; i < list_body.size(); i++){
                            date.add(list_body.get(i).getStor_date().split("-")[1] + "-" + list_body.get(i).getStor_date().split("-")[2]);
                            values.add(new Entry(i, (float)list_body.get(i).getStor_kg()));
                        }
                        makeChart(values, date, cnt_height, cnt_weight, predate, "kg");
                    } else{
                        chart_ln.setVisibility(View.GONE);
                        empty_data.setVisibility(View.VISIBLE);
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
    public void makeChart(ArrayList<Entry> values, ArrayList<String> date, float height, float weight, String predate, String category){
        lineChart.invalidate();

        LineDataSet lineDataSet = new LineDataSet(values, "내아이");

        lineDataSet.setLineWidth(5);
        lineDataSet.setCircleRadius(8);
        lineDataSet.setCircleColor(Color.parseColor("#4da442ff"));
        lineDataSet.setCircleHoleColor(Color.parseColor("#a442ff"));
        lineDataSet.setColor(Color.parseColor("#99a442ff"));
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawValues(false);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis(); //x축 설정
        YAxis yLAxis = lineChart.getAxisLeft(); //y축 설정

        yLAxis.removeAllLimitLines();

        if(category.equals("heat")){
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

            LimitLine min_limit = new LimitLine(36.4f, "36.4");
            min_limit.setLineWidth(4f);
            min_limit.enableDashedLine(10f, 10f, 10f);
            min_limit.setLineColor(Color.parseColor("#4f81ff"));

            LimitLine max_limit = new LimitLine(38.0f, "38.0");
            max_limit.setLineWidth(4f);
            max_limit.enableDashedLine(10f, 10f, 10f);
            max_limit.setLineColor(Color.parseColor("#ff5e5e"));

            yLAxis.setDrawLimitLinesBehindData(true);
            yLAxis.addLimitLine(min_limit);
            yLAxis.addLimitLine(max_limit);

            yLAxis.setAxisMinimum(34);
            yLAxis.setAxisMaximum(40);
        }else{
            if(category.equals("cm")){
                LimitLine hg_limit = new LimitLine(height, predate+"일 기준 평균 " + height + "cm");
                hg_limit.setLineWidth(4f);
                hg_limit.enableDashedLine(10f, 10f, 10f);
                hg_limit.setLineColor(Color.parseColor("#1c5ffc"));

                yLAxis.setDrawLimitLinesBehindData(true);
                yLAxis.addLimitLine(hg_limit);

                yLAxis.setAxisMinimum(0);
                yLAxis.setAxisMaximum(180);
            } else if(category.equals("kg")){
                LimitLine wg_limit = new LimitLine(weight, predate + "일 기준 평균 " + weight + "kg");
                wg_limit.setLineWidth(4f);
                wg_limit.enableDashedLine(10f, 10f, 10f);
                wg_limit.setLineColor(Color.parseColor("#1cbdfc"));

                yLAxis.setDrawLimitLinesBehindData(true);
                yLAxis.addLimitLine(wg_limit);

                yLAxis.setAxisMinimum(0);
                yLAxis.setAxisMaximum(100);
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

        yLAxis.setTextColor(Color.BLACK);
        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

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
        lineChart.setVisibleXRangeMaximum(5); //드래그
        lineChart.setVisibleXRangeMinimum(2);
        lineChart.animateY(2000, Easing.EaseInBounce);
        lineChart.getLegend().setEnabled(false); //속성표시
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
                    String label = topValueFormatter.getFormattedValue(mXAxis.mEntries[i / 2], mXAxis);
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

    public void set_avg(){
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
    }
}