package com.game.metaxml;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.game.common.util.BeanUtil;
import com.game.db.dao.AccountDb;
import com.game.db.dao.BaseDb;
import com.game.metaxml.ProtoComm.RoleData;
import com.game.metaxml.ResdbMeta.CardDef;
import com.game.spring.DataSourceContextHolder;
import com.game.spring.DataSourceType;

/**
 * 数据库 中的 数据转xml 
 * @author zgt
 *
 * 修改 RoleData 调试 用 
 */
public class DbMemXml {
	
	/**
	 * 
	 * @param cmd 0 转成 xml 
	 * @param Uin 1 xml 写入 数据库中
	 */
	public DbMemXml(int cmd,int Uin){
		if (cmd == 0) {
			roleDataToXml(Uin);
		}else if (cmd == 1) {
			xmlToRoleDataDb(Uin);
		}
	}
	
	private void xmlToRoleDataDb(int Uin) {
		String fileName = Uin+".xml";
		File file = new File(fileName);
		String xml = CommMisc.readStr2File(file);
		RoleData roleData = CommMisc.xml2obj(xml, RoleData.class);
		CommMisc.updateRoleData(DataSourceType.DB_ACCOUNT, roleData);
		
	}

	private void roleDataToXml(int Uin){
		RoleData roleData = CommMisc.loadRoleData(DataSourceType.DB_ACCOUNT, Uin);
		String xml = CommMisc.obj2xml(roleData);
		String fileName = Uin+".xml";
		File file = new File(fileName);
		CommMisc.writeStr2File(xml, file);
	}
	
	
	public static void main(String[] args)throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		
		//DbMemXml dbMemXml = new DbMemXml(0, 100);
		
		Account account = new Account();
		//account.setUin(200);
		account.setRoleName("hello word");
		account.setPassword("999999");
		
		//CommMisc.updateDb(DataSourceType.SOURCE_MOP, "account", account, " Uin = 200 ");
		
		/*String tables[] = new String[]{"armdef"};
		String xml = CommMisc.table2XmlStruct(DataSourceType.DS_RESDB, tables);
		System.out.println(xml);*/
		
		List<CardDef> list =  CommMisc.table2Objs(DataSourceType.DS_RESDB, "carddef", CardDef.class);
		HashMap<Integer, CardDef> map = CommMisc.list2Map(list, int.class, "ID");
		System.out.println(map);
//		for (CardDef cardDef : list) {
//			System.out.println(cardDef.getActive().getOne().get(0).getID());
//			System.out.println(cardDef.getActive().getOne().get(0).getLvl());
//			System.out.println(cardDef.getActive().getOne().get(0).getUse());
//		}
		
		//RoleData roleData = CommMisc.loadRoleData(DataSourceType.DB_ACCOUNT, 100);
		//roleData.setUin(500);
		//CommMisc.insertBinary(DataSourceType.DB_ACCOUNT, "roledata", roleData);
		
		DataSourceContextHolder.setDbType(DataSourceType.DB_ACCOUNT);
		//AccountDb accountDb = BeanUtil.getBean(AccountDb.class);
		//accountDb.insertAccount(account);
		//DataSourceType database, String table,Object obj,Class<BaseDb> baseDbClazz
		CommMisc.insertDb(DataSourceType.DB_ACCOUNT, "account", account );
		
		
		System.out.println(account.getUin());
	}
	
}
