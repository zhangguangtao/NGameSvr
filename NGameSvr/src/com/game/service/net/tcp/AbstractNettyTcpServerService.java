package com.game.service.net.tcp;

import org.slf4j.Logger;

import com.game.common.constant.Loggers;
import com.game.service.net.AbstractNettyServerService;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class AbstractNettyTcpServerService extends AbstractNettyServerService{

	private Logger logger = Loggers.serverLogger;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

   
    private ChannelInitializer<NioSocketChannel> channelInitializer;

    private ChannelFuture serverChannelFuture;
    public AbstractNettyTcpServerService(String serviceId, int serverPort, String bossTreadName, String workThreadName,ChannelInitializer<NioSocketChannel> channelInitializer) {
    	super(serviceId, serverPort);
        this.channelInitializer = channelInitializer;
    }

    
    public boolean startService() throws Exception{
        boolean serviceFlag  = true;
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap = serverBootstrap.group(bossGroup, workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_REUSEADDR, true) //重用地址ַ
                    .childOption(ChannelOption.SO_RCVBUF, 65536)
                    .childOption(ChannelOption.SO_SNDBUF, 65536)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                   // .childOption(ChannelOption.ALLOCATOR, new PooledByteBufAllocator(false))  // heap buf 's better
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(channelInitializer);
            serverChannelFuture = serverBootstrap.bind(serverPort).sync();
          //TODO这里会阻塞main线程，暂时先注释掉
          //serverChannelFuture.channel().closeFuture().sync();
            serverChannelFuture.channel().closeFuture().addListener(ChannelFutureListener.CLOSE);
        }catch (Exception e) {
            logger.error(e.toString(), e);
            serviceFlag = false;
        }
        return serviceFlag;
    }

    public boolean stopService() throws Exception{
        if(bossGroup != null){
            bossGroup.shutdownGracefully();
        }
        if(workerGroup != null){
            workerGroup.shutdownGracefully();
        }
        return true;
    }

    public void finish() throws InterruptedException {
     //   serverChannelFuture.channel().closeFuture().sync();
    }
	
}
