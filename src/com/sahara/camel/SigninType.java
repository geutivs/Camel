package com.sahara.camel;

public enum SigninType {
	
	NFC(3),
	BARCODE(4),
	LOCATION(5),
	;

	private int type;
	
	private SigninType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
