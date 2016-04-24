package xzy.zzia.com.landscaping.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import xzy.zzia.com.landscaping.R;
import xzy.zzia.com.landscaping.utils.TitleBuilder;
import xzy.zzia.com.landscaping.utils.ViewFindUtils;

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

        new TitleBuilder(view).setTitleText(getString(R.string.message)).build();

        initView();

        initEvent();

        return view;
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
