package com.zua.landscaping.bean;

public class Project {

	private int pId;
	private String pName;
	private int pTotalTime;
	private int pStartTime;
	private int PEndTime;
	private int pUseTime;

	public int getpUseTime() {
		return pUseTime;
	}

	public void setpUseTime(int pUseTime) {
		this.pUseTime = pUseTime;
	}

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public int getpTotalTime() {
		return pTotalTime;
	}

	public void setpTotalTime(int pTotalTime) {
		this.pTotalTime = pTotalTime;
	}

	public int getpStartTime() {
		return pStartTime;
	}

	public void setpStartTime(int pStartTime) {
		this.pStartTime = pStartTime;
	}

	public int getPEndTime() {
		return PEndTime;
	}

	public void setPEndTime(int pEndTime) {
		PEndTime = pEndTime;
	}

	@Override
	public String toString() {
		return "Project [pId=" + pId + ", pName=" + pName + ", pTotalTime="
				+ pTotalTime + ", pStartTime=" + pStartTime + ", PEndTime="
				+ PEndTime + ", pUseTime=" + pUseTime + "]";
	}


}
