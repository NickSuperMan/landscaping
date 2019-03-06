package com.zua.landscaping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.zua.landscaping.R;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.Event;
import com.zua.landscaping.utils.DataLoad;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by roy on 4/28/16.
 */
public class SplashActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_layout_splash);
        App.getInstance().addActivity(this);
        getData();
    }

    private void getData() {
        DataLoad.getNewsData();
        DataLoad.getProjectData();
        DataLoad.getVideoData();
        DataLoad.getPhotoData();
        DataLoad.getSafeData();
        DataLoad.getOpinionData();
        DataLoad.getDeviceData();
        DataLoad.getTechnicalData();
        DataLoad.getDrawingData();
        DataLoad.getMeetingData();
    }

    @Subscribe
    public void onEventMainThread(Event event) {

        Log.e("roy", "splash");

        if (App.getSceneVideoList() != null && App.getScenePhotoList() != null && App.getProjectList() != null &&
                App.getDeviceList() != null && App.getTechnicalList() != null && App.getNewsList() != null && App.getSceneOpinionList() != null
                && App.getSceneSafeList() != null && App.getDrawingList() != null && App.getMeetingList() != null) {

            startActivity(new Intent(SplashActivity.this, LoginActivity1.class));
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
