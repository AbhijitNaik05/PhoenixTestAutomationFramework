package com.api.constant;

public enum ServiceLocation {
	SERVICE_CENTER_A(1),SERVICE_CENTER_B(2),SERVICE_CENTER_C(3);
	
	int code;
	private ServiceLocation(int code) {
		this.code=code;
	}
	
	public int getCode() {
		return code;
	}
	
}
