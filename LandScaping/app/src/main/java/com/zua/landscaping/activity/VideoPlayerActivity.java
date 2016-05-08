package com.zua.landscaping.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.zua.landscaping.R;
import com.zua.landscaping.bean.ScreenBean;
import com.zua.landscaping.utils.LocUtil;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;


public class VideoPlayerActivity extends Activity implements MediaPlayer.OnInfoListener, MediaPlayer.OnCompletionListener {

    private String path = "";
    /**
     * 当前声音
     */
    private int mVolume = -1;
    /**
     * 最大音量
     */
    private int mMaxVolume;
    /**
     * 当前亮度
     */
    private float mBrightness = -1f;
    /**
     * 手势数目
     */
    private int finNum = 0;

    private View mParentVideo;
    private View mVolumeBrightnessLayout;
    private View mLoadingView;
    private ImageView mOperationBg;
    private ImageView mOperationPercent;
    private VideoView mVideoView;
    private GestureDetector gestureDetector;
    private ScaleGestureDetector scaleGestureDetector;

    private ScreenBean screenBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Vitamio.isInitialized(getApplicationContext());



        setContentView(R.layout.actiity_layout_video_player);
        Intent intent = getIntent();
        path = intent.getStringExtra("url");

        Log.e("roy", path + "");

        init();
    }

    private void init() {

        mVideoView = (VideoView) findViewById(R.id.surface_view);
        mVolumeBrightnessLayout = findViewById(R.id.operation_volume_brightness);
        mOperationBg = (ImageView) findViewById(R.id.operation_bg);
        mOperationPercent = (ImageView) findViewById(R.id.operation_percent);
        mLoadingView = findViewById(R.id.video_loading);
        mParentVideo = findViewById(R.id.vvVideo);

        mMaxVolume = LocUtil.getMaxVolume(this);
        gestureDetector = new GestureDetector(this, new SingleGestureListener());
        scaleGestureDetector = new ScaleGestureDetector(this, new MultiGestureListener());

        mVideoView.setBufferSize(1024 * 4);
        mVideoView.setOnInfoListener(this);
        mVideoView.setOnCompletionListener(this);
        screenBean = LocUtil.getScreenPix(this);

        if (path == "") {
            return;
        } else {
//            mVideoView.setVideoPath(path);
            mVideoView.setVideoURI(Uri.parse(path));
            MediaController controller = new MediaController(this, true, mParentVideo);

            mVideoView.setMediaController(controller);
            mVideoView.requestFocus();

            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setPlaybackSpeed(1.0f);
                }
            });
        }
    }


    private Handler mDismissHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mVolumeBrightnessLayout.setVisibility(View.GONE);
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        finNum = event.getPointerCount();
        if (1 == finNum) {
            gestureDetector.onTouchEvent(event);
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    endGesture();
            }
        } else if (2 == finNum) {
            scaleGestureDetector.onTouchEvent(event);
        }
        return true;
    }

    private void endGesture() {
        mVolume = -1;
        mBrightness = -1f;

        mDismissHandler.removeMessages(0);
        mDismissHandler.sendEmptyMessageDelayed(0, 500);
    }

    private boolean isPlaying() {
        return mVideoView != null && mVideoView.isPlaying();
    }

    private void stopPlayer() {
        if (mVideoView != null) {
            mVideoView.pause();
        }
    }

    private boolean needResume;

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
//                if (isPlaying()) {
//                    stopPlayer();
                needResume = true;
//                }
//                mLoadingView.setVisibility(View.VISIBLE);
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                if (needResume)
                    startPlayer();
                mLoadingView.setVisibility(View.GONE);
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                break;

        }
        return true;
    }

    private void startPlayer() {
        if (mVideoView != null) {
            mVideoView.start();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        finish();
    }


    private class SingleGestureListener implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            if (2 == finNum) {
                return false;
            }

            float moldX = e1.getX();
            float moldY = e1.getY();
            float y = e2.getY();

            if (moldX > screenBean.getsWidth() * 9.0 / 10) /**右边滑动*/ {
                changeVolume((moldY - y) / screenBean.getsHeight());
            } else if (moldX < screenBean.getsWidth() / 10.0)/**左边滑动*/ {
                changeBrightness((moldY - y) / screenBean.getsHeight());
            }

            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }

    private void changeBrightness(float percent) {
        if (mBrightness < 0) {
            mBrightness = getWindow().getAttributes().screenBrightness;
            if (mBrightness <= 0.00f)
                mBrightness = 0.50f;
            if (mBrightness < 0.01f)
                mBrightness = 0.01f;

            mOperationBg.setImageResource(R.drawable.video_brightness_bg);
            mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
        }

        WindowManager.LayoutParams lpa = getWindow().getAttributes();
        lpa.screenBrightness = mBrightness + percent;
        lpa.screenBrightness = mBrightness + percent;
        if (lpa.screenBrightness > 1.0f)
            lpa.screenBrightness = 1.0f;
        else if (lpa.screenBrightness < 0.01f)
            lpa.screenBrightness = 0.01f;
        getWindow().setAttributes(lpa);

        ViewGroup.LayoutParams lp = mOperationPercent.getLayoutParams();
        lp.width = (int) (findViewById(R.id.operation_full).getLayoutParams().width * lpa.screenBrightness);
        mOperationPercent.setLayoutParams(lp);
    }

    private void changeVolume(float percent) {
        if (mVolume == -1) {
            mVolume = LocUtil.getCurVolume(this);
            if (mVolume < 0)
                mVolume = 0;
            mOperationBg.setImageResource(R.drawable.video_volumn_bg);
            mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
        }

        int index = (int) (percent * mMaxVolume + mVolume);
        if (index > mMaxVolume) {
            index = mMaxVolume;
        } else if (index < 0) {
            index = 0;
        }

        LocUtil.setCurVolume(this, index);

        ViewGroup.LayoutParams lp = mOperationPercent.getLayoutParams();
        lp.width = findViewById(R.id.operation_full).getLayoutParams().width * index / mMaxVolume;
        mOperationPercent.setLayoutParams(lp);
    }

    private class MultiGestureListener implements ScaleGestureDetector.OnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

            float oldDis = detector.getPreviousSpan();
            float curDis = detector.getCurrentSpan();
            if (oldDis - curDis > 50) {
                changeLayout(0);
            } else if (oldDis - curDis < -50) {
                changeLayout(1);
            }
        }
    }

    private void changeLayout(int size) {
        mVideoView.setVideoLayout(size, 0);
    }
}
