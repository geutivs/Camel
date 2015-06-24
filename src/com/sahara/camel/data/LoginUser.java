package com.sahara.camel.data;

import com.sahara.camel.model.UserInfo;

public class LoginUser {
	
	private static UserInfo user;
	
	public static UserInfo getUser() {
		return user;
	}
	
	public static void updateUser(UserInfo u) {
		user = u;
	}
}
