package com.zua.landscaping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.zua.landscaping.R;
import com.zua.landscaping.utils.TitleBuilder;
import com.zua.landscaping.utils.ToastUtils;
import com.zua.landscaping.view.MovieRecorderView;

/**
 * Created by roy on 4/27/16.
 */
public class RecordVideoActivity extends Activity {

    private MovieRecorderView mRecorderView;//视频录制控件
    private Button mShootBtn;//视频开始录制按钮
    private boolean isFinish = true;
    private boolean success = false;//防止录制完成后出现多次跳转事件

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (success) {
                startActivity();
            }
        }
    };

    private void startActivity() {
        if (isFinish) {
            mRecorderView.stop();
            Intent intent = new Intent(this, RecordSuccessActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("text", mRecorderView.getmVecordFile().toString());
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
        success = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layout_record);

        new TitleBuilder(this).setLeftImage(R.drawable.back).setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).setTitleText(getString(R.string.record)).build();

        initView();
        initEvent();
    }

    private void initView() {
        mRecorderView = (MovieRecorderView) findViewById(R.id.movieRecordView);
        mShootBtn = (Button) findViewById(R.id.shoot_button);
    }

    private void initEvent() {
        //用户长按事件监听
        mShootBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mShootBtn.setBackgroundResource(R.drawable.bg_movie_add_shoot_select);
                    mRecorderView.record(new MovieRecorderView.OnRecordFinishListener() {
                        @Override
                        public void onRecordFinish() {
                            if (!success && mRecorderView.getTimeCount() < 10) {
                                success = true;
                                handler.sendEmptyMessage(1);
                            }
                        }
                    });
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    mShootBtn.setBackgroundResource(R.drawable.bg_movie_add_shoot);
                    if (mRecorderView.getTimeCount() > 3) {
                        if (!success) {
                            success = true;
                            handler.sendEmptyMessage(1);
                        }
                    } else {
                        success = false;
                        if (mRecorderView.getmVecordFile() != null)
                            mRecorderView.getmVecordFile().delete();
                        mRecorderView.stop();
                        ToastUtils.showShort(RecordVideoActivity.this, "Time too short");
                    }
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isFinish = true;
        if (mRecorderView.getmVecordFile() != null)
            mRecorderView.getmVecordFile().delete();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        isFinish = false;
        success = false;
        mRecorderView.stop();
    }

}
