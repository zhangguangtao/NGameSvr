package com.game.service.net;

import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;

import com.game.bootstrap.SvrConfigParse;
import com.game.common.constant.GlobalConstants;
import com.game.common.constant.Loggers;
import com.game.service.net.tcp.GameNetProtoMessageTcpServerChannelInitializer;
import com.game.service.net.tcp.GameNettyTcpServerService;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;

public class LocalNetService {

	
	private Logger serverLogger = Loggers.serverLogger;

    /**
     * tcp服务
     */
    private GameNettyTcpServerService gameNettyTcpServerService;
    
    private ChannelInitializer<NioSocketChannel> nettyTcpChannelInitializer;
	
	public String getId() {
		return null;
	}

	public void startup() throws Exception {
		
	}

	public void shutdown() throws Exception {
		
	}
	
	 public void initChannelInitializer(){
	        //nettyTcpChannelInitializer = new GameNetProtoMessageTcpServerChannelInitializer(Port);
	 }
	
	public void initNetService() throws Exception {
		
		
		
		int DirPort = SvrConfigParse.getSvrMap().get("DirSvr").getPort();
		startService(DirPort);
		
		int AuthPort = SvrConfigParse.getSvrMap().get("AuthSvr").getPort();
		startService(AuthPort);
		
		int ZonePort = SvrConfigParse.getSvrMap().get("ZoneSvr").getPort();
		startService(ZonePort);
		
		

//		gameNettyTcpServerService = new GameNettyTcpServerService("777", 9999
//              , GlobalConstants.Thread.NET_TCP_BOSS, GlobalConstants.Thread.NET_TCP_WORKER, nettyTcpChannelInitializer);
//		boolean startUpFlag = gameNettyTcpServerService.startService();
//		System.out.println(startUpFlag);
	
	} 
	
	
	public void startService(int Port) throws Exception {
		nettyTcpChannelInitializer = new GameNetProtoMessageTcpServerChannelInitializer(Port);
		gameNettyTcpServerService = new GameNettyTcpServerService("777", Port
              , GlobalConstants.Thread.NET_TCP_BOSS, 
              GlobalConstants.Thread.NET_TCP_WORKER, 
              nettyTcpChannelInitializer);//new GameNetProtoMessageTcpServerChannelInitializer()
		boolean startUpFlag = gameNettyTcpServerService.startService();
		System.out.println(startUpFlag);
		
	}
	

}
