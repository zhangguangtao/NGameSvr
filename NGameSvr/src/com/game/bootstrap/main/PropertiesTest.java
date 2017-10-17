package com.game.bootstrap.main;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//@Service
public class PropertiesTest {
	
	//@Value("${account_username}")
	private String url;
	
	//@Value("${account_username}")
	private String username;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
