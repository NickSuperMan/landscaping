package com.zua.landscaping.bean;


import java.util.Date;

public class Friends {

    private int friendId;
    private int userId;
    private int userFriendId;
    private Date addTime;

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserFriendId() {
        return userFriendId;
    }

    public void setUserFriendId(int userFriendId) {
        this.userFriendId = userFriendId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "Friends [friendId=" + friendId + ", userId=" + userId
                + ", userFriendId=" + userFriendId + ", addTime=" + addTime
                + "]";
    }

}
