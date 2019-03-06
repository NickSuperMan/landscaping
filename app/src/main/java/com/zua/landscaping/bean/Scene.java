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
    private String userName;
    private String userPicUrl;
    private String videoUrl;



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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPicUrl() {
        return userPicUrl;
    }

    public void setUserPicUrl(String userPicUrl) {
        this.userPicUrl = userPicUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public String toString() {
        return "Scene [sceneId=" + sceneId + ", userId=" + userId
                + ", sceneDescription=" + sceneDescription + ", scenePosition="
                + scenePosition + ", scenePicUrl=" + scenePicUrl
                + ", sceneTime=" + sceneTime + ", sceneStatus=" + sceneStatus
                + ", userName=" + userName + ", userPicUrl=" + userPicUrl + "]";
    }

}
