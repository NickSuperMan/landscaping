package com.zua.landscaping.bean;

import java.util.Date;

/**
 * Created by roy on 4/26/16.
 */
public class Device {

    private int deviceId;
    private String deviceName;
    private Date deviceTime;
    private String deviceOwner;
    private String devicecPicUrl;

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceOwner() {
        return deviceOwner;
    }

    public void setDeviceOwner(String deviceOwner) {
        this.deviceOwner = deviceOwner;
    }

    public Date getDeviceTime() {
        return deviceTime;
    }

    public void setDeviceTime(Date deviceTime) {
        this.deviceTime = deviceTime;
    }

    public String getDevicecPicUrl() {
        return devicecPicUrl;
    }

    public void setDevicecPicUrl(String devicecPicUrl) {
        this.devicecPicUrl = devicecPicUrl;
    }

    @Override
    public String toString() {
        return "Device [deviceId=" + deviceId + ", deviceName=" + deviceName
                + ", deviceTime=" + deviceTime + ", deviceOwner=" + deviceOwner
                + ", devicecPicUrl=" + devicecPicUrl + "]";
    }

}
