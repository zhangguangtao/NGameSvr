package com.game.logic.net;

import java.util.HashMap;
import java.util.List;

import com.game.metaxml.CommMisc;
import com.game.metaxml.ResdbMeta.CardDef;
import com.game.spring.DataSourceType;

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
	private ZONESVRENV(){
		init();
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

	
}
