package com.game.exception;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
/**
 * 方法调用后执行
 * @author zgt
 *
 */
public class AfterMethod implements AfterReturningAdvice {

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		// TODO Auto-generated method stub
		//System.out.println("方法 调用后执行:"+returnValue);
	}

}
