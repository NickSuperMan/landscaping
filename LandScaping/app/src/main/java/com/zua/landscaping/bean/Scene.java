package com.zua.landscaping.bean;

import java.util.Date;

/**
 * Created by roy on 4/26/16.
 */
public class Scene {

    private int sceneId;
    private int userId;
    private String sceneDescription;
    private String scenePosition;
    private String scenePicUrl;
    private Date sceneTime;
    private int sceneStatus;


    public String getSceneDescription() {
        return sceneDescription;
    }

    public void setSceneDescription(String sceneDescription) {
        this.sceneDescription = sceneDescription;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public String getScenePicUrl() {
        return scenePicUrl;
    }

    public void setScenePicUrl(String scenePicUrl) {
        this.scenePicUrl = scenePicUrl;
    }

    public String getScenePosition() {
        return scenePosition;
    }

    public void setScenePosition(String scenePosition) {
        this.scenePosition = scenePosition;
    }

    public int getSceneStatus() {
        return sceneStatus;
    }

    public void setSceneStatus(int sceneStatus) {
        this.sceneStatus = sceneStatus;
    }

    public Date getSceneTime() {
        return sceneTime;
    }

    public void setSceneTime(Date sceneTime) {
        this.sceneTime = sceneTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Scene{" +
                "sceneDescription='" + sceneDescription + '\'' +
                ", sceneId=" + sceneId +
                ", userId=" + userId +
                ", scenePosition='" + scenePosition + '\'' +
                ", scenePicUrl='" + scenePicUrl + '\'' +
                ", sceneTime=" + sceneTime +
                ", sceneStatus=" + sceneStatus +
                '}';
    }
}
