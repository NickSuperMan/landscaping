package xzy.zzia.com.landscaping.app;

import android.app.Application;

/**
 * Created by roy on 16/4/21.
 */
public class App extends Application {

    public static boolean isLogin;


    @Override
    public void onCreate() {
        super.onCreate();


    }



    public static boolean isLogin() {
        isLogin = false;

        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        App.isLogin = isLogin;
    }

//    public void initLocation() {
//        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//        int span = 1000;
//        option.setScanSpan(span);
//        option.setIsNeedAddress(true);
//        option.setOpenGps(true);//可选，默认false,设置是否使用gps
//        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
//        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
//        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
//        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
//        mLocationClient.setLocOption(option);
//    }


}
