package com.game.service.net.servlet.action;

import org.springframework.stereotype.Service;

import com.game.annotation.PassParameter;
import com.game.annotation.RequestMapping;
import com.game.service.net.servlet.Request;

@Service
@RequestMapping("test")
public class TestAction {
	
	@RequestMapping("login")
	public byte[] login(@PassParameter(name = "name")String name, @PassParameter(name = "password")String password, Request request){
		
		System.out.println(name);
		System.out.println(password);
		
		return "HelloWord".getBytes();
	}
	

}
