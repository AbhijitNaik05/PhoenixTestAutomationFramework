package com.api.constant;

public enum Platform_Id {
	FST(3), FRONTDESK(2);

	int code;

	private Platform_Id(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
