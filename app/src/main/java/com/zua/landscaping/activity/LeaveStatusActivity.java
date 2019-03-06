package com.zua.landscaping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.zua.landscaping.R;
import com.zua.landscaping.adapter.ApplyLeaveStatusAdapter;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.Code;
import com.zua.landscaping.bean.Leave;
import com.zua.landscaping.utils.ConnService;
import com.zua.landscaping.utils.LocalDisplay;
import com.zua.landscaping.utils.ServiceGenerator;
import com.zua.landscaping.utils.TitleBuilder;

import java.util.HashMap;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by roy on 16/5/4.
 */
public class LeaveStatusActivity extends Activity implements AdapterView.OnItemLongClickListener {

    private ListView listView;
    private ApplyLeaveStatusAdapter adapter;
    private PtrClassicFrameLayout mPtrFrameLayout;
    private MaterialDialog dialog;
    List<Leave> leaveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_leave_status);
        App.getInstance().addActivity(this);

        new TitleBuilder(this).setTitleText(getString(R.string.leave_status)).setLeftImage(R.drawable.back).setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).setRightText(getString(R.string.apply_leave)).setRightOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LeaveStatusActivity.this, ApplyLeaveActivity.class));
            }
        }).build();

        getData();
        initView();
        initPull();
    }

    private void initPull() {
        // header
        final MaterialHeader header = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
        header.setPtrFrameLayout(mPtrFrameLayout);

        mPtrFrameLayout.setLoadingMinTime(1000);
        mPtrFrameLayout.setDurationToCloseHeader(1500);
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.addPtrUIHandler(header);

        mPtrFrameLayout.setLastUpdateTimeRelateObject(this);


        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData();
                        frame.refreshComplete();
                    }
                }, 0);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        // the following are default settings
        mPtrFrameLayout.setResistance(1.7f);
        mPtrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrameLayout.setDurationToClose(200);
        mPtrFrameLayout.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrameLayout.setPullToRefresh(false);
        // default is true
        mPtrFrameLayout.setKeepHeaderWhenRefresh(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void initView() {
        dialog = new MaterialDialog(this);
        listView = (ListView) findViewById(R.id.listview_leave_status);
        mPtrFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.store_house_ptr_frame_leave);
        listView.setOnItemLongClickListener(this);
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
                    leaveList = response.body();
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

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        if (leaveList.get(position).getLeaveStatus() == 1) {
            dialog.isTitleShow(false)
                    .content("是否已经回来？")
                    .contentTextSize(18)
                    .btnText("取消", "确定")
                    .showAnim(new BounceTopEnter())
                    .dismissAnim(new SlideBottomExit())
                    .show();

            dialog.setOnBtnClickL(new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    dialog.dismiss();
                }
            }, new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    dialog.superDismiss();
                    comeBack(position, leaveList.get(position).getLeaveId());
                }
            });
        }

        return false;
    }

    private void comeBack(final int position, final int leaveId) {
        ConnService service = ServiceGenerator.createService(ConnService.class);
        Call<Code> call = service.comeBack("come", leaveId + "");
        call.enqueue(new Callback<Code>() {
            @Override
            public void onResponse(Call<Code> call, Response<Code> response) {
                if (response.isSuccess()) {
                    if (response.body().getCode() == 1) {
                        Log.e("roy", "~~~~~~~");
                        leaveList.get(position).setLeaveStatus(3);
                        initEvent(leaveList);
                    }
                }
            }

            @Override
            public void onFailure(Call<Code> call, Throwable t) {

            }
        });
    }
}
