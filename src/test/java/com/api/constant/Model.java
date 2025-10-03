package com.api.constant;

public enum Model {
 NEXUS_2_BLUE(1),GALLEXY(2);
	int model;
	
	private Model(int model) {
		this.model=model;
	}
	
	public int getModel() {
		return model;
	}
}
