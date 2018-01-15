package com.game.spring;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.game.common.constant.Loggers;
import com.game.db.dao.BaseDb;

public class TestMain {
	
	private String name;
	private int age;
	
	
	
	
	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public int getAge() {
		return age;
	}




	public void setAge(int age) {
		this.age = age;
	}




	public static void main(String[] args)throws Exception {
		@SuppressWarnings("resource")
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		DataSourceContextHolder.setDbType(DataSourceType.DS_ADMIN);  
		
		
		List<Map<String, Object>> list = 	BeanUtil.getBean(BaseDb.class).selectAll("`tbl_ad_app`");
		String json = JSONObject.toJSONString(list);
		System.out.println(json);
		System.out.println("<<<<<<<<<<<<");
		System.out.println(list);
		
		Loggers.adminLogger.info(list.toString());
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		TestMain obj = new TestMain();
		//obj.setAge(2);
		//obj.setName("zhang");
		Class clazz = obj.getClass();
	       String table = clazz.getSimpleName();
	       Field field[] = clazz.getDeclaredFields();
	       StringBuilder column = new StringBuilder();
	       StringBuilder columnData = new StringBuilder();
	       for (int i=0;i<field.length;i++){
	           Field f = field[i];
	           f.setAccessible(true);
	           String key = f.getName();
	           Object value =f.get(obj);
	           column.append(key+",");
	           columnData.append(value+",");
	       }
	       column.deleteCharAt(column.length()-1);
	       columnData.deleteCharAt(columnData.length()-1);
		
		System.out.println(column);
		System.out.println(columnData);
		
	
	
	}

}
