package com.zua.landscaping.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zua.landscaping.R;
import com.zua.landscaping.bean.Code;
import com.zua.landscaping.bean.Note;
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
public class ModifyNoteActivity extends Activity {

    private TextView note_title;
    private TextView note_content;
    private Note note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_note_add);

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

        note = (Note) getIntent().getSerializableExtra("item");
        initView();
    }

    private void initView() {
        note_title = (TextView) findViewById(R.id.note_add_title);
        note_content = (TextView) findViewById(R.id.note_add_content);
        note_title.setText(note.getNoteTitle());
        note_content.setText(note.getNoteContet());
    }


    private void saveNote() {
        String title = note_title.getText().toString().trim();
        String content = note_content.getText().toString().trim();

        if (title.equals("") || null == title)
            return;
        ConnService service = ServiceGenerator.createService(ConnService.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("method", "update");
        map.put("noteId", note.getNoteId() + "");
        map.put("title", title);
        map.put("content", content);
        Call<Code> call = service.updateNote(map);
        call.enqueue(new Callback<Code>() {
            @Override
            public void onResponse(Call<Code> call, Response<Code> response) {
                if (response.isSuccess()) {
                    if (response.body().getCode() == 1) {
                        ToastUtils.showShort(ModifyNoteActivity.this, response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Code> call, Throwable t) {

            }
        });
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
}
