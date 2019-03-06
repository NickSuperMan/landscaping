package com.zua.landscaping.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zua.landscaping.R;
import com.zua.landscaping.adapter.OpinionAdapter;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.Event;
import com.zua.landscaping.bean.Scene;
import com.zua.landscaping.utils.DataLoad;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


/**
 * Created by roy on 16/4/21.
 */
public class OpinionFragment extends BaseFragment {

    private View view;
    private ListView listView;
    private OpinionAdapter adapter;
    private List<Scene> datas;
    private PtrClassicFrameLayout mPtrFrame;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("roy", "register");
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = View.inflate(activity, R.layout.layout_scene_opinion, null);

        initView();

        return view;
    }


    private void initView() {
        datas = App.getSceneOpinionList();
        listView = (ListView) view.findViewById(R.id.opinion_listview);
        adapter = new OpinionAdapter(getActivity(), datas);
        listView.setAdapter(adapter);

        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_list_view_frame);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
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
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
    }

    @Subscribe
    public void onEvent(Event event) {

        Log.e("roy", "opinion~~~~~~~~~~~~~~~~~~~~~");

        if (event.getMsg().equals("7")) {
            adapter.getDataList().clear();
            adapter.getDataList().addAll(App.getSceneOpinionList());
            mPtrFrame.refreshComplete();
            adapter.notifyDataSetChanged();
        }

    }

    private void updateData() {

        Log.e("roy", "!!!!!!!!!!!!!!!!!!!!!");
        DataLoad.getOpinionData();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("roy", "unregister");
        EventBus.getDefault().unregister(this);
    }
}
