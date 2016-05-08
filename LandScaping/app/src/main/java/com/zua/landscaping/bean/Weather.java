package com.zua.landscaping.bean;

/**
 * Created by roy on 4/24/16.
 */
public class Weather {

    private String city;
    private String pm25;
    private String dayImageUrl;
    private String nightImageUrl;
    private String date;
    private String weather;
    private String temperature;
    private String wind;


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayImageUrl() {
        return dayImageUrl;
    }

    public void setDayImageUrl(String dayImageUrl) {
        this.dayImageUrl = dayImageUrl;
    }

    public String getNightImageUrl() {
        return nightImageUrl;
    }

    public void setNightImageUrl(String nightImageUrl) {
        this.nightImageUrl = nightImageUrl;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "city='" + city + '\'' +
                ", pm25='" + pm25 + '\'' +
                ", dayImageUrl='" + dayImageUrl + '\'' +
                ", nightImageUrl='" + nightImageUrl + '\'' +
                ", date='" + date + '\'' +
                ", weather='" + weather + '\'' +
                ", temperature='" + temperature + '\'' +
                ", wind='" + wind + '\'' +
                '}';
    }
}
