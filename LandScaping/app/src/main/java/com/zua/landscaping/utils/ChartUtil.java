package com.zua.landscaping.utils;

import android.graphics.Color;


import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.zua.landscaping.bean.Project;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by roy on 2016/3/9.
 */
public class ChartUtil {

    public static void showBarChart(HorizontalBarChart barChart, BarData barData) {

        barChart.setDrawBorders(false);
        barChart.setDescription("");
        barChart.setNoDataTextDescription("You need to provide data for the chart.");
        barChart.setDrawGridBackground(false);
        barChart.setTouchEnabled(false);
        barChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF);
        barChart.setDrawBarShadow(false);
        barChart.setPinchZoom(false);

        barChart.setData(barData);

        Legend mLegend = barChart.getLegend();
        mLegend.setForm(Legend.LegendForm.SQUARE);
        mLegend.setFormSize(10f);
        mLegend.setTextColor(Color.BLACK);

        XAxis xAxis = barChart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.TOP);
//        YAxis yAxis = barChart.getAxisLeft();
//        yAxis.setStartAtZero(true);
//        yAxis.setSpaceBottom(0);
//        yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);

        barChart.animateX(2500);
    }

    public static BarData getBarData(List<Project> list) {

        ArrayList<String> xValues = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            xValues.add(list.get(i).getpName());
        }

        ArrayList<BarEntry> yValues = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            int totalTime = list.get(i).getpTotalTime();
            int usedTime = list.get(i).getpUseTime();
            double value = usedTime * 100 / totalTime;
            yValues.add(new BarEntry((float) value, i));
        }

        BarDataSet barDataSet = new BarDataSet(yValues, "工程进度柱状图");
        barDataSet.setColor(Color.rgb(172, 0, 60));

        ArrayList<IBarDataSet> barDataSets = new ArrayList<>();
        barDataSets.add(barDataSet);

        BarData barData = new BarData(xValues, barDataSets);
        return barData;
    }
}
