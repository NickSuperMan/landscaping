package com.zua.landscaping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.FadeEnter.FadeEnter;
import com.flyco.animation.FadeExit.FadeExit;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.zua.landscaping.R;
import com.zua.landscaping.adapter.NoteListViewAdapter;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.Code;
import com.zua.landscaping.bean.Note;
import com.zua.landscaping.utils.ConnService;
import com.zua.landscaping.utils.ServiceGenerator;
import com.zua.landscaping.utils.TitleBuilder;
import com.zua.landscaping.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by roy on 16/4/21.
 */
public class NoteActivity extends Activity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView recyclerView;
    private NoteListViewAdapter adapter;
    private List<Note> noteList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_note);


        if (App.getNoteList() == null) {
            getNoteDatas();
        } else {
            noteList = App.getNoteList();
            initView();
        }


        new TitleBuilder(this).setTitleText(getString(R.string.note)).setLeftImage(R.drawable.back).setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).build();

    }

    @Override
    protected void onResume() {
        super.onResume();

        getNoteDatas();

    }

    private void initView() {

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(NoteActivity.this, AddNoteActivity.class));
            }
        });

        recyclerView = (ListView) findViewById(R.id.note_recycleView);
        adapter = new NoteListViewAdapter(this, noteList);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnItemClickListener(this);
        recyclerView.setOnItemLongClickListener(this);
    }

    private void getNoteDatas() {

        noteList = new ArrayList<>();

        ConnService service = ServiceGenerator.createService(ConnService.class);
        Call<List<Note>> call = service.getAllNotes(App.getUser().getUserId() + "", "query");
        call.enqueue(new Callback<List<Note>>() {
            @Override
            public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                if (response.isSuccess()) {
                    noteList = response.body();
                    App.setNoteList(noteList);
                    initView();
                }
            }

            @Override
            public void onFailure(Call<List<Note>> call, Throwable t) {
                Log.e("roy", "noteList" + t.toString());
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ModifyNoteActivity.class);
        intent.putExtra("item", noteList.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        final MaterialDialog dialog = new MaterialDialog(this);
        dialog.isTitleShow(false)
                .content("是否确定删除改笔记?")
                .contentTextSize(18)
                .btnText("取消", "确定")
                .showAnim(new FadeEnter())
                .dismissAnim(new FadeExit())
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
                deleteNote(position);
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getNoteDatas();
            }
        });
        return false;
    }

    private void deleteNote(int position) {
        ConnService service = ServiceGenerator.createService(ConnService.class);
        Call<Code> call = service.deleteNote(noteList.get(position).getNoteId() + "", "delete");
        call.enqueue(new Callback<Code>() {
            @Override
            public void onResponse(Call<Code> call, Response<Code> response) {
                if (response.isSuccess()) {
                    if (response.body().getCode() == 1) {
                        ToastUtils.showShort(NoteActivity.this, response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Code> call, Throwable t) {

            }
        });
    }
}
