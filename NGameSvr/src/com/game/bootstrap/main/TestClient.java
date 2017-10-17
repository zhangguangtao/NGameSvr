package com.game.bootstrap.main;

import com.game.logic.net.Cs.CsHeartReq;
import com.game.metaxml.Cspb;
import com.game.service.message.CSMSG;
import com.game.service.message.CSMSGHEAD;
import com.game.service.message.decoder.NetMessageTCPDecoder;
import com.game.service.message.encoder.NetMessageTCPEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TestClient {
	
	private SocketChannel channel;
	 public void connect(int port, String host) throws Exception {
	        // 配置客户端NIO线程组
	        EventLoopGroup group = new NioEventLoopGroup();
	        try {
	            Bootstrap b = new Bootstrap();
	            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
	                    .handler(new ChannelInitializer<SocketChannel>() {
	                        @Override
	                        public void initChannel(SocketChannel ch) throws Exception {
	                        	ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
	                        	ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 4, 2,0,0));
	                        	ch.pipeline().addLast(new NetMessageTCPDecoder());
	                        	ch.pipeline().addLast(new NetMessageTCPEncoder());
	                        	
	                        	
	                            ch.pipeline().addLast(new ProtoBufClientHandler());
	                            
	                            channel = ch;
	                            
	                           
	                        }
	                    });

	            // 发起异步连接操作
	            ChannelFuture f = b.connect(host, port).sync();
	            for(int i=1;i<5;i++){
	            	 send();
	            	 Thread.sleep(2000);
	            }
	           
	            // 当代客户端链路关闭
	            f.channel().closeFuture().sync();
	        } finally {
	            // 优雅退出，释放NIO线程组
	            group.shutdownGracefully();
	        }
	    }
	 
	 
	 public void send() {
		   /*LoginReq.Builder req = LoginReq.newBuilder();
		   req.setAccName("123456");
		   req.setUin(4);
		   req.setEnc("88888888888");
		 
			byte [] body = req.build().toByteArray();
			CSMSG csmsg = new CSMSG();
			csmsg.setHead(new CSMSGHEAD(Cspb.MSG__TYPE__LOGIN_REQ,body.length));
			csmsg.setBody(body);
			channel.writeAndFlush(csmsg);
			*/
	}
	 
	

	    /**
	     * @param args
	     * @throws Exception
	     */
	    public static void main(String[] args) throws Exception {
	        int port = 9999;
	        if (args != null && args.length > 0) {
	            try {
	                port = Integer.valueOf(args[0]);
	            } catch (NumberFormatException e) {
	                // 采用默认值
	            }
	        }
	        new TestClient().connect(port, "127.0.0.1");
	    }
	    
	    public static class ProtoBufClientHandler extends SimpleChannelInboundHandler<CSMSG>{

			@Override
			protected void channelRead0(ChannelHandlerContext ctx, CSMSG msg) throws Exception {
				System.out.println("客户端:");
				System.out.println(msg);
				
			}
			
			@Override
			public void channelActive(ChannelHandlerContext ctx) throws Exception {
				
				   CsHeartReq.Builder heartReq = CsHeartReq.newBuilder();
				   
				   heartReq.setSvrTime(1);
				   
				
					byte [] body = heartReq.build().toByteArray();
					System.out.println(body.length);
					CSMSG csmsg = new CSMSG();
					csmsg.setHead(new CSMSGHEAD(Cspb.MSG__TYPE__HEART_REQ,(short) body.length));
					csmsg.setBody(body);
					ctx.writeAndFlush(csmsg);
			}
			
			
			 public void  send() {
				 
			}
	    	
	    }

}
