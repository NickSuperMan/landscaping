package com.zua.landscaping.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.zua.landscaping.R;
import com.zua.landscaping.adapter.MeetingAdatpter;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.Event;
import com.zua.landscaping.bean.Meeting;
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
public class MeetingActivity extends Activity {

    private ListView listView;
    private PtrClassicFrameLayout mPtrFrameLayout;
    private MeetingAdatpter adapter;
    private List<Meeting> meetingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layout_meeting);
        EventBus.getDefault().register(this);

        new TitleBuilder(this).setTitleText(getString(R.string.take_meeting)).setLeftImage(R.drawable.back).setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).build();
        meetingList = App.getMeetingList();
        initView();
        initEvent();
    }

    private void initView() {
        mPtrFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_frame_meeting);
        listView = (ListView) findViewById(R.id.listview_meeting);
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

        adapter = new MeetingAdatpter(this, meetingList);
        listView.setAdapter(adapter);

    }

    private void updateData() {
        DataLoad.getMeetingData();
    }


    @Subscribe
    public void onEvent(Event event) {

        if (event.getMsg().equals("10")) {
            adapter.getDataList().clear();
            adapter.getDataList().addAll(App.getMeetingList());
            mPtrFrameLayout.refreshComplete();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
