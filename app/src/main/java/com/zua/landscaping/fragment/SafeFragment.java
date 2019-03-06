package com.zua.landscaping.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zua.landscaping.R;
import com.zua.landscaping.activity.PhotoViewPagerPreview;
import com.zua.landscaping.adapter.ScenePhotoAdapter;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.Event;
import com.zua.landscaping.bean.Scene;
import com.zua.landscaping.utils.DataLoad;
import com.zua.landscaping.utils.LocalDisplay;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;


/**
 * Created by roy on 16/4/21.
 */
public class SafeFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private View view;
    private ListView listView;
    private ScenePhotoAdapter adapter;
    private List<Scene> sceneList;
    private PtrClassicFrameLayout frame;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = View.inflate(activity, R.layout.layout_scene_safe, null);
        sceneList = App.getSceneSafeList();

        initView();
        initEvent();
        return view;
    }

    private void initView() {
        listView = (ListView) view.findViewById(R.id.safe_listView);
        adapter = new ScenePhotoAdapter(getActivity(), sceneList);

    }

    private void initEvent() {
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        frame = (PtrClassicFrameLayout) view.findViewById(R.id.store_house_ptr_frame_safe);
        // header
        final StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.setPadding(0, LocalDisplay.dp2px(15), 0, 0);

        header.initWithPointList(getPointList());
        header.setTextColor(Color.alpha(R.color.accent));

        frame.setDurationToCloseHeader(3000);
        frame.setHeaderView(header);
        frame.addPtrUIHandler(header);

        frame.setPtrHandler(new PtrHandler() {
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

    }

    @Subscribe
    public void onEvent(Event event) {

        if (event.getMsg().equals("8")) {
            adapter.getDataList().clear();
            adapter.getDataList().addAll(App.getSceneSafeList());
            frame.refreshComplete();
            adapter.notifyDataSetChanged();
        }

    }

    private void updateData() {
        DataLoad.getSafeData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), PhotoViewPagerPreview.class);
        intent.putExtra("url", sceneList.get(position).getScenePicUrl());
        startActivity(intent);
    }

    private ArrayList<float[]> getPointList() {
        // this point is taken from https://github.com/cloay/CRefreshLayout
        List<Point> startPoints = new ArrayList<Point>();
        startPoints.add(new Point(240, 80));
        startPoints.add(new Point(270, 80));
        startPoints.add(new Point(265, 103));
        startPoints.add(new Point(255, 65));
        startPoints.add(new Point(275, 80));
        startPoints.add(new Point(275, 80));
        startPoints.add(new Point(302, 80));
        startPoints.add(new Point(275, 107));

        startPoints.add(new Point(320, 70));
        startPoints.add(new Point(313, 80));
        startPoints.add(new Point(330, 63));
        startPoints.add(new Point(315, 87));
        startPoints.add(new Point(330, 80));
        startPoints.add(new Point(315, 100));
        startPoints.add(new Point(330, 90));
        startPoints.add(new Point(315, 110));
        startPoints.add(new Point(345, 65));
        startPoints.add(new Point(357, 67));
        startPoints.add(new Point(363, 103));

        startPoints.add(new Point(375, 80));
        startPoints.add(new Point(375, 80));
        startPoints.add(new Point(425, 80));
        startPoints.add(new Point(380, 95));
        startPoints.add(new Point(400, 63));

        List<Point> endPoints = new ArrayList<Point>();
        endPoints.add(new Point(270, 80));
        endPoints.add(new Point(270, 110));
        endPoints.add(new Point(270, 110));
        endPoints.add(new Point(250, 110));
        endPoints.add(new Point(275, 107));
        endPoints.add(new Point(302, 80));
        endPoints.add(new Point(302, 107));
        endPoints.add(new Point(302, 107));

        endPoints.add(new Point(340, 70));
        endPoints.add(new Point(360, 80));
        endPoints.add(new Point(330, 80));
        endPoints.add(new Point(340, 87));
        endPoints.add(new Point(315, 100));
        endPoints.add(new Point(345, 98));
        endPoints.add(new Point(330, 120));
        endPoints.add(new Point(345, 108));
        endPoints.add(new Point(360, 120));
        endPoints.add(new Point(363, 75));
        endPoints.add(new Point(345, 117));

        endPoints.add(new Point(380, 95));
        endPoints.add(new Point(425, 80));
        endPoints.add(new Point(420, 95));
        endPoints.add(new Point(420, 95));
        endPoints.add(new Point(400, 120));
        ArrayList<float[]> list = new ArrayList<float[]>();

        int offsetX = Integer.MAX_VALUE;
        int offsetY = Integer.MAX_VALUE;

        for (int i = 0; i < startPoints.size(); i++) {
            offsetX = Math.min(startPoints.get(i).x, offsetX);
            offsetY = Math.min(startPoints.get(i).y, offsetY);
        }
        for (int i = 0; i < endPoints.size(); i++) {
            float[] point = new float[4];
            point[0] = startPoints.get(i).x - offsetX;
            point[1] = startPoints.get(i).y - offsetY;
            point[2] = endPoints.get(i).x - offsetX;
            point[3] = endPoints.get(i).y - offsetY;
            list.add(point);
        }
        return list;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
