package com.api.constant;

public enum Warrenty_Status {
	IN_WARRENTY(1), OUT_OF_WARRENTY(2);
	
	int code;
	private Warrenty_Status(int code) {
		this.code=code;
	}
	
	public int getCode() {
		return code;
	}
}
