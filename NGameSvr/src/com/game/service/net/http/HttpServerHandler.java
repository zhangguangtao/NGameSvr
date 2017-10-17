package com.game.service.net.http;

import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.game.service.net.servlet.Request;
import com.game.service.net.servlet.Response;
import com.game.service.net.servlet.Servlet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;

public class HttpServerHandler extends ChannelInboundHandlerAdapter {

	private Servlet servlet;
	
    private HttpRequest currHttpRequest;
    
    private String command;
    
    private String params;
	
	public HttpServerHandler(Servlet servlet){
		this.servlet = servlet;
	}
	
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @SuppressWarnings("deprecation")
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) msg;
            this.currHttpRequest = httpRequest;
            if (httpRequest.getMethod().equals(HttpMethod.GET) || httpRequest.getMethod().equals(HttpMethod.POST) ){
            	//Pattern p = Pattern.compile(".*/command=(.*)\\?(.*)"); 
            	
            	String uri = httpRequest.getUri();
            	String [] group = uri.split("\\?");
            	command = group[0];
            	if (group.length>1){
            		params =  group[1];
            	}
            	//Pattern p = Pattern.compile(".*/(.*)\\?(.*)"); 
            	System.out.println(httpRequest.getUri());
            	/*Matcher m = p.matcher(httpRequest.getUri()); 
            	if (m.matches()){
            		command = m.group(1);
            		params = m.group(2);
            	}*/
            }
            else if (httpRequest.getMethod().equals(HttpMethod.POST)){
            	Pattern p = Pattern.compile("http://(.*):(.*)/command=(.*)");
            	System.out.println("post");
            	System.out.println(httpRequest.getUri());
            	Matcher m = p.matcher(httpRequest.getUri()); 
            	if (m.matches()){
            		command = m.group(3);
            	}
            }
        }
        
        if (msg instanceof HttpContent) {
        	HttpContent httpContent = (HttpContent) msg;
        	if (currHttpRequest.getMethod().equals(HttpMethod.POST)){
        		params = httpContent.content().copy().toString();
        	}
        	
            Request request = new Request(0, command, params, "0", ctx);
            Response response = servlet.service(request);
            
            ByteBuf responseBuf = Unpooled.wrappedBuffer(response.getContents());
            
            
            FullHttpResponse httpResponse = new DefaultFullHttpResponse(HTTP_1_1, OK, responseBuf);
            httpResponse.headers().set(CONTENT_TYPE, "text/plain");
            httpResponse.headers().set(CONTENT_LENGTH, httpResponse.content().readableBytes());

            boolean keepAlive = isKeepAlive(currHttpRequest);
            if (!keepAlive) {
                ctx.write(httpResponse).addListener(ChannelFutureListener.CLOSE);
            } else {
                httpResponse.headers().set(CONNECTION, Values.KEEP_ALIVE);
                ctx.write(httpResponse);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
