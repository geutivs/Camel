package com.sahara.camel.network;

public class UrlLocations {

	private static final String SERVER_ENDPOINT = "139.196.21.38:9000";
	private static final String PROTOCOL = "http";
	private static final String URL_PREFIX = PROTOCOL + "://" + SERVER_ENDPOINT
			+ "/";

	public static final String getLoginUrl() {
		return URL_PREFIX + "login";
	}

	public static final String getSignUpUrl() {
		return URL_PREFIX + "register";
	}
	
	public static final String getCreateFirmUrl() {
		return URL_PREFIX + "company";
	}

	public static final String getEditPwdUrl() {
		return URL_PREFIX + "editpwd";
	}

	public static final String getContactsListUrl() {
		return URL_PREFIX + "getemplist";
	}

	public static final String getWorkSigningUrl() {
		return URL_PREFIX + "attrecord";
	}

	public static final String getSignInRecordQueryUrl() {
		return URL_PREFIX + "attRecQuery";
	}

}
