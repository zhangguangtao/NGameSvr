package com.game.logic.net;

import com.game.service.message.CSMSG;

import io.netty.channel.ChannelHandlerContext;

public class ZoneHttpSvr {
	
	private static  ZoneHttpSvr httpSvr;
	
	public static ZoneHttpSvr getInstance(){
		if (httpSvr==null) {
			httpSvr = new ZoneHttpSvr();
		}
		return httpSvr;
	}
	
	private  ZoneHttpSvr() {
	}
	
	
	public void Get(ChannelHandlerContext ctx,CSMSG pstMsg){
		
		
	}
	
	

}
