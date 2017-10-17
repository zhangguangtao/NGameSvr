package com.game.exception;

import java.lang.reflect.Method;


import org.springframework.aop.MethodBeforeAdvice;
/**
 * 前至方法执行前 做到处理
 * @author zgt
 *
 */
public class BeforeMethod implements MethodBeforeAdvice  {

	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		//System.out.println("方法执行前");
	}

}
