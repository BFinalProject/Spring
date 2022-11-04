package com.mysite.sbb.user;

import lombok.Data;

@Data
public class AuthInfo {
	private String username;
	private String password;
	
	public AuthInfo(String username, String password) {
		this.username = username;
		this.password = password;
	}

	

	
}