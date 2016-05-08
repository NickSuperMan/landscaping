package com.zua.landscaping.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.zua.landscaping.bean.Device;
import com.zua.landscaping.bean.Project;
import com.zua.landscaping.bean.Scene;
import com.zua.landscaping.bean.Technical;
import com.zua.landscaping.bean.User;
import com.zua.landscaping.bean.Weather;
import com.zua.landscaping.utils.ToastUtils;

import java.io.File;
import java.util.List;

import io.rong.imkit.RongIM;


/**
 * Created by roy on 16/4/21.
 */
public class App extends Application implements BDLocationListener {

    public static boolean isLogin;
    private static String city;
    private BDLocation bdLocation;
    private LocationClient mLocationClient;
    public static Weather weather;
    public static boolean weatherIsFirstIn;
    public static User user;
    private static String position;
    public static List<Scene> sceneVideoList;
    public static List<Scene> scenePhotoList;
    public static List<Scene> sceneSafeList;
    public static List<Scene> sceneOpinionList;
    public static List<Project> projectList;
    private static ImageLoader imageLoader;
    private static DisplayImageOptions options;
    private static Bitmap icon;
    private static List<Device> deviceList;
    private static List<Technical> technicalList;


    @Override
    public void onCreate() {
        super.onCreate();

        weatherIsFirstIn = true;
        initRong();
        initImageLoader();
        initLocation();

    }

    private static void getUserIcon() {
        imageLoader = ImageLoader.getInstance();

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        imageLoader.loadImage(Constant.PicPath + getUser().getUserPicUrl(), options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                icon = loadedImage;
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });

    }

    private void initRong() {
        /**
         *
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(this);

            if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {

                MyContext.init(this);
            }
        }
    }

    /**
     * 获取当前进程的名字
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }

        return null;
    }

    private void initImageLoader() {

        File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "LandScaping/cache");

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration
                .Builder(getApplicationContext());
        config.threadPriority(Thread.NORM_PRIORITY);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.diskCache(new UnlimitedDiskCache(cacheDir));
        config.writeDebugLogs();

        ImageLoader.getInstance().init(config.build());
    }


    public void initLocation() {

        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(this);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        int span = 1000;
        option.setScanSpan(span);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();

    }


    public static boolean isLogin() {
        isLogin = false;

        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        App.isLogin = isLogin;
    }


    @Override
    public void onReceiveLocation(BDLocation location) {
        if (null == location) {
            if (bdLocation != null) {
                bdLocation = null;
            }
            Log.e("roy", "定位失败");
            return;
        }

        int code = location.getLocType();
        if (code == 161) {
            this.bdLocation = location;
            String city = location.getCity();
            String near = location.getAddrStr();
            if (!city.isEmpty() && !near.isEmpty()) {
                this.city = city;
                this.position = near;
            }
            mLocationClient.stop();
            ToastUtils.showShort(this, "city" + city + "~~~~" + near);
        } else {
            if (bdLocation != null) {
                bdLocation = null;
            }
            Log.e("roy", "定位失败");
        }

    }

    public static String getCity() {
        return city;
    }

    public static Weather getWeather() {
        return weather;
    }

    public static void setWeather(Weather weather) {
        App.weather = weather;
    }

    public static boolean isWeatherIsFirstIn() {
        return weatherIsFirstIn;
    }

    public static void setWeatherIsFirstIn(boolean weatherIsFirstIn) {
        App.weatherIsFirstIn = weatherIsFirstIn;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {

        App.user = user;
        getUserIcon();
    }

    public static String getPosition() {
        return position;
    }

    public static List<Project> getProjectList() {
        return projectList;
    }

    public static void setProjectList(List<Project> projectList) {
        App.projectList = projectList;
    }

    public static List<Scene> getSceneOpinionList() {
        return sceneOpinionList;
    }

    public static void setSceneOpinionList(List<Scene> sceneOpinionList) {
        App.sceneOpinionList = sceneOpinionList;
    }

    public static List<Scene> getSceneSafeList() {
        return sceneSafeList;
    }

    public static void setSceneSafeList(List<Scene> sceneSafeList) {
        App.sceneSafeList = sceneSafeList;
    }

    public static List<Scene> getScenePhotoList() {
        return scenePhotoList;
    }

    public static void setScenePhotoList(List<Scene> scenePhotoList) {
        App.scenePhotoList = scenePhotoList;
    }

    public static List<Scene> getSceneVideoList() {
        return sceneVideoList;
    }

    public static void setSceneVideoList(List<Scene> sceneVideoList) {
        App.sceneVideoList = sceneVideoList;
    }

    public static Bitmap getIcon() {
        return icon;
    }

    public static List<Device> getDeviceList() {
        return deviceList;
    }

    public static void setDeviceList(List<Device> deviceList) {
        App.deviceList = deviceList;
    }

    public static List<Technical> getTechnicalList() {
        return technicalList;
    }

    public static void setTechnicalList(List<Technical> technicalList) {
        App.technicalList = technicalList;
    }
}
