package com.game.bootstrap.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.game.service.net.http.AbstractNettyHttpServerService;
import com.game.service.net.http.GameNetProtoMessageHttpServerChannelInitializer;
import com.game.service.net.servlet.Servlet;

public class TestHttp {

	@SuppressWarnings("resource")
	public static void main(String[] args)throws Exception {
		//http://localhost:880/command=test!login?name=ddd&password=123456
		//http://localhost:880/test/login?name=ddd&password=123456
		new ClassPathXmlApplicationContext("beans.xml");
		Servlet servlet = new Servlet();
		servlet.init();
		GameNetProtoMessageHttpServerChannelInitializer channelInitializer = new GameNetProtoMessageHttpServerChannelInitializer(servlet);
		
		AbstractNettyHttpServerService serverService = new AbstractNettyHttpServerService(channelInitializer, 880);
	
		serverService.startService();
	}
	
}
