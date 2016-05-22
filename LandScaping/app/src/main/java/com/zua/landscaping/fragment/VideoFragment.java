package com.zua.landscaping.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zua.landscaping.R;
import com.zua.landscaping.adapter.SceneVideoAdapter;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.Event;
import com.zua.landscaping.bean.Scene;
import com.zua.landscaping.utils.DataLoad;
import com.zua.landscaping.utils.LocalDisplay;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;


/**
 * Created by roy on 16/4/21.
 */
public class VideoFragment extends BaseFragment {

    private View view;
    private ListView listView;
    private SceneVideoAdapter adapter;
    private List<Scene> scenes;
    private PtrClassicFrameLayout mPtrFrameLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = View.inflate(activity, R.layout.layout_scene_video, null);


        initView();
        initEvent();


        return view;
    }

    private void initView() {
        scenes = App.getSceneVideoList();

        adapter = new SceneVideoAdapter(getActivity(), scenes);


        listView = (ListView) view.findViewById(R.id.video_listView);

        listView.setAdapter(adapter);
    }


    private void initEvent() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);
//                intent.putExtra("url", Constant.BasePath+scenes.get(position).getScenePicUrl());
//                startActivity(intent);
            }
        });


        mPtrFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_list_view_frame_video);

        // header
        final MaterialHeader header = new MaterialHeader(getContext());
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
    }

    private void updateData() {
        DataLoad.getVideoData();
    }


    @Override
    public void onResume() {
        super.onResume();
        initView();
    }


    @Subscribe
    public void onEvent(Event event) {

        if (event.getMsg().equals("3")) {
            adapter.getDataList().clear();
            adapter.getDataList().addAll(App.getSceneVideoList());
            mPtrFrameLayout.refreshComplete();
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
