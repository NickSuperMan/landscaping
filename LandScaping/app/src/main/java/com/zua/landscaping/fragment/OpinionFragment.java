package com.zua.landscaping.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zua.landscaping.R;
import com.zua.landscaping.adapter.OpinionAdapter;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.Scene;

import java.util.List;


/**
 * Created by roy on 16/4/21.
 */
public class OpinionFragment extends BaseFragment {

    private View view;
    private ListView listView;
    private OpinionAdapter adapter;
    private List<Scene> datas;

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
    }
}
