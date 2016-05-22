package com.zua.landscaping.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zua.landscaping.R;
import com.zua.landscaping.activity.PhotoPreviewActivity;
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
import in.srain.cube.views.ptr.header.MaterialHeader;


/**
 * Created by roy on 16/4/21.
 */
public class PhotoFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private View view;
    private ListView listView;
    private ScenePhotoAdapter adapter;
    private List<Scene> scenePhotoList = new ArrayList<>();
    private PtrClassicFrameLayout mPtrFrameLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = View.inflate(activity, R.layout.layout_scene_photo, null);

        scenePhotoList = App.getScenePhotoList();

        initView();
        initEvent();

        return view;
    }

    private void initView() {
        mPtrFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_list_view_frame_photo);
        listView = (ListView) view.findViewById(R.id.photo_listView);

    }

    private void initEvent() {
        adapter = new ScenePhotoAdapter(getActivity(), scenePhotoList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

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
        mPtrFrameLayout.setPinContent(true);

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
        DataLoad.getPhotoData();
    }

    @Subscribe
    public void onEvent(Event event) {

        if (event.getMsg().equals("4")) {
            adapter.getDataList().clear();
            adapter.getDataList().addAll(App.getScenePhotoList());
            mPtrFrameLayout.refreshComplete();
            adapter.notifyDataSetChanged();
        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String path = scenePhotoList.get(position).getScenePicUrl();
        String ext = path.substring(path.lastIndexOf("."));
        String realPath = path.substring(0, path.lastIndexOf("_")) + ext;
        Intent intent = new Intent(getActivity(), PhotoPreviewActivity.class);
        intent.putExtra("url", realPath);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
