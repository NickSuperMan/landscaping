package com.zua.landscaping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.zua.landscaping.R;
import com.zua.landscaping.adapter.DrawingAdapter;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.Drawing;
import com.zua.landscaping.bean.Event;
import com.zua.landscaping.utils.DataLoad;
import com.zua.landscaping.utils.LocalDisplay;
import com.zua.landscaping.utils.TitleBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Created by roy on 16/5/24.
 */
public class ProjectDrawing extends Activity implements AdapterView.OnItemClickListener {

    private GridView gridView;
    private PtrClassicFrameLayout mPtrFrameLayout;
    private DrawingAdapter adapter;
    private List<Drawing> drawingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_drawing);
        App.getInstance().addActivity(this);
        EventBus.getDefault().register(this);

        new TitleBuilder(this).setTitleText(getString(R.string.take_drawing)).setLeftImage(R.drawable.back).setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).build();

        drawingList = App.getDrawingList();
        initView();
        initEvent();
    }

    private void initView() {
        mPtrFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_frame_drawing);
        gridView = (GridView) findViewById(R.id.gridView_drawing);

    }

    private void initEvent() {

        // header
        final MaterialHeader header = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
        header.setPtrFrameLayout(mPtrFrameLayout);

        mPtrFrameLayout.setLoadingMinTime(1000);
        mPtrFrameLayout.setDurationToCloseHeader(1500);
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.addPtrUIHandler(header);

        mPtrFrameLayout.setLastUpdateTimeRelateObject(this);


        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateData();
                    }
                }, 0);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        // the following are default settings
        mPtrFrameLayout.setResistance(1.7f);
        mPtrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrameLayout.setDurationToClose(200);
        mPtrFrameLayout.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrameLayout.setPullToRefresh(false);
        // default is true
        mPtrFrameLayout.setKeepHeaderWhenRefresh(true);

        adapter = new DrawingAdapter(this, drawingList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    private void updateData() {
        DataLoad.getDrawingData();
    }

    @Subscribe
    public void onEvent(Event event) {

        if (event.getMsg().equals("9")) {
            adapter.getDataList().clear();
            adapter.getDataList().addAll(App.getDrawingList());
            mPtrFrameLayout.refreshComplete();
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, PhotoPreviewActivity.class);
        intent.putExtra("url", drawingList.get(position).getDrawPicUrl());
        startActivity(intent);
    }
}
