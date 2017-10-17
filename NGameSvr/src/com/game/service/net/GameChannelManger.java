package com.game.service.net;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;

public class GameChannelManger {
	
	private static GameChannelManger gameChannelManger;
	
	public static GameChannelManger getInstance(){
		if (gameChannelManger == null) {
			gameChannelManger = new GameChannelManger();
		}
		return gameChannelManger;
	}
	
	private Map<Integer,ChannelHandlerContext> channelMap = new HashMap<>();
	
	private GameChannelManger(){
		
	}

	public Map<Integer, ChannelHandlerContext> getChannelMap() {
		return channelMap;
	}

	
	
	public void putChannel(Integer uin,ChannelHandlerContext channelHandler){
		if (!channelMap.containsKey(uin)) {
			channelMap.put(uin, channelHandler);
		}else {
			channelMap.get(uin).close();
			channelMap.remove(uin);
			channelMap.put(uin, channelHandler);
		}
	}
	
	public void removeChannel(Integer uin){
		if (channelMap.containsKey(uin)) {
			channelMap.remove(uin);
		}
	}
	
	
	
	
	
	
	

}
