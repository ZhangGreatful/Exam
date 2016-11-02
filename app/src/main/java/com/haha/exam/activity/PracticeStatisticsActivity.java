package com.haha.exam.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.google.gson.Gson;
import com.haha.exam.R;
import com.haha.exam.bean.Statistics;
import com.haha.exam.web.WebInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 练习统计界面
 */
public class PracticeStatisticsActivity extends BaseActivity {
    private PieChart mChart;
    private TextView undo,right,wrong;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("练习统计");
        initView();
        initData();
    }

    private void initData() {
        OkGo.post(WebInterface.statistics)
                .tag(this)
                .params("telphone", "18266142739")
                .params("subject","1")
                .params("cartype","xc")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Statistics statistics = gson.fromJson(s, Statistics.class);
                        int count=Integer.valueOf(statistics.getMsg().get(0).getAllcount());
                        right.setText(statistics.getMsg().get(0).getRightcount()+"道");
                        wrong.setText(statistics.getMsg().get(0).getErrorcount()+"道");
                        PieData mPieData = getPieData(3, count,statistics);
                        showChart(mChart, mPieData);
                    }
                });
    }

    private void initView() {
        mChart = (PieChart) findViewById(R.id.spread_pie_chart);
        undo= (TextView) findViewById(R.id.undo);
        right= (TextView) findViewById(R.id.right);
        wrong= (TextView) findViewById(R.id.wrong);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_practice_statistics;
    }

    private void showChart(PieChart pieChart, PieData pieData) {
//        pieChart.setHoleColorTransparent(true);

//        pieChart.setHoleRadius(60f);  //半径
//        pieChart.setTransparentCircleRadius(64f); // 半透明圈
        pieChart.setHoleRadius(0); //实心圆
        pieChart.isDrawHoleEnabled();

        pieChart.setDescription("练习统计");
        pieChart.setDescriptionTextSize(20);

        // mChart.setDrawYValues(true);
        pieChart.setDrawCenterText(false);  //饼状图中间可以添加文字

        pieChart.setDrawHoleEnabled(false);

        pieChart.setRotationAngle(0); // 初始旋转角度

        // draws the corresponding description value into the slice
        // mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(false); // 可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(true);  //显示成百分比
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
//      mChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

//      mChart.setOnAnimationListener(this);

//        pieChart.setCenterText("Quarterly Revenue");  //饼状图中间的文字

        //设置数据
        pieChart.setData(pieData);

        // undo all highlights
//      pieChart.highlightValues(null);
//      pieChart.invalidate();

//        Legend mLegend = pieChart.getLegend();  //设置比例图
//        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);  //最右边显示
//      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形
//        mLegend.setXEntrySpace(7f);
//        mLegend.setYEntrySpace(5f);

        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);
    }

    /**
     * @param count 分成几部分
     * @param range
     */
    private PieData getPieData(int count, float range,Statistics statistics) {

        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容
//
        for (int i = 0; i < count; i++) {
            xValues.add("");  //饼块上显示成Quarterly1, Quarterly2, Quarterly3, Quarterly4
        }

        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据

        // 饼图数据
        /**
         * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38
         * 所以 14代表的百分比就是14%
         */

        int quarterly2 = Integer.valueOf(statistics.getMsg().get(0).getErrorcount());
        int quarterly3 = Integer.valueOf(statistics.getMsg().get(0).getRightcount());
        int quarterly1 = Integer.valueOf(statistics.getMsg().get(0).getAllcount())-quarterly2-quarterly3;
        undo.setText(String.valueOf(quarterly1)+"道");

        yValues.add(new Entry(quarterly1, 0));
        yValues.add(new Entry(quarterly2, 1));
        yValues.add(new Entry(quarterly3, 2));

        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, ""/*显示在比例图上*/);
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离

        ArrayList<Integer> colors = new ArrayList<Integer>();

        // 饼图颜色
        colors.add(Color.rgb(255, 165, 0));//橙色
        colors.add(Color.rgb(255, 84, 84));//红色
        colors.add(Color.rgb(118, 212, 55));//绿色

        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度
        pieDataSet.setHighlightEnabled(true); //: 设置是否显示y轴的值的数据
        PieData pieData = new PieData(xValues, pieDataSet);

        return pieData;
    }
}
