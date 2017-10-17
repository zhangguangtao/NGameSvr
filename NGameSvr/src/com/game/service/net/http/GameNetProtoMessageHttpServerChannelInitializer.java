package com.game.service.net.http;

import com.game.service.net.servlet.Servlet;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class GameNetProtoMessageHttpServerChannelInitializer extends ChannelInitializer<SocketChannel>{

	//final Servlet servlet = new Servlet();
	
	private  Servlet servlet;
	public GameNetProtoMessageHttpServerChannelInitializer( Servlet servlet) {
		this.servlet = servlet;
	}
	
	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		 ChannelPipeline channelPipLine = socketChannel.pipeline();
	        int maxLength = Integer.MAX_VALUE;
//	        channelPipLine.addLast("frame", new LengthFieldBasedFrameDecoder(maxLength, 2, 4, 0, 0));
	        channelPipLine.addLast("encoder", new HttpResponseEncoder());
	        channelPipLine.addLast("trunk", new HttpObjectAggregator(1048576));
	        channelPipLine.addLast("decoder", new HttpRequestDecoder());
	        
	        channelPipLine.addLast(new HttpServerHandler(this.servlet));
	   
	}

}
