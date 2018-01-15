package com.game.service.net.servlet.action;

import org.springframework.stereotype.Service;

import com.game.annotation.PassParameter;
import com.game.annotation.RequestMapping;
import com.game.service.net.servlet.Request;

import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;

@Service
@RequestMapping("test")
public class TestAction {
	
	private static final HttpDataFactory factory =  
            new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE); // Disk if size exceed  
	
	private HttpPostRequestDecoder decoder; 
	
	private boolean readingChunks;  
	
	@RequestMapping("login")
	public byte[] login(@PassParameter(name = "name")String name, @PassParameter(name = "password")String password, Request request){
		
		System.out.println(name);
		System.out.println(password);
		
		decoder = new HttpPostRequestDecoder(factory, request.getHttpRequest());  
		readingChunks = HttpHeaders.isTransferEncodingChunked(request.getHttpRequest()); 
		
		
		HttpContent chunk = request.getHttpContent();
		  decoder.offer(chunk);  
		
		return "HelloWord".getBytes();
	}
	

}
