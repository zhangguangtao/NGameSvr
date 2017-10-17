package com.game.service.message.decoder;

import java.util.List;

import com.game.service.message.CSMSG;
import com.game.service.message.CSMSGHEAD;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class NetMessageTCPDecoder extends MessageToMessageDecoder<ByteBuf>{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        
		short Cmd = msg.readShort();
		short HeadLen = msg.readShort();
		short BodyLen = msg.readShort();
		
		byte [] body = new byte[msg.readableBytes()];
		msg.readBytes(body); //解析 内容
		
		/*
		//System.out.println("****************");
		int cmd = msg.readInt(); //解析命令
		//System.out.println(cmd);
		int bodyLen = msg.readInt(); //解析长度
		//System.out.println(bodyLen);
		byte [] body = new byte[msg.readableBytes()];
		msg.readBytes(body); //解析 内容
		*/
		
		
		//构造数据
		CSMSG pstMsg = new CSMSG();
		CSMSGHEAD head = new CSMSGHEAD(Cmd,BodyLen);
		head.setHeadLen(HeadLen);
		pstMsg.setHead(head);
		pstMsg.setBody(body);
		out.add(pstMsg);
	}
	
}
