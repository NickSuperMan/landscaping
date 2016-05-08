package com.zua.landscaping.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.zua.landscaping.R;
import com.zua.landscaping.activity.AddGroupActivity;
import com.zua.landscaping.adapter.MyFriendListAdapter;
import com.zua.landscaping.adapter.MyGroupAdapter;
import com.zua.landscaping.utils.ViewFindUtils;


/**
 * Created by roy on 16/4/21.
 */
public class GroupFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private View view;
    private TextView text_group;
    private ListView group_listView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = View.inflate(activity, R.layout.layout_message_group, null);

        initView();
        initEvent();

        return view;
    }

    private void initView() {
        text_group = ViewFindUtils.find(view, R.id.create_group_chat);
        group_listView = ViewFindUtils.find(view, R.id.group_list);


    }

    private void initEvent() {
        text_group.setOnClickListener(this);



        group_listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        intent2Activity(AddGroupActivity.class);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
