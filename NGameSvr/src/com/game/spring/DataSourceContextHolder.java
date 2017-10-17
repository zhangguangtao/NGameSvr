package com.game.spring;

public class DataSourceContextHolder {
	
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
	  
    public static void setDbType(DataSourceType dbType) {  
           contextHolder.set(dbType.getDatabase());  
    }  
 
    public static String getDbType() {  
           return ((String) contextHolder.get());  
    }  
 
    public static void clearDbType() {  
           contextHolder.remove();  
    }  
	

}
