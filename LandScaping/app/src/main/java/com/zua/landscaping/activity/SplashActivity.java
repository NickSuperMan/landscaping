package com.zua.landscaping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zua.landscaping.R;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.Device;
import com.zua.landscaping.bean.Project;
import com.zua.landscaping.bean.Scene;
import com.zua.landscaping.bean.Technical;
import com.zua.landscaping.utils.ConnService;
import com.zua.landscaping.utils.ServiceGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by roy on 4/28/16.
 */
public class SplashActivity extends Activity {

    private List<Scene> sceneVideo;
    private List<Scene> scenesPhoto;
    private List<Scene> sceneSafe;
    private List<Scene> sceneOpinion;
    private List<Project> projects;
    private List<Device> deviceList;
    private List<Technical> technicalList;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layout_splash);

        getProjectData();
        getVideoData();
        getPhotoData();
        getDeviceData();
        getTechnicalData();

        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }

    private void getTechnicalData() {
        ConnService service = ServiceGenerator.createService(ConnService.class);
        Call<List<Technical>> call = service.getAllTechnical();
        call.enqueue(new Callback<List<Technical>>() {
            @Override
            public void onResponse(Call<List<Technical>> call, Response<List<Technical>> response) {
                if (response.isSuccess()){
                    technicalList = response.body();
                    App.setTechnicalList(technicalList);
                }
            }

            @Override
            public void onFailure(Call<List<Technical>> call, Throwable t) {

            }
        });
    }

    private void getVideoData() {
        ConnService service = ServiceGenerator.createService(ConnService.class);
        Call<List<Scene>> video = service.getAllScene("1", "1");
        Log.e("roy", "getPhotoScene");
        video.enqueue(new Callback<List<Scene>>() {
            @Override
            public void onResponse(Call<List<Scene>> call, Response<List<Scene>> response) {
                if (response.isSuccess()) {
                    sceneVideo = response.body();
                    App.setSceneVideoList(sceneVideo);
                    Log.e("roy", sceneVideo.toString() + "");

                }
            }

            @Override
            public void onFailure(Call<List<Scene>> call, Throwable t) {
                Log.e("roy", "~~~~~~~" + t.toString());
            }

        });
    }

    private void getPhotoData() {
        ConnService service = ServiceGenerator.createService(ConnService.class);

        Call<List<Scene>> photo = service.getAllScene("1", "0");
        photo.enqueue(new Callback<List<Scene>>() {
            @Override
            public void onResponse(Call<List<Scene>> call, Response<List<Scene>> response) {
                if (response.isSuccess()) {
                    scenesPhoto = response.body();
                    App.setScenePhotoList(scenesPhoto);
                    Log.e("roy", scenesPhoto.toString() + "~~");

                }
            }

            @Override
            public void onFailure(Call<List<Scene>> call, Throwable t) {

            }
        });


    }

    private void getProjectData() {

        ConnService service = ServiceGenerator.createService(ConnService.class);
        Call<List<Project>> call = service.getAllProjects();
        call.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if (response.isSuccess()) {
                    projects = response.body();
                    App.setProjectList(projects);
                    Log.e("roy", projects.toString() + "~~");

                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {

            }
        });
    }

    private void getDeviceData() {

        ConnService service = ServiceGenerator.createService(ConnService.class);
        Call<List<Device>> call = service.getAllDevices("query");
        call.enqueue(new Callback<List<Device>>() {
            @Override
            public void onResponse(Call<List<Device>> call, Response<List<Device>> response) {
                if (response.isSuccess()) {
                    deviceList = response.body();
                    App.setDeviceList(deviceList);
                }
            }

            @Override
            public void onFailure(Call<List<Device>> call, Throwable t) {

            }
        });
    }


}
