package com.zua.landscaping.activity;

import android.app.Activity;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.zua.landscaping.R;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.Project;
import com.zua.landscaping.utils.ChartUtil;
import com.zua.landscaping.utils.ConnService;
import com.zua.landscaping.utils.ServiceGenerator;
import com.zua.landscaping.utils.TitleBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 123 on 2016/3/7.
 */
public class ProjectActivity extends Activity {

    private HorizontalBarChart chart;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_project);

        new TitleBuilder(this).setTitleText(getString(R.string.check_process)).setLeftImage(R.drawable.back).setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).build();

        initView();
        initEvent();

    }

    private void initEvent() {

        List<Project> list = App.getProjectList();
        BarData barData = ChartUtil.getBarData(list);
        ChartUtil.showBarChart(chart, barData);


//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String fileName = Environment.getExternalStorageDirectory() + File.separator + "/shortDemo";
//                Toast.makeText(ProjectActivity.this,"click",Toast.LENGTH_SHORT).show();
//                ExcelUtils.writeExcel(fileName,list);
//            }
//        });
    }

    private void initView() {
        chart = (HorizontalBarChart) findViewById(R.id.chart);
        button = (Button) findViewById(R.id.create_excel);
    }


}
