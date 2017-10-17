package com.game.service.net.http;

import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpContent;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.stream.ChunkedInput;
import io.netty.util.CharsetUtil;

// TODO
public class CarmeloChunkedInput implements ChunkedInput<HttpContent>{
	
	LinkedBlockingQueue<HttpContent> queue = new LinkedBlockingQueue<HttpContent>();
	
	private int id = 0;

	public boolean isEndOfInput() throws Exception {
		//return queue.isEmpty();
		return false;
	}

	public void close() throws Exception {
	}

	public HttpContent readChunk(ChannelHandlerContext ctx) throws Exception {
		return queue.poll();
	}
	
	public void writeChunk() {
		// TODO
		ByteBuf buffer = Unpooled.copiedBuffer("xxx " + id + "\n" + new Date() + "\n", CharsetUtil.UTF_8);
		id++;
		queue.offer(new DefaultHttpContent(buffer));
	}

	@Override
	public HttpContent readChunk(ByteBufAllocator allocator) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long length() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long progress() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
