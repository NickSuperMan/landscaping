package com.zua.landscaping.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zua.landscaping.R;
import com.zua.landscaping.adapter.NoteAdapter;
import com.zua.landscaping.utils.DividerItemDecoration;
import com.zua.landscaping.utils.TitleBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roy on 16/4/21.
 */
public class NoteActivity extends Activity {

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_note);
        new TitleBuilder(this).setTitleText(getString(R.string.note)).setLeftImage(R.drawable.back).setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).build();

        initData();
        initView();

    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.note_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(this, mDatas);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    protected void initData() {
        mDatas = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }
}
