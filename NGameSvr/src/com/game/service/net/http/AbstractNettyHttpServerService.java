package com.game.service.net.http;


import org.slf4j.Logger;

import com.game.common.constant.Loggers;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class AbstractNettyHttpServerService {

    private Logger logger = Loggers.serverLogger;
	private ChannelInitializer channelInitializer;
	private ChannelFuture serverChannelFuture;
	
	private int httServerPort;
	
	public AbstractNettyHttpServerService(ChannelInitializer channelInitializer,int httServerPort) {
		this.httServerPort = httServerPort;
		this.channelInitializer = channelInitializer;
	}
	
	 public boolean startService() throws Exception{
		 boolean serviceFlag  = true;
		 EventLoopGroup bossGroup =  new NioEventLoopGroup();
		 EventLoopGroup workerGroup = new NioEventLoopGroup();
	        try{
	            ServerBootstrap serverBootstrap = new ServerBootstrap();
	            serverBootstrap = serverBootstrap.group(bossGroup, workerGroup);
	            serverBootstrap.channel(NioServerSocketChannel.class)
	                    .option(ChannelOption.SO_BACKLOG, 1024)
	                    .childOption(ChannelOption.SO_REUSEADDR, true) //重用地址
	                    .childOption(ChannelOption.SO_RCVBUF, 65536)
	                    .childOption(ChannelOption.SO_SNDBUF, 65536)
	                    .childOption(ChannelOption.TCP_NODELAY, true)
	                    .childOption(ChannelOption.SO_KEEPALIVE, true)
	                    //.childOption(ChannelOption.ALLOCATOR, new PooledByteBufAllocator(false))  // heap buf 's better
	                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)//关键是这句
	                    .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, Integer.valueOf(1000))
	                    .handler(new LoggingHandler(LogLevel.INFO))
	                    .childHandler(channelInitializer);
//	            bootstrap.setOption("child.tcpNoDelay", Boolean.valueOf(true));
//	            bootstrap.setOption("child.keepAlive", Boolean.valueOf(true));
//	            bootstrap.setOption("child.reuseAddress", Boolean.valueOf(true));
//	            bootstrap.setOption("child.connectTimeoutMillis", Integer.valueOf(100));
	            serverChannelFuture = serverBootstrap.bind(httServerPort).sync();

	            serverChannelFuture.channel().closeFuture().addListener(ChannelFutureListener.CLOSE);
	        }catch (Exception e) {
	            logger.error(e.toString(), e);
	            serviceFlag = false;
	        }
		 
		return serviceFlag;
	 }

}
