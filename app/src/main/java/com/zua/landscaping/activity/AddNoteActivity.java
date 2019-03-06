package com.zua.landscaping.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
 * Created by roy on 16/5/11.
 */
public class AddNoteActivity extends Activity {

    private TextView note_title;
    private TextView note_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_note_add);
        App.getInstance().addActivity(this);
        new TitleBuilder(this).setTitleText(getString(R.string.note_add)).setLeftImage(R.drawable.back).setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }
        }).build();

        initView();
    }

    private void initView() {
        note_title = (TextView) findViewById(R.id.note_add_title);
        note_content = (TextView) findViewById(R.id.note_add_content);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveNote();
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finish();
    }

    private void saveNote() {
        String title = note_title.getText().toString().trim();
        String content = note_content.getText().toString().trim();

        if (title.equals("") || null == title)
            return;

        ConnService service = ServiceGenerator.createService(ConnService.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("method", "add");
        map.put("userId", App.getUser().getUserId() + "");
        map.put("title", title);
        map.put("content", content);
        Call<Code> call = service.addNote(map);
        call.enqueue(new Callback<Code>() {
            @Override
            public void onResponse(Call<Code> call, Response<Code> response) {
                if (response.isSuccess()) {
                    if (response.body().getCode() == 1) {
                        ToastUtils.showShort(AddNoteActivity.this, response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Code> call, Throwable t) {

            }
        });

    }
}
