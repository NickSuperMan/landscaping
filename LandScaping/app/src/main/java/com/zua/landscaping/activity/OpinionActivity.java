package com.zua.landscaping.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.zua.landscaping.R;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.Code;
import com.zua.landscaping.utils.ConnService;
import com.zua.landscaping.utils.ServiceGenerator;
import com.zua.landscaping.utils.TitleBuilder;
import com.zua.landscaping.utils.ToastUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by roy on 16/5/14.
 */
public class OpinionActivity extends Activity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.actvity_layout_opinion);
        App.getInstance().addActivity(this);

        new TitleBuilder(this).setTitleText(getString(R.string.opinion)).setLeftImage(R.drawable.back).setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        }).setRightText(getString(R.string.video_publish)).setRightOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();

            }
        }).build();

        editText = (EditText) findViewById(R.id.opinion_suggest);

    }

    private void upload() {
        String sceneDescription = editText.getText().toString().trim();
        ConnService service = ServiceGenerator.createService(ConnService.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", App.getUser().getUserId() + "");
        map.put("method", "opinion");

        map.put("sceneDescription", sceneDescription);
        map.put("scenePosition", App.getPosition());
        Call<Code> call = service.uploadOpinion(map);
        call.enqueue(new Callback<Code>() {
            @Override
            public void onResponse(Call<Code> call, Response<Code> response) {
                if (response.isSuccess()) {
                    if (response.body().getCode() == 1)
                        ToastUtils.showShort(OpinionActivity.this, response.body().getMessage());
                }
                OpinionActivity.this.finish();
            }

            @Override
            public void onFailure(Call<Code> call, Throwable t) {

            }
        });
    }
}
