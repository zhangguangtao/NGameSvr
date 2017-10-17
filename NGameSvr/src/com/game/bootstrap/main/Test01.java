package com.game.bootstrap.main;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.game.bootstrap.GameServer;
import com.game.bootstrap.SvrConfig;
import com.game.bootstrap.test.TestException;
import com.game.common.constant.Loggers;
import com.game.common.util.BeanUtil;
import com.game.metaxml.CommMisc;
import com.game.metaxml.ProtoComm;
import com.game.metaxml.ProtoComm.Armdef;
import com.game.metaxml.ProtoComm.RoleData;
import com.game.service.net.LocalNetService;
import com.game.spring.DataSourceType;

import sun.net.www.protocol.file.FileURLConnection;

public class Test01 {
	
	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		
		PropertiesTest test = BeanUtil.getBean(PropertiesTest.class);
		System.out.println(test.getUrl());
		System.out.println(test.getUsername());
		
		TestException test2 = (TestException) BeanUtil.getBean(TestException.class);
		test2.say();
		
		SvrConfig svrConfig = BeanUtil.getBean(SvrConfig.class);
		
		System.out.println(svrConfig);
		
//		for(int i=0;i<5;i++){
//			TestException test = (TestException) BeanUtil.getBean(TestException.class);
//			System.out.println(test);
//			test.addN();
//			System.out.println(test.getN());
//			//System.out.println(test.n);
//		}
		
		
		
		
		
		
		
		
		/*RoleData roleData = CommMisc.loadRoleData(DataSourceType.DB_ACCOUNT, 100);
		System.out.println(roleData);
		roleData.setCreateTime((int)System.currentTimeMillis());
		roleData.setRoleName("上水");
		CommMisc.updateRoleData(DataSourceType.DB_ACCOUNT, roleData);
		
		List<Map<String, Object>> maps = CommMisc.getTable2Map(DataSourceType.DB_ACCOUNT, "roledata");
		System.out.println(maps);
		
		System.out.println("------------------------------------");
		
		List< ProtoComm.Armdef> list = CommMisc.table2Objs(DataSourceType.DB_ACCOUNT, "armdef", ProtoComm.Armdef.class);
		for (Armdef armdef : list) {
			byte [] bytes = CommMisc.obj2bytes(armdef);
			//System.out.println(bytes[0]);
			Armdef armdef2  =  (Armdef) CommMisc.bytes2obj(bytes);
			
			String xml = CommMisc.obj2xml(armdef2);
			System.out.println(xml);
			
			armdef2 =  CommMisc.xml2obj(xml, Armdef.class);
			
			if (armdef2.getRecastMoney() != null){
				int n = armdef2.getRecastMoney().getNum();
				System.out.println(n);
			}
		}
		*/
		
		
	}

}
