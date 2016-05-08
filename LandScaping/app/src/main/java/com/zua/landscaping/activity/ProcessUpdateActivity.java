package com.zua.landscaping.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.zua.landscaping.R;
import com.zua.landscaping.adapter.ProcessUpdateAdapter;
import com.zua.landscaping.bean.Update;
import com.zua.landscaping.utils.ConnService;
import com.zua.landscaping.utils.ServiceGenerator;
import com.zua.landscaping.utils.TitleBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by roy on 16/5/5.
 */
public class ProcessUpdateActivity extends Activity {

    private ListView listView;
    private ProcessUpdateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_process_update);
        getUpdateData();
        new TitleBuilder(this).setLeftImage(R.drawable.back).setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).setTitleText(getString(R.string.process_update)).build();

        initView();

    }

    private void initEvent(List<Update> updateList) {
        adapter = new ProcessUpdateAdapter(this, updateList);
        listView.setAdapter(adapter);
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView_process_update);
    }

    private void getUpdateData() {

        ConnService service = ServiceGenerator.createService(ConnService.class);
        Call<List<Update>> call = service.getUpdateData("query");
        call.enqueue(new Callback<List<Update>>() {
            @Override
            public void onResponse(Call<List<Update>> call, Response<List<Update>> response) {
                if (response.isSuccess()) {
                    List<Update> updateList = response.body();
                    initEvent(updateList);
                }
            }

            @Override
            public void onFailure(Call<List<Update>> call, Throwable t) {
                Log.e("roy", t.toString());
            }
        });
    }
}
