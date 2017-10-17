package com.game.logic.net;

import com.game.metaxml.CommMisc;
import com.game.metaxml.ProtoComm.Player;
import com.game.metaxml.ProtoComm.RoleData;
import com.game.spring.DataSourceType;

import io.netty.channel.ChannelHandlerContext;

public class ZonePlayer {

	private Player pstPlayer;
	private ZONESVRENV pstEnv;
	private ChannelHandlerContext channelHandlerContext;
	public ZonePlayer(ZONESVRENV pstEnv,ChannelHandlerContext channelHandler) {
		this.pstEnv = pstEnv;
		this.channelHandlerContext = channelHandler;
	}
	
	/**
	 * 获取 Player
	 * @param pstEnv
	 * @param iUin
	 * @return
	 */
	public Player getPlayerByUin( int iUin){
		RoleData stRoleData = CommMisc.loadRoleData(DataSourceType.DB_ACCOUNT, iUin);
		Player player = new Player();
		player.setStRoleData(stRoleData);
		this.pstPlayer = player;
		return player;
	}
	
	
	
	
	
	
	
	
}
