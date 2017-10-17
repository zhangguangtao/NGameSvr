package com.game.bootstrap.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.game.bootstrap.test.AopTest;
import com.game.common.util.BeanUtil;

public class TestAop {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		new ClassPathXmlApplicationContext("beans.xml");
		
		AopTest aopTest = BeanUtil.getBean(AopTest.class);
		aopTest.add(1, 2);
		
		
	}
	
}
