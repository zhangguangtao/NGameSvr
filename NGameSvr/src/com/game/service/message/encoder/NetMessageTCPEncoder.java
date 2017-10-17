package com.game.service.message.encoder;

import java.util.List;

import com.game.service.message.CSMSG;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public class NetMessageTCPEncoder extends MessageToMessageEncoder<CSMSG> {
	
	@Override
	protected void encode(ChannelHandlerContext ctx, CSMSG msg, List<Object> out)
			throws Exception {
		//System.out.println(msg);
		ByteBuf byteBuf = Unpooled.buffer(256);
		byteBuf.writeShort(msg.getHead().getCmd());
		byteBuf.writeShort(msg.getHead().getHeadLen());
		byteBuf.writeShort(msg.getHead().getBodyLen());
		byteBuf.writeBytes(msg.getBody());
		out.add(byteBuf);
	}

}