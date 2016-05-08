package com.zua.landscaping.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zua.landscaping.R;
import com.zua.landscaping.adapter.MyGroupAdapter;

import java.util.List;

import io.rong.imkit.RongIM;

/**
 * Created by roy on 4/26/16.
 */
public class AddGroupActivity extends Activity implements View.OnClickListener {

    private ListView listView;
    private TextView back, select;
    private MyGroupAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_add_group);

        initView();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.friend_list_add);
        back = (TextView) findViewById(R.id.back);
        select = (TextView) findViewById(R.id.select);

        back.setOnClickListener(this);
        select.setOnClickListener(this);

        mAdapter = new MyGroupAdapter(this);
        listView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.select:
                final List<String> list = mAdapter.checkedList();
                Toast.makeText(this, list.toString(), Toast.LENGTH_SHORT).show();
                final EditText text = new EditText(this);
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("请输入讨论组名称：")
                        .setView(text)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = text.getText().toString();
                                RongIM.getInstance().createDiscussionChat(AddGroupActivity.this, list, name);
                                dialog.dismiss();
                                finish();
                            }
                        });
                builder.show();
                break;
        }
    }
}
