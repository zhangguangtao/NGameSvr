package com.game.bootstrap.main;

import com.game.bootstrap.GameServer;

public class NetServiceMain {
	
	public static void main(String[] args)throws Exception {
		/*
		  1,连接服务器 6721 端口 获取 svrlist
		  2,连接认证服 6727 用户认证
		  3,zonesvr 9002 进入游戏 
		*/
		GameServer gameServer = new GameServer();
		gameServer.init();
	}

}
