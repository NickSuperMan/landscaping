package com.zua.landscaping.bean;

import java.util.Date;

public class Technical {

	private int technicalId;
	private String technicalName;
	private String technicalSummary;
	private String technicalUrl;
	private Date technicalTime;
	private String technicalStatus;
	public int getTechnicalId() {
		return technicalId;
	}
	public void setTechnicalId(int technicalId) {
		this.technicalId = technicalId;
	}
	public String getTechnicalName() {
		return technicalName;
	}
	public void setTechnicalName(String technicalName) {
		this.technicalName = technicalName;
	}
	public String getTechnicalSummary() {
		return technicalSummary;
	}
	public void setTechnicalSummary(String technicalSummary) {
		this.technicalSummary = technicalSummary;
	}
	public String getTechnicalUrl() {
		return technicalUrl;
	}
	public void setTechnicalUrl(String technicalUrl) {
		this.technicalUrl = technicalUrl;
	}
	public Date getTechnicalTime() {
		return technicalTime;
	}
	public void setTechnicalTime(Date technicalTime) {
		this.technicalTime = technicalTime;
	}
	public String getTechnicalStatus() {
		return technicalStatus;
	}
	public void setTechnicalStatus(String technicalStatus) {
		this.technicalStatus = technicalStatus;
	}
	@Override
	public String toString() {
		return "Technical [technicalId=" + technicalId + ", technicalName="
				+ technicalName + ", technicalSummary=" + technicalSummary
				+ ", technicalUrl=" + technicalUrl + ", technicalTime="
				+ technicalTime + ", technicalStatus=" + technicalStatus + "]";
	}
}
