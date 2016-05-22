package com.zua.landscaping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.yqritc.scalablevideoview.ScalableVideoView;
import com.zua.landscaping.R;


public class VideoPlayerActivity extends Activity {

    private String path;
    private ScalableVideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.actiity_layout_video_player);
        Intent intent = getIntent();
        path = intent.getStringExtra("url");

        Log.e("roy", path + "");

        init();
    }

    private void init() {

        videoView = (ScalableVideoView) findViewById(R.id.video_view);
    }

}
