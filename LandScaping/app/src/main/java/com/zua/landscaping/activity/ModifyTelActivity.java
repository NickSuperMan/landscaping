package com.zua.landscaping.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.zua.landscaping.R;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.Code;
import com.zua.landscaping.bean.User;
import com.zua.landscaping.utils.ConnService;
import com.zua.landscaping.utils.ServiceGenerator;
import com.zua.landscaping.utils.TitleBuilder;
import com.zua.landscaping.utils.ToastUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by roy on 16/5/13.
 */
public class ModifyTelActivity extends Activity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_personal_sign);

        new TitleBuilder(this).setTitleText(getString(R.string.modify_tel)).setLeftImage(R.drawable.back).setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).setRightText(getString(R.string.ok)).setRightOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString().trim();
                App.getUser().setUserTel(s);
                modifySign();
                finish();
            }
        }).build();

        editText = (EditText) findViewById(R.id.modify_sign);
        editText.setText(App.getUser().getUserTel());
    }

    private void modifySign() {
        User user = App.getUser();
        String userSign = editText.getText().toString().trim();
        ConnService service = ServiceGenerator.createService(ConnService.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("method", "sign");
        map.put("userId", user.getUserId() + "");
        map.put("userSign", userSign);
        map.put("userAge",user.getUserAge());
        map.put("userPic",user.getUserPicUrl());
        map.put("userTel",user.getUserTel());

        Call<Code> call = service.updateUser(map);
        call.enqueue(new Callback<Code>() {
            @Override
            public void onResponse(Call<Code> call, Response<Code> response) {
                if (response.isSuccess()) {
                    Code body = response.body();
                    if (body.getCode() == 1) {
                        ToastUtils.showShort(ModifyTelActivity.this, body.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Code> call, Throwable t) {

            }
        });
    }
}
