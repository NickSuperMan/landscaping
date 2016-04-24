package xzy.zzia.com.landscaping.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by roy on 4/23/16.
 */
public class LocationService extends Service implements BDLocationListener {

    private String TAG = "LocationService";

    public LocationClient mLocationClient = null;
    private BDLocation bdLocation;
    private Bitmap weatherBitmap;
    private String city;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "------------->onBind");

        return null;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i(TAG, "------>onStart");
        initLocation();
    }

    private void initLocation() {

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

    @Override
    public void onReceiveLocation(BDLocation location) {
        if (null == location) {
            if (bdLocation != null) {
                bdLocation = null;
            }
            Log.d(TAG, "定位失败");
            return;
        }

        /**
         * 61 ： GPS定位结果
         *  62 ： 扫描整合定位依据失败。此时定位结果无效。
         *  63 ： 网络异常，没有成功向服务器发起请求。此时定位结果无效。
         *  65 ： 定位缓存的结果。
         *  66 ： 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果
         *  67 ： 离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果
         *  68 ： 网络连接失败时，查找本地离线定位时对应的返回结果
         *  161： 表示网络定位结果
         *  162~167： 服务端定位失败
         *  502：key参数错误
         *  505：key不存在或者非法
         *  601：key服务被开发者自己禁用
         *  602：key mcode不匹配
         *  501～700：key验证失败
         */

        int code = location.getLocType();
        if (code == 161) {
            this.bdLocation = location;
            city = location.getCity();
            double latitude = location.getLatitude();
            double lontitude = location.getLongitude();
            String address = location.getAddrStr();
            Log.d(TAG, "city" + city + "latitude" + latitude + "lontitude" + lontitude + "address" + address);

        } else {
            if (bdLocation != null) {
                bdLocation = null;
            }
            Log.d(TAG, "定位失败：code" + code);
        }
    }

    @Override
    public void onDestroy() {

        if (mLocationClient != null) {
            mLocationClient.stop();
        }

        super.onDestroy();
    }
}
