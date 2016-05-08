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
import com.zua.landscaping.activity.VideoPlayerActivity;
import com.zua.landscaping.adapter.SceneVideoAdapter;
import com.zua.landscaping.app.App;
import com.zua.landscaping.app.Constant;
import com.zua.landscaping.bean.Scene;

import java.util.List;


/**
 * Created by roy on 16/4/21.
 */
public class VideoFragment extends BaseFragment {

    private View view;
    private ListView listView;
    private SceneVideoAdapter adapter;
    private List<Scene> scenes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = View.inflate(activity, R.layout.layout_scene_video, null);

        scenes = App.getSceneVideoList();

        adapter = new SceneVideoAdapter(getActivity());


        listView = (ListView) view.findViewById(R.id.video_listView);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);
                intent.putExtra("url", Constant.BasePath+scenes.get(position).getScenePicUrl());
                startActivity(intent);
            }
        });


        return view;
    }

}
