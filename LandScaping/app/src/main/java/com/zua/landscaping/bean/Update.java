package com.zua.landscaping.bean;

import java.util.Date;

public class Update {

	private int updateId;
	private int pId;
	private Date updateTime;

	public int getUpdateId() {
		return updateId;
	}

	public void setUpdateId(int updateId) {
		this.updateId = updateId;
	}

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "Update [updateId=" + updateId + ", pId=" + pId
				+ ", updateTime=" + updateTime + "]";
	}

}
