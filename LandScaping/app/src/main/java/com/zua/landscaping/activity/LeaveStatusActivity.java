package com.zua.landscaping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.zua.landscaping.R;
import com.zua.landscaping.adapter.ApplyLeaveStatusAdapter;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.Leave;
import com.zua.landscaping.utils.ConnService;
import com.zua.landscaping.utils.ServiceGenerator;
import com.zua.landscaping.utils.TitleBuilder;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by roy on 16/5/4.
 */
public class LeaveStatusActivity extends Activity {

    private ListView listView;
    private ApplyLeaveStatusAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_leave_status);

        new TitleBuilder(this).setTitleText(getString(R.string.leave_status)).setLeftImage(R.drawable.back).setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).setRightText(getString(R.string.apply_leave)).setRightOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LeaveStatusActivity.this,ApplyLeaveActivity.class));
            }
        }).build();

        getData();
        initView();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listview_leave_status);
    }

    private void getData() {
        ConnService service = ServiceGenerator.createService(ConnService.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("method", "query");
        map.put("userId", App.getUser().getUserId() + "");
        Call<List<Leave>> call = service.getLeaveList(map);
        call.enqueue(new Callback<List<Leave>>() {
            @Override
            public void onResponse(Call<List<Leave>> call, Response<List<Leave>> response) {
                if (response.isSuccess()) {
                    List<Leave> leaveList = response.body();
                    initEvent(leaveList);
                }
            }

            @Override
            public void onFailure(Call<List<Leave>> call, Throwable t) {
                Log.e("roy", t.toString());
            }
        });
    }

    private void initEvent(List<Leave> list) {
        adapter = new ApplyLeaveStatusAdapter(this, list);
        listView.setAdapter(adapter);

    }

}
