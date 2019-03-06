package com.zua.landscaping.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zua.landscaping.R;
import com.zua.landscaping.activity.ConversationActivity;
import com.zua.landscaping.adapter.MyFriendListAdapter;
import com.zua.landscaping.app.App;
import com.zua.landscaping.utils.ToastUtils;
import com.zua.landscaping.utils.ViewFindUtils;

import io.rong.imkit.RongIM;


/**
 * Created by roy on 16/4/21.
 */
public class FriendFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private View view;
    private ListView friendList;
    private MyFriendListAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = View.inflate(activity, R.layout.layout_message_friend, null);

        initView();


        return view;
    }

    private void initView() {
        friendList = ViewFindUtils.find(view, R.id.friend_list);
        adapter = new MyFriendListAdapter(getActivity());
        friendList.setAdapter(adapter);

        friendList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        ToastUtils.showShort(getActivity(), App.getUser().getFriends().get(position).getUserId() + "");
        Log.e("roy", App.getUser().getFriends().get(position).getUserId() + "");

        if (RongIM.getInstance() != null) {

            RongIM.getInstance().startPrivateChat(getActivity(), App.getUser().getFriends().get(position).getUserId() + "", "title");
        }

    }
}
