package com.game.logic.net;

import java.util.HashMap;
import java.util.List;

import com.game.metaxml.CommMisc;
import com.game.metaxml.ResdbMeta.CardDef;
import com.game.spring.DataSourceType;

import io.netty.channel.ChannelHandlerContext;

/**
 * 区服 环境 配置
 * @author zgt
 *
 */
public class ZONESVRENV {
	
	private static ZONESVRENV pstEnv;
	public static ZONESVRENV getInstance(){
		if (pstEnv==null) {
			pstEnv = new ZONESVRENV();
		}
		return pstEnv;
	}
	
	private HashMap<Integer, CardDef> CardDef;
	
	/**
	 * 全区的uin
	 */
	private HashMap<Integer, ChannelHandlerContext> Uins;
	
	private ZONESVRENV(){
		Uins = new HashMap<>();
		init();
	}
	
	
	
	public HashMap<Integer, ChannelHandlerContext> getUins() {
		return Uins;
	}



	/**
	 * 初始化 数据
	 */
	private void init(){
		List<CardDef> list = CommMisc.table2Objs(DataSourceType.DS_RESDB, "carddef", CardDef.class);
		CardDef = CommMisc.list2Map(list, int.class, "ID");
		
		
	}

	public HashMap<Integer, CardDef> getCardDef() {
		return CardDef;
	}
	
	public long getSvrTime(){
		return System.currentTimeMillis();
	}

	
}
