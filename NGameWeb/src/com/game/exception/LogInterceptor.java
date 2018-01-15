package com.game.exception;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;

import com.game.common.constant.Loggers;
/**
 * log 日志记录
 * @author zgt
 *
 */
public class LogInterceptor implements MethodInterceptor  {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		//System.out.println(invocation);
		//Logger loger = Loggers.errorLogger;
		  
       // loger.error("--Log By Andy Chan -----------------------------------------------------------------------------");  
       // loger.info(invocation.getMethod() + ":BEGIN!--(Andy ChanLOG)");// 方法前的操作  
        Object obj = invocation.proceed();// 执行需要Log的方法  
      //  loger.info(invocation.getMethod() + ":END!--(Andy ChanLOG)");// 方法后的操作  
       // loger.info("-------------------------------------------------------------------------------------------------");  
  
        return obj; 
	}

}
