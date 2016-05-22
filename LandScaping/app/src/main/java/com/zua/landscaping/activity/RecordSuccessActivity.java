package com.zua.landscaping.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.yqritc.scalablevideoview.ScalableVideoView;
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
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by roy on 4/27/16.
 */
public class RecordSuccessActivity extends Activity {

    private String path = "";
    private ScalableVideoView mScalableVideoView;
    private ImageView mPlayImageView;
    private ImageView mThumbnailImageView;
    private EditText video_text_description;
    private String description;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_preview_video);



        path = getIntent().getStringExtra("text");
        Log.e("roy", path + "");
        if (TextUtils.isEmpty(path)) {
            Toast.makeText(this, "视频路径错误", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setContentView(R.layout.activity_layout_preview_video);
        mScalableVideoView = (ScalableVideoView) findViewById(R.id.video_view);
        try {
            // 这个调用是为了初始化mediaplayer并让它能及时和surface绑定
            mScalableVideoView.setDataSource("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        mPlayImageView = (ImageView) findViewById(R.id.playImageView);

        mThumbnailImageView = (ImageView) findViewById(R.id.thumbnailImageView);
        mThumbnailImageView.setImageBitmap(getVideoThumbnail(path));


        video_text_description = (EditText) findViewById(R.id.video_text_description);


        new TitleBuilder(this).setTitleText(getString(R.string.record_preview)).setLeftText(getString(R.string.re_record)).setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecordSuccessActivity.this, RecordVideoActivity.class));
                finish();
            }
        }).setRightText(getString(R.string.video_publish)).setRightOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upload();
            }

        }).build();

    }


    /**
     * 获取视频缩略图（这里获取第一帧）
     *
     * @param filePath
     * @return
     */
    public Bitmap getVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime(TimeUnit.MILLISECONDS.toMicros(1));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_view:
                mScalableVideoView.stop();
                mPlayImageView.setVisibility(View.VISIBLE);
                mThumbnailImageView.setVisibility(View.VISIBLE);
                break;
            case R.id.playImageView:
                try {

                    mScalableVideoView.setDataSource(path);
                    mScalableVideoView.setLooping(false);
                    mScalableVideoView.prepare();
                    mScalableVideoView.start();
                    mPlayImageView.setVisibility(View.GONE);
                    mThumbnailImageView.setVisibility(View.GONE);
                } catch (IOException e) {
                    Log.e("roy", e.getLocalizedMessage());
                    Toast.makeText(this, "播放视频异常", Toast.LENGTH_SHORT).show();
                }
                break;
        }
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


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
