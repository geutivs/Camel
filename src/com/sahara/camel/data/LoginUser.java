package com.sahara.camel.data;

import com.sahara.camel.model.UserInfo;

public class LoginUser {
	
	private static String authToken;
	private static UserInfo user;
	
	public static String getAuthToken() {
		return authToken;
	}

	public static void setAuthToken(String authToken) {
		LoginUser.authToken = authToken;
	}

	public static void setUser(UserInfo user) {
		LoginUser.user = user;
	}

	public static UserInfo getUser() {
		return user;
	}

}
