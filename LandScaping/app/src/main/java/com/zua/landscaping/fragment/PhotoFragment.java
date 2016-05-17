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
import com.zua.landscaping.bean.Scene;
import com.zua.landscaping.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by roy on 16/4/21.
 */
public class PhotoFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private View view;
    private ListView listView;
    private ScenePhotoAdapter adapter;
    private List<Scene> scenePhotoList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = View.inflate(activity, R.layout.layout_scene_photo, null);

        scenePhotoList = App.getScenePhotoList();
        listView = (ListView) view.findViewById(R.id.photo_listView);
        adapter = new ScenePhotoAdapter(getActivity(),scenePhotoList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        return view;
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
}
