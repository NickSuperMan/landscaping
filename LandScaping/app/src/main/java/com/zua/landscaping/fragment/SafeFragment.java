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
import com.zua.landscaping.activity.PhotoViewPagerPreview;
import com.zua.landscaping.adapter.ScenePhotoAdapter;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.Scene;

import java.util.List;


/**
 * Created by roy on 16/4/21.
 */
public class SafeFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private View view;
    private ListView listView;
    private ScenePhotoAdapter adapter;
    private List<Scene> sceneList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = View.inflate(activity, R.layout.layout_scene_safe, null);

        initView();
        return view;
    }

    private void initView() {
        sceneList = App.getSceneSafeList();
        listView = (ListView) view.findViewById(R.id.safe_listView);
        adapter = new ScenePhotoAdapter(getActivity(), sceneList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), PhotoViewPagerPreview.class);
        intent.putExtra("url", sceneList.get(position).getScenePicUrl());
        startActivity(intent);
    }
}
