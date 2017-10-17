package com.game.logic.net;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.game.service.message.CSMSG;
import com.game.service.message.CSMSGHEAD;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageLite;

import io.netty.channel.ChannelHandlerContext;

public abstract class BaseSvr {
	
	protected ChannelHandlerContext channelHandler;
	
	protected Map<Short, String> map = new HashMap<>();
	
	public BaseSvr(){
		
	}
	
	public void init(ChannelHandlerContext channelHandler){
		this.channelHandler = channelHandler;
		this.initCmdMap();
	}
	
	/**
	 * 注册 请求 指令
	 */
	public abstract void initCmdMap();
	
	public abstract String debugInfo();
	
	
	public void dispatchMsg(CSMSG stMsg)throws Exception{
		String str = map.get(stMsg.getHead().getCmd());
		System.err.println(debugInfo()+"   Cmd指令:"+stMsg.getHead().getCmd()+"     回调函数:"+str);
		if (str != null) {
			Method method = this.getClass().getMethod(str, stMsg.getClass());
			method.invoke(this, stMsg);
		}

	}
	
	
	/**
	 * 解析 proto
	 * @param generatedMessage
	 * @param stMsg
	 * @return
	 */
	public Message parserProto(CSMSG stMsg,GeneratedMessage generatedMessage){
		Message messageType = null; 
		try {
			messageType = generatedMessage.getParserForType().parseFrom(stMsg.getBody());
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		return messageType;
	}
	
	/**
	 * 发送 封装 数据
	 * @param cmd
	 * @param builder
	 */
	public void svrMsgSend(short cmd,MessageLite.Builder builder){
		byte [] data = builder.build().toByteArray();
		CSMSGHEAD head = new CSMSGHEAD(cmd,(short) data.length);
		CSMSG stMsg = new CSMSG();
		stMsg.setHead(head);
		stMsg.setBody(data);
		this.channelHandler.writeAndFlush(stMsg);
	}

}
