package com.game.service.net.tcp.handler;

import org.springframework.context.annotation.EnableLoadTimeWeaving;

import com.game.common.util.BeanUtil;
import com.game.logic.net.AuthSvr;
import com.game.logic.net.DirSvr;
import com.game.logic.net.ZoneHttpSvr;
import com.game.logic.net.ZoneSvr;
import com.game.metaxml.CommMisc;
import com.game.metaxml.ProtoCs;
import com.game.metaxml.ProtoComm.Player;
import com.game.service.message.CSMSG;
import com.game.spring.DataSourceType;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class GameNetMessageTcpServerHandler extends SimpleChannelInboundHandler<Object> {
	
	private int Port;
	public GameNetMessageTcpServerHandler(int Port){
		this.Port = Port;
	}

	private ZoneSvr zoneSvr;
	private AuthSvr authSvr;
	private DirSvr  dirSvr;
	
	//通道未注册
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		//super.channelUnregistered(ctx);
	}
	
	//read
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		CSMSG pstMsg = (CSMSG) msg;
		if (pstMsg.getHead().getCmd() != 9){
			System.out.println(msg);
		}
		
		if(dirSvr!=null){
			dirSvr.dispatchMsg(pstMsg);
			
		}
		if(authSvr!=null){
			authSvr.dispatchMsg(pstMsg);
		}
		if(zoneSvr!=null){
			zoneSvr.dispatchMsg(pstMsg);
			System.out.println(zoneSvr);
		}
		
		//处理http的请求
		if (pstMsg.getHead().getCmd() == ProtoCs.MSG__TYPE__HTTP_REQ){
			ZoneHttpSvr.getInstance().Get(ctx, pstMsg);
		}
	}
	
	
	//你是不是在添加这个handler之前还添加了消息处理的handler，如lineBasedFrameDecoder或者FixLengthFramDecoder等，这样的话当消息没有到结束标志时，会进到complete方法里，到达消息的结束标志，才会调用read方法。
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		//super.channelReadComplete(ctx);
		ctx.flush();
	}
	
	//连接成功
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		//super.channelRegistered(ctx);
		
		//GameChannelManger.getInstance().getChannelMap().put(key, value);
		
		//ctx.channel().localAddress();
		
		System.out.println("连接成功");
		//ctx.channel().localAddress();
		if(Port == 6721 && dirSvr==null){
			dirSvr = BeanUtil.getBean(DirSvr.class);//new DirSvr(ctx);
			dirSvr.init(ctx);
		}
		if(Port == 6727 && authSvr==null){
			authSvr = BeanUtil.getBean(AuthSvr.class);//new AuthSvr(ctx);
			authSvr.init(ctx);
			
		}
		if(Port == 9002 && zoneSvr==null){
			zoneSvr = BeanUtil.getBean(ZoneSvr.class);//new ZoneSvr(ctx);
			zoneSvr.init(ctx);
		}
		
		
		
		//super.channelRegistered(ctx);
	}
	
	//用户事件触发 
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		//super.userEventTriggered(ctx, evt);
		//System.out.println("心跳");
		if (evt instanceof IdleStateEvent) {  
            IdleState state = ((IdleStateEvent) evt).state();  
            if (state == IdleState.READER_IDLE) {  
            	//10 秒 没有心跳 表示 停止
            	if(zoneSvr != null){
            		Player player = zoneSvr.getPstPlayer();
            		CommMisc.updateRoleData(DataSourceType.DB_ACCOUNT, player.getStRoleData());;
            	}
            	//与客户端 断开连接
            	ctx.close();
            	System.out.println("断开连接了");
            	//System.out.println("READER_IDLE");
            	//客户端没有发送心跳连接了
            	//System.out.println("客户端没有发送心跳连接了");
            	
               // throw new Exception("idle exception");  
            	//System.out.println(ctx.channel().id().asLongText());
                //System.out.println(ctx.channel().id().asShortText());
            }  
        } else {  
            super.userEventTriggered(ctx, evt);  
        }  
	}
	
	//异常
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		//super.exceptionCaught(ctx, cause);
		ctx.close();
		cause.printStackTrace();
	}

}
