package com.game.service.net.servlet.action;

import org.springframework.stereotype.Service;

import com.game.annotation.PassParameter;
import com.game.annotation.RequestMapping;
import com.game.service.net.servlet.Request;

@Service
@RequestMapping("loginTest")
public class LoginTest {

	@RequestMapping("test")
	public byte[] test(Request request,@PassParameter(name="name")String name){
		System.out.println(name);
		return "6666".getBytes();
	}
	
}
