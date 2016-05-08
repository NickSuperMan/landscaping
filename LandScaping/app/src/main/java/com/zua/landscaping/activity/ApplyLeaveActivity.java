package com.zua.landscaping.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.zua.landscaping.R;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.Code;
import com.zua.landscaping.utils.ConnService;
import com.zua.landscaping.utils.ServiceGenerator;
import com.zua.landscaping.utils.TitleBuilder;
import com.zua.landscaping.utils.ToastUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by roy on 16/4/21.
 */
public class ApplyLeaveActivity extends Activity implements View.OnClickListener {

    private EditText apply_reason;
    private Button bt_leave_time;
    private Button bt_back_time;
    private Button bt_sumbit_apply;
    private TextView tv_leave_time;
    private TextView tv_back_time;

    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private Calendar calendar = Calendar.getInstance(Locale.CHINA);

    DatePickerDialog.OnDateSetListener leaveTime = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            tv_leave_time.setText(format.format(calendar.getTime()));
        }
    };


    DatePickerDialog.OnDateSetListener backTime = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            tv_back_time.setText(format.format(calendar.getTime()));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_apply_leave);

        new TitleBuilder(this).setTitleText(getString(R.string.apply_leave)).setLeftImage(R.drawable.back).setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).build();

        initView();
        initEvent();
    }

    private void initView() {
        apply_reason = (EditText) findViewById(R.id.apply_reason);
        bt_leave_time = (Button) findViewById(R.id.bt_leave_time);
        bt_back_time = (Button) findViewById(R.id.bt_back_time);
        bt_sumbit_apply = (Button) findViewById(R.id.sumbit_apply);
        tv_leave_time = (TextView) findViewById(R.id.leave_time);
        tv_back_time = (TextView) findViewById(R.id.back_time);
    }

    private void initEvent() {
        bt_leave_time.setOnClickListener(this);
        bt_back_time.setOnClickListener(this);
        bt_sumbit_apply.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_leave_time:
                leaveTimePicker();
                break;
            case R.id.bt_back_time:
                backTimePicker();
                break;
            case R.id.sumbit_apply:
                uploadLeaveApply();
                break;
        }
    }

    private void uploadLeaveApply() {
        String reason = apply_reason.getText().toString().trim();
        String leaveTime = tv_leave_time.getText().toString().trim();
        String backTime = tv_back_time.getText().toString().trim();

        ConnService service = ServiceGenerator.createService(ConnService.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", App.getUser().getUserId() + "");
        map.put("reason", reason);
        map.put("leaveTime", leaveTime);
        map.put("backTime", backTime);
        map.put("method", "apply");
        Call<Code> call = service.applyLeave(map);
        call.enqueue(new Callback<Code>() {
            @Override
            public void onResponse(Call<Code> call, Response<Code> response) {
                if (response.isSuccess()) {
                    Code code = response.body();
                    if (code.getCode() == 1) {
                        ToastUtils.showShort(ApplyLeaveActivity.this, code.getMessage());
                        clearView();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<Code> call, Throwable t) {

            }
        });
    }

    private void clearView() {
        apply_reason.setText("");
        tv_leave_time.setText("");
        tv_back_time.setText("");
    }

    private void backTimePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, backTime, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void leaveTimePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, leaveTime, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

}
