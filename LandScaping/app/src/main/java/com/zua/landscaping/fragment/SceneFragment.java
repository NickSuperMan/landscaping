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
import com.zua.landscaping.utils.TitleBuilder;
import com.zua.landscaping.utils.ViewFindUtils;


/**
 * Created by roy on 16/4/18.
 */
public class SceneFragment extends BaseFragment {
    private View view;
    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(activity, R.layout.layout_scene, null);

        new TitleBuilder(view).setTitleText(getString(R.string.scene)).build();
        initView();
        initEvent();

        return view;
    }

    private void initView() {
        smartTabLayout = ViewFindUtils.find(view, R.id.viewpager_scene_indicator);
        viewPager = ViewFindUtils.find(view, R.id.viewpager_scene);
    }

    private void initEvent() {

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(activity.getSupportFragmentManager(),
                FragmentPagerItems.with(getActivity())
                        .add(R.string.photo, PhotoFragment.class)
                        .add(R.string.video, VideoFragment.class)
                        .add(R.string.safe, SafeFragment.class)
                        .add(R.string.opinion, OpinionFragment.class)
                        .create());

        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
    }
}
