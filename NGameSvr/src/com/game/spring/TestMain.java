package com.game.spring;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.game.common.constant.Loggers;
import com.game.db.dao.BaseDb;

public class TestMain {
	
	
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		DataSourceContextHolder.setDbType(DataSourceType.DS_ADMIN);  
		
		
		List<Map<String, Object>> list = 	ctx.getBean(BaseDb.class).selectAll("`tbl_ad_app`");
		
		System.out.println(list);
		
		Loggers.adminLogger.info(list.toString());
		
	
	
	}

}
