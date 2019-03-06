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
import com.zua.landscaping.app.App;
import com.zua.landscaping.utils.TitleBuilder;

import java.util.List;

import io.rong.imkit.RongIM;

/**
 * Created by roy on 4/26/16.
 */
public class AddGroupActivity extends Activity {

    private ListView listView;
    private MyGroupAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_add_group);
        App.getInstance().addActivity(this);

        initView();

        initTitle();
    }

    private void initTitle() {
        new TitleBuilder(this).setLeftImage(R.drawable.back).setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).setTitleText(getString(R.string.add_group)).setRightImage(R.drawable.icon_add).setRightOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<String> list = mAdapter.checkedList();

                final EditText text = new EditText(AddGroupActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(AddGroupActivity.this)
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
            }
        }).build();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.friend_list_add);


        mAdapter = new MyGroupAdapter(this);
        listView.setAdapter(mAdapter);
    }

}
