package com.zua.landscaping.bean;

public class Result {

	private String code;
	private String token;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "Result [code=" + code + ", token=" + token + "]";
	}
}
