package com.zua.landscaping.bean;

import java.util.Date;

/**
 * Created by roy on 4/26/16.
 */
public class Sign {

    private int signId;
    private int userId;
    private Date signTime;
    private String signPosition;
    private int currentPeople;
    private int totalPeople;

    public int getSignId() {
        return signId;
    }

    public void setSignId(int signId) {
        this.signId = signId;
    }

    public String getSignPosition() {
        return signPosition;
    }

    public void setSignPosition(String signPosition) {
        this.signPosition = signPosition;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

	public int getCurrentPeople() {
		return currentPeople;
	}

	public void setCurrentPeople(int currentPeople) {
		this.currentPeople = currentPeople;
	}

	public int getTotalPeople() {
		return totalPeople;
	}

	public void setTotalPeople(int totalPeople) {
		this.totalPeople = totalPeople;
	}

	@Override
	public String toString() {
		return "Sign [signId=" + signId + ", userId=" + userId + ", signTime="
				+ signTime + ", signPosition=" + signPosition
				+ ", currentPeople=" + currentPeople + ", totalPeople="
				+ totalPeople + "]";
	}

  
}
