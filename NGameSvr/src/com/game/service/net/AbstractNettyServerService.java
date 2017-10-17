package com.game.service.net;


import java.net.InetSocketAddress;

public abstract class AbstractNettyServerService  {

    protected int serverPort;
    protected InetSocketAddress serverAddress;


    public AbstractNettyServerService(String serviceId, int serverPort) {
        this.serverPort = serverPort;
        this.serverAddress = new InetSocketAddress(serverPort);
    }
}
