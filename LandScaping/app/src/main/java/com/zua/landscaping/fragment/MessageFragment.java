package com.zua.landscaping.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.zua.landscaping.R;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.Code;
import com.zua.landscaping.utils.ConnService;
import com.zua.landscaping.utils.ServiceGenerator;
import com.zua.landscaping.utils.TitleBuilder;
import com.zua.landscaping.utils.ViewFindUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by roy on 16/4/18.
 */
public class MessageFragment extends BaseFragment {

    private View view;
    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = View.inflate(activity, R.layout.layout_message, null);

        new TitleBuilder(view).setTitleText(getString(R.string.message)).setRightImage(R.drawable.icon_add).setRightOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriend();
            }
        }).build();

        initView();

        initEvent();

        return view;
    }

    private void addFriend() {
        ConnService service = ServiceGenerator.createService(ConnService.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", App.getUser().getUserId() + "");
        map.put("friendId", 3 + "");
        Call<Code> call = service.addFriend(map);
        call.enqueue(new Callback<Code>() {
            @Override
            public void onResponse(Call<Code> call, Response<Code> response) {

            }

            @Override
            public void onFailure(Call<Code> call, Throwable t) {

            }
        });
    }

    private void initView() {
        smartTabLayout = ViewFindUtils.find(view, R.id.viewpager_message_indicator);
        viewPager = ViewFindUtils.find(view, R.id.viewpager_message);
    }

    private void initEvent() {


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(activity.getSupportFragmentManager(),
                FragmentPagerItems.with(getActivity())
                        .add(R.string.chat, ConversationFragment.class)
                        .add(R.string.friend, FriendFragment.class)
                        .add(R.string.group, GroupFragment.class)
                        .create());

        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
    }


}
