package com.sahara.camel.network;

public class UrlLocations {

	private static final String SERVER_ENDPOINT = "120.25.146.221:8090";
	private static final String PROTOCOL = "http";
	private static final String ACTION_PREFIX = "WebApi";
	private static final String ACTION_SUFFIX = ".action";
	private static final String URL_PREFIX = PROTOCOL + "://" + SERVER_ENDPOINT
			+ "/" + ACTION_PREFIX + "/";
	
	public static final String getLoginUrl() {
		return URL_PREFIX + "userLogin" + ACTION_SUFFIX ;
	}
	
	public static final String getContactsListUrl() {
		return URL_PREFIX + "getemplist" + ACTION_SUFFIX;
	}
	
	public static final String getWorkSigningUrl() {
		return URL_PREFIX + "attrecord" + ACTION_SUFFIX;
	}

}
