package com.zua.landscaping.bean;

import java.util.Date;

/**
 * Created by roy on 4/26/16.
 */
public class Meeting {

    private int meetId;
    private String meetName;
    private String meetContent;
    private String meetCompere;
    private Date meetTime;


    public String getMeetCompere() {
        return meetCompere;
    }

    public void setMeetCompere(String meetCompere) {
        this.meetCompere = meetCompere;
    }

    public String getMeetContent() {
        return meetContent;
    }

    public void setMeetContent(String meetContent) {
        this.meetContent = meetContent;
    }

    public int getMeetId() {
        return meetId;
    }

    public void setMeetId(int meetId) {
        this.meetId = meetId;
    }

    public String getMeetName() {
        return meetName;
    }

    public void setMeetName(String meetName) {
        this.meetName = meetName;
    }

    public Date getMeetTime() {
        return meetTime;
    }

    public void setMeetTime(Date meetTime) {
        this.meetTime = meetTime;
    }


    @Override
    public String toString() {
        return "Meeting{" +
                "meetCompere='" + meetCompere + '\'' +
                ", meetId=" + meetId +
                ", meetName='" + meetName + '\'' +
                ", meetContent='" + meetContent + '\'' +
                ", meetTime=" + meetTime +
                '}';
    }
}
