package com.game.bootstrap;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.game.logic.net.ZONESVRENV;
import com.game.logic.net.ZoneMail;
import com.game.service.net.LocalNetService;
import com.game.service.net.http.AbstractNettyHttpServerService;
import com.game.service.net.http.GameNetProtoMessageHttpServerChannelInitializer;
import com.game.service.net.servlet.Servlet;

public class GameServer {

	
	 /**
     * 初始化各种资源和服务
     *
     * @throws Exception
     */
    public void init() throws Exception {
        initSpring();
        this.loadZoneSvrRes();
        this.initServer();
        this.initHttp();
    }
    
    /**
     * http 初始化
     */
    private void initHttp() {
    	Servlet servlet = new Servlet();
		servlet.init();
		GameNetProtoMessageHttpServerChannelInitializer channelInitializer = new GameNetProtoMessageHttpServerChannelInitializer(servlet);
		AbstractNettyHttpServerService serverService = new AbstractNettyHttpServerService(channelInitializer, 880);
		try {
			serverService.startService();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
     * 加载资源
     */
    private void loadZoneSvrRes() {
    	ZONESVRENV.getInstance();
	}

	private void  initConfig() {
		
    	
	}
	
	 public void initSpring()throws Exception{
		 ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
	 }
	 
	 private void initServer()throws Exception {
		 tick();
		 start(); 
	 }
	 
	 //启动计时器
	 private void tick(){
		 Timer timer = new Timer();
		 TimerTask task = new TimerTask() {
			@Override
			public void run() {
				ZoneMail.getInstance().mailDelByTime();
			}
		};
		timer.schedule(task, 1000,1000);
	 }
	 
	 
	 
	 
	 /**
	 * 启动服务器
	 *
	 * @throws Exception
	 */
	public void start() throws Exception {
	
		LocalNetService localNetService = new LocalNetService();
		localNetService.initChannelInitializer();
		localNetService.initNetService();
	   
	}

}
