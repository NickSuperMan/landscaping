package com.zua.landscaping.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zua.landscaping.R;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.Weather;
import com.zua.landscaping.utils.TitleBuilder;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by roy on 4/24/16.
 */
public class WeatherActivity extends Activity {

    private TextView weather_date, weather_city, weather_weather, weather_wind, weather_temperture, weather_pm25;
    private ImageView weather_pic;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);

        new TitleBuilder(this).setTitleText(getString(R.string.weather)).build();

        initView();
        initEvent();
    }


    private void initView() {

        weather_city = (TextView) findViewById(R.id.weather_city);
        weather_date = (TextView) findViewById(R.id.weather_date);
        weather_pm25 = (TextView) findViewById(R.id.weather_pm25);
        weather_temperture = (TextView) findViewById(R.id.weather_temperature);
        weather_weather = (TextView) findViewById(R.id.weather_weather);
        weather_wind = (TextView) findViewById(R.id.weather_wind);
        weather_pic = (ImageView) findViewById(R.id.weather_pic);

    }

    private void initEvent() {
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        if (App.isWeatherIsFirstIn()) {
            Log.e("roy", "yibuzhiixng~~~~~~~");
            new MyAsyncWeather().execute(App.getCity());
            return;
        }

        fillData(App.getWeather());
    }

    class MyAsyncWeather extends AsyncTask<String, Void, JSONObject> {


        @Override
        protected JSONObject doInBackground(String... params) {
            String city = params[0];
            if (city.contains("市")) {
                city.replace("市", "");
            }
            StringBuffer url = new StringBuffer();
            url.append("http://api.map.baidu.com/telematics/v3/weather?location=");
            url.append(city);
            url.append("&output=json&ak=");
            url.append("fyxd9WjIzXiGiNOvgjPmuzet51QBlwro");
            try {
                URL url1 = new URL(url.toString());

                HttpURLConnection connection = (HttpURLConnection) url1.openConnection();

                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                int code = connection.getResponseCode();
                String result = "";
                if (code == 200) {
                    InputStream in = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(in, "utf-8");
                    BufferedReader reader = new BufferedReader(isr);
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        result += line;
                    }
                }
                JSONObject jsonObject = new JSONObject(result);
                return jsonObject;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject object) {
            super.onPostExecute(object);

            if (object == null) {
                return;
            }
            try {
                int code = object.getInt("error");
                String states = object.getString("status");
                if (code == 0 && states.equals("success")) {

                    Weather weather = new Weather();

                    JSONArray results = object.getJSONArray("results");
                    JSONObject resultObj = results.getJSONObject(0);
                    String city = resultObj.getString("currentCity");
                    weather.setCity(city);
                    String pm25 = resultObj.getString("pm25");
                    weather.setPm25(pm25);

                    JSONArray weatherArray = resultObj.getJSONArray("weather_data");
                    JSONObject weatherObj = weatherArray.getJSONObject(0);

                    String dayPictureUrl = weatherObj.getString("dayPictureUrl");
                    weather.setDayImageUrl(dayPictureUrl);
                    String nightPictureUrl = weatherObj.getString("nightPictureUrl");
                    weather.setNightImageUrl(nightPictureUrl);
                    String weather1 = weatherObj.getString("weather");
                    weather.setWeather(weather1);
                    String wind = weatherObj.getString("wind");
                    weather.setWind(wind);
                    String temperature = weatherObj.getString("temperature");
                    weather.setTemperature(temperature);
                    String date = weatherObj.getString("date");
                    weather.setDate(date);

                    App.setWeather(weather);
                    App.setWeatherIsFirstIn(false);
                    fillData(weather);


                } else {
                    Log.e("roy", code + "解析失败");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void fillData(Weather weather) {

        int tag = judeAmOrPm();

        if (tag == 0) {
            if (weather.getDayImageUrl() != null)
                imageLoader.displayImage(weather.getDayImageUrl(), weather_pic, options);
        } else {
            if (weather.getNightImageUrl() != null)
                imageLoader.displayImage(weather.getNightImageUrl(), weather_pic, options);
        }
        weather_city.setText(weather.getCity());
        weather_date.setText(weather.getDate());
        weather_wind.setText(weather.getWind());
        weather_weather.setText(weather.getWeather());
        weather_temperture.setText(weather.getTemperature());
        weather_pm25.setText(weather.getPm25());
    }


    public static int judeAmOrPm() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH");
        String hourString = format.format(date);
        int hour = Integer.parseInt(hourString);
        if (hour < 6 || hour > 19) {
            return 1;
        } else {
            return 0;
        }
    }
}
