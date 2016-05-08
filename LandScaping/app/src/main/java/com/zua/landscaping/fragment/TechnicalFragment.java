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
import com.zua.landscaping.activity.WebActivity;
import com.zua.landscaping.adapter.TechnicalAdapter;
import com.zua.landscaping.app.App;
import com.zua.landscaping.utils.TitleBuilder;


/**
 * Created by roy on 16/4/18.
 */
public class TechnicalFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private View view;
    private ListView listView;
    private TechnicalAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(activity, R.layout.layout_technical, null);
        new TitleBuilder(view).setTitleText(getString(R.string.technical)).build();

        initView();
        return view;
    }

    private void initView() {
        listView = (ListView) view.findViewById(R.id.technical_listView);
        adapter = new TechnicalAdapter(getActivity());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra("url", App.getTechnicalList().get(position).getTechnicalUrl());
        intent.putExtra("name", App.getTechnicalList().get(position).getTechnicalName());
        startActivity(intent);
    }
}
