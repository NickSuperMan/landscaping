package com.zua.landscaping.utils;

import android.util.Log;

import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.Device;
import com.zua.landscaping.bean.Drawing;
import com.zua.landscaping.bean.Event;
import com.zua.landscaping.bean.Leave;
import com.zua.landscaping.bean.Meeting;
import com.zua.landscaping.bean.News;
import com.zua.landscaping.bean.Project;
import com.zua.landscaping.bean.Scene;
import com.zua.landscaping.bean.Technical;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by roy on 16/5/21.
 */
public class DataLoad {

    private static List<Scene> sceneVideo;
    private static List<Scene> scenesPhoto;
    private static List<Scene> sceneSafe;
    private static List<Scene> sceneOpinion;
    private static List<Project> projects;
    private static List<Device> deviceList;
    private static List<Technical> technicalList;
    private static List<News> newsList;
    private static List<Drawing> drawingList;
    private static List<Meeting> meetingList;


    private static ConnService service = ServiceGenerator.createService(ConnService.class);

    public static void getNewsData() {

        Call<List<News>> call = service.getAllNews();
        call.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if (response.isSuccess()) {
                    newsList = response.body();
                    App.setNewsList(newsList);
//                    Message message = new Message();
//                    message.obj = 1;
//                    handler.sendMessage(message);

                    EventBus.getDefault().post(new Event("1"));
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {

            }
        });
    }

    public static void getTechnicalData() {

        Call<List<Technical>> call = service.getAllTechnical();
        call.enqueue(new Callback<List<Technical>>() {
            @Override
            public void onResponse(Call<List<Technical>> call, Response<List<Technical>> response) {
                if (response.isSuccess()) {
                    technicalList = response.body();
                    App.setTechnicalList(technicalList);

                    EventBus.getDefault().post(new Event("2"));
                }
            }

            @Override
            public void onFailure(Call<List<Technical>> call, Throwable t) {

            }
        });
    }

    public static void getVideoData() {

        Call<List<Scene>> video = service.getAllScene("query", "1", "1");
        Log.e("roy", "getPhotoScene");
        video.enqueue(new Callback<List<Scene>>() {
            @Override
            public void onResponse(Call<List<Scene>> call, Response<List<Scene>> response) {
                if (response.isSuccess()) {
                    sceneVideo = response.body();
                    App.setSceneVideoList(sceneVideo);
                    EventBus.getDefault().post(new Event("3"));
                }
            }

            @Override
            public void onFailure(Call<List<Scene>> call, Throwable t) {
                Log.e("roy", "~~~~~~~" + t.toString());
            }

        });
    }

    public static void getPhotoData() {

        Call<List<Scene>> photo = service.getAllScene("query", "1", "0");
        photo.enqueue(new Callback<List<Scene>>() {
            @Override
            public void onResponse(Call<List<Scene>> call, Response<List<Scene>> response) {
                if (response.isSuccess()) {
                    scenesPhoto = response.body();
                    App.setScenePhotoList(scenesPhoto);
                    EventBus.getDefault().post(new Event("4"));
                }
            }

            @Override
            public void onFailure(Call<List<Scene>> call, Throwable t) {

            }
        });


    }

    public static void getProjectData() {


        Call<List<Project>> call = service.getAllProjects();
        call.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if (response.isSuccess()) {
                    projects = response.body();
                    App.setProjectList(projects);
                    EventBus.getDefault().post(new Event("5"));
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {

            }
        });
    }

    public static void getDeviceData() {


        Call<List<Device>> call = service.getAllDevices("query");
        call.enqueue(new Callback<List<Device>>() {
            @Override
            public void onResponse(Call<List<Device>> call, Response<List<Device>> response) {
                if (response.isSuccess()) {
                    deviceList = response.body();
                    App.setDeviceList(deviceList);
                    EventBus.getDefault().post(new Event("6"));
                }
            }

            @Override
            public void onFailure(Call<List<Device>> call, Throwable t) {

            }
        });
    }

    public static void getOpinionData() {

        Call<List<Scene>> video = service.getAllScene("query", "1", "3");

        video.enqueue(new Callback<List<Scene>>() {
            @Override
            public void onResponse(Call<List<Scene>> call, Response<List<Scene>> response) {
                if (response.isSuccess()) {
                    sceneOpinion = response.body();
                    App.setSceneOpinionList(sceneOpinion);
                    EventBus.getDefault().post(new Event("7"));
                }
            }

            @Override
            public void onFailure(Call<List<Scene>> call, Throwable t) {
                Log.e("roy", "~~~~~~~" + t.toString());
            }

        });
    }


    public static void getSafeData() {

        Call<List<Scene>> safe = service.getAllScene("query", "1", "2");

        safe.enqueue(new Callback<List<Scene>>() {
            @Override
            public void onResponse(Call<List<Scene>> call, Response<List<Scene>> response) {
                if (response.isSuccess()) {
                    sceneSafe = response.body();
                    App.setSceneSafeList(sceneSafe);
                    EventBus.getDefault().post(new Event("8"));
                }
            }

            @Override
            public void onFailure(Call<List<Scene>> call, Throwable t) {
                Log.e("roy", "~~~~~~~" + t.toString());
            }
        });
    }

    public static void getDrawingData() {
        Call<List<Drawing>> call = service.getAllDrawing();
        call.enqueue(new Callback<List<Drawing>>() {
            @Override
            public void onResponse(Call<List<Drawing>> call, Response<List<Drawing>> response) {
                if (response.isSuccess()) {
                    drawingList = response.body();
                    App.setDrawingList(drawingList);
                    EventBus.getDefault().post(new Event("9"));
                }
            }

            @Override
            public void onFailure(Call<List<Drawing>> call, Throwable t) {

            }
        });
    }

    public static void getMeetingData() {
        Call<List<Meeting>> call = service.getAllMeeting();
        call.enqueue(new Callback<List<Meeting>>() {
            @Override
            public void onResponse(Call<List<Meeting>> call, Response<List<Meeting>> response) {
                if (response.isSuccess()) {
                    meetingList = response.body();
                    Log.e("roy", meetingList.toString());
                    App.setMeetingList(meetingList);
                    EventBus.getDefault().post(new Event("10"));
                }
            }

            @Override
            public void onFailure(Call<List<Meeting>> call, Throwable t) {
                Log.e("roy", t.toString());
            }
        });
    }

}
