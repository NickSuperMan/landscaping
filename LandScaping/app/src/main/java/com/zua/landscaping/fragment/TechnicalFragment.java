package com.zua.landscaping.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zua.landscaping.R;
import com.zua.landscaping.utils.TitleBuilder;


/**
 * Created by roy on 16/4/18.
 */
public class TechnicalFragment extends BaseFragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(activity, R.layout.layout_technical, null);
        new TitleBuilder(view).setTitleText(getString(R.string.technical)).build();

        return view;
    }
}
