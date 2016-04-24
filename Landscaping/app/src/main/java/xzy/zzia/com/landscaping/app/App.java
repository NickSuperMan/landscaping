package xzy.zzia.com.landscaping.app;

import android.app.Application;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

import xzy.zzia.com.landscaping.bean.Weather;
import xzy.zzia.com.landscaping.utils.ToastUtils;


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


    @Override
    public void onCreate() {
        super.onCreate();

        weatherIsFirstIn = true;
        initImageLoader();
        initLocation();
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
            if (!city.isEmpty() && !near.isEmpty())
                this.city = city;
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
}
