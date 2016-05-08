package com.zua.landscaping.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.VideoView;

import com.zua.landscaping.R;
import com.zua.landscaping.app.App;
import com.zua.landscaping.app.Constant;
import com.zua.landscaping.utils.TitleBuilder;
import com.zua.landscaping.utils.ToastUtils;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by roy on 4/27/16.
 */
public class RecordSuccessActivity extends Activity {

    private String path = "";
    private VideoView videoView;
    private EditText video_text_description;
    private String description;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_preview_video);

        new TitleBuilder(this).setTitleText(getString(R.string.record_preview)).setLeftText(getString(R.string.re_record)).setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecordSuccessActivity.this, RecordSuccessActivity.class));
                finish();
            }
        }).setRightText(getString(R.string.video_publish)).setRightOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upload();
            }

        }).build();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        path = extras.getString("text");

        videoView = (VideoView) findViewById(R.id.record_preview);
        videoView.setVideoPath(path);
        videoView.start();

        video_text_description = (EditText) findViewById(R.id.video_text_description);

    }

    private void upload() {
        dialog = ProgressDialog.show(this, null, getString(R.string.video_loading));

        description = video_text_description.getText().toString().trim();

        File file = new File(path);
        AjaxParams params = new AjaxParams();
        params.put("userId", App.getUser().getUserId() + "");
        params.put("sceneDescription", description);
        params.put("scenePosition", App.getPosition() + "");
        Log.e("roy", App.getPosition());

        try {
            params.put("file", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FinalHttp http = new FinalHttp();
        http.post(Constant.BasePath + "UploadVideoServlet", params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

                ToastUtils.showShort(RecordSuccessActivity.this, "upload success");
                deleteDirectory();
                finish();
            }

            @Override
            public void onLoading(long count, long current) {
                super.onLoading(count, current);


            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("roy", t.toString() + strMsg);
            }
        });
    }

    private void deleteDirectory() {
        File imageFileDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "LandScaping" + File.separator + "video");
        if (imageFileDir.exists() && imageFileDir.isDirectory()) {
            File[] files = imageFileDir.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    File imageFile = new File(files[i].getAbsolutePath());
                    if (imageFile.isFile() && imageFile.exists()) {
                        imageFile.delete();
                    }
                }
            }
            imageFileDir.delete();
        }
    }

}
