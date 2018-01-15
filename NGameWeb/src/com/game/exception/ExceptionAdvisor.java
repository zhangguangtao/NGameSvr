package com.game.exception;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;

import com.game.common.constant.Loggers;
/**
 * 异常记录 
 * @author zgt
 *
 */
public class ExceptionAdvisor implements ThrowsAdvice  {
	
	
	public void afterThrowing(Method method, Object[] args, Object target,  
            Exception ex) throws Throwable  {
		System.out.println(method);
		System.out.println(target);
		System.err.println("截获异常 : "+ex);
		System.out.println("afterThrowing");
		Loggers.errorLogger.info("截获异常 : " + exception(ex));
	}
	
	private static String exception(Throwable t){  
	    if(t == null)  
	        return null;  
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	    try{  
	        t.printStackTrace(new PrintStream(baos));  
	    }
	    finally{  
	        try {
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
	    }  
	    return baos.toString();  
	}
	
	
	

}
