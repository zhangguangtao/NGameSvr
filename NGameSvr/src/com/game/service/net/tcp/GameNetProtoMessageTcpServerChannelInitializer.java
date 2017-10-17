package com.game.service.net.tcp;

import java.util.concurrent.TimeUnit;

import com.game.service.message.decoder.NetMessageTCPDecoder;
import com.game.service.message.encoder.NetMessageTCPEncoder;
import com.game.service.net.tcp.handler.GameNetMessageTcpServerHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class GameNetProtoMessageTcpServerChannelInitializer extends ChannelInitializer<NioSocketChannel>{

	private int Port;
	public GameNetProtoMessageTcpServerChannelInitializer(int Port){
		this.Port = Port;
	}
	
	@Override
	protected void initChannel(NioSocketChannel ch) throws Exception {
		ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
		// head 4 lenght 4 data
		//ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 4, 4,0,0));
		ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 4, 2,0,0));
    	
		ch.pipeline().addLast(new NetMessageTCPDecoder());
    	ch.pipeline().addLast(new NetMessageTCPEncoder());
    	
    	ch.pipeline().addLast(new IdleStateHandler(10, 0, 0, TimeUnit.SECONDS));  
        ch.pipeline().addLast(new GameNetMessageTcpServerHandler(Port));
		
        //System.out.println(ch.id().asLongText());
        //System.out.println(ch.id().asShortText());
	}

}
