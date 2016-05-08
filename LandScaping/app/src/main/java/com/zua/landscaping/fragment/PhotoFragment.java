package com.zua.landscaping.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zua.landscaping.R;
import com.zua.landscaping.adapter.ScenePhotoAdapter;


/**
 * Created by roy on 16/4/21.
 */
public class PhotoFragment extends BaseFragment {

    private View view;
    private ListView listView;
    private ScenePhotoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = View.inflate(activity, R.layout.layout_scene_photo, null);

        listView = (ListView) view.findViewById(R.id.photo_listView);
        adapter = new ScenePhotoAdapter(getActivity());
        listView.setAdapter(adapter);

        return view;
    }
}
