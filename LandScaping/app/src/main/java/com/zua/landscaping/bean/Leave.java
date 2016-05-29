package com.zua.landscaping.bean;

import java.util.Date;

/**
 * Created by roy on 4/26/16.
 */
public class Leave {

    private int leaveId;
    private int userId;
    private int leaveStatus;
    private String leaveReason;
    private Date leaveTime;
    private Date backTime;
    private Date realBackTime;

    public Date getBackTime() {
        return backTime;
    }

    public void setBackTime(Date backTime) {
        this.backTime = backTime;
    }

    public int getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }

    public int getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(int leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public int getUserId() {
        return userId;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public Date getRealBackTime() {
        return realBackTime;
    }

    public void setRealBackTime(Date realBackTime) {
        this.realBackTime = realBackTime;
    }

    public void setUserId(int userId) {
        this.userId = userId;

    }
    @Override
    public String toString() {
        return "Leave [leaveId=" + leaveId + ", userId=" + userId
                + ", leaveStatus=" + leaveStatus + ", leaveReason="
                + leaveReason + ", leaveTime=" + leaveTime + ", backTime="
                + backTime + ", realBackTime=" + realBackTime + "]";
    }

}
