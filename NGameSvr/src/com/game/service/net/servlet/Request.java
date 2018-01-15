package com.game.service.net.servlet;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;


/**
 * an input to an action invocation  
 * 
 * @author needmorecode
 *
 */
public class Request {
	
	private int id;
	
	private String protocol;
	
	private String command;
	
	private Map<String, String> paramMap;
	
	private String params;
	
	private String sessionId;
	
	private ChannelHandlerContext ctx;
	
	private HttpRequest httpRequest;
	
	private HttpContent httpContent;
	
	public Request(){
		
	}
	
	public Request(int requestId, String command, String params, String sessionId, ChannelHandlerContext ctx){
		this.setId(requestId);
		this.command = command;
		this.params = params;
		this.paramMap = new HashMap<String, String>();
		if (params!=null&&params.length() > 0){
			try {
				for(String param : params.split("&")){
					String[] paramArray = param.split("=");
					paramMap.put(paramArray[0], paramArray[1]);
				}
			} catch (Exception e) {
				System.out.println("url 参数错误  "+params);
			}
		}
		this.sessionId = sessionId;
		this.ctx = ctx;
	}



	public HttpContent getHttpContent() {
		return httpContent;
	}

	public void setHttpContent(HttpContent httpContent) {
		this.httpContent = httpContent;
	}

	public HttpRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	
	

}
