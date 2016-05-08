package com.zua.landscaping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.zua.landscaping.R;
import com.zua.landscaping.adapter.DeviceAdapter;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.Device;
import com.zua.landscaping.utils.TitleBuilder;

import java.util.List;

/**
 * Created by roy on 16/5/6.
 */
public class DeviceListActivity extends Activity {

    private GridView gridView;
    private DeviceAdapter adapter;
    private List<Device> deviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_device);
        new TitleBuilder(this).setTitleText(getString(R.string.take_device)).setLeftImage(R.drawable.back).setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).setRightText(getString(R.string.add_device)).setRightOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).build();
        deviceList = App.getDeviceList();
        initView();
        initEvent();
    }

    private void initEvent() {
        adapter = new DeviceAdapter(this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DeviceListActivity.this, PhotoPreviewActivity.class);
                intent.putExtra("url", deviceList.get(position).getDevicecPicUrl());
                startActivity(intent);
            }
        });
    }

    private void initView() {

        gridView = (GridView) findViewById(R.id.gridView_device);

    }
}
