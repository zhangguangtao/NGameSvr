package com.game.db.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import com.game.annotation.BinaryDb;
import com.game.metaxml.CommMisc;

/**
 * 映射 工具
 * @author zgt
 */
public class MapperUtils {
	
	/**
	 * 自动转化成 对象   缺点  最多 有两_ eg: Active_One1_ID  数字 不能超过 10 只能为 0 - 9 的数   不过 已经够用了  下个版本在扩展 
	 * @param clazz
	 * @param map
	 * @return
	 */
	public static <T> T mapperObj(Class<T> clazz,Map<String, Object> map){
		
		try {
			T obj = clazz.newInstance();
			for (Map.Entry<String, Object> entry : map.entrySet()) { 
			  	String key = entry.getKey();
			  	if (key.contains("_")) {
					int index = key.indexOf("_");
			  		String _indexChar = key.substring(index-1, index); 
			  		if (Character.isDigit(_indexChar.charAt(0))){
			  			int num = Integer.parseInt(_indexChar);
			  			String name = key.substring(0, index-1); 
			  			String subName = key.substring(index+1,key.length());
			  			//System.out.println(subName);
			  			Field f = clazz.getDeclaredField(name);
			  			f.setAccessible(true); 
			  			ParameterizedType pt = (ParameterizedType) f.getGenericType(); 
			  			Class ptClazz = (Class) pt.getActualTypeArguments()[0];
			  			
			  			parseList(obj, entry, num, subName, f, ptClazz);
			  			
			  		}else {
			  			String name = key.substring(0, index); 
			  			String subName = key.substring(index+1,key.length());
			  			Field f = clazz.getDeclaredField(name);
			  			f.setAccessible(true); 
			  			Object subObj1 = f.get(obj);
			  			if (subObj1 != null) {
			  				
			  				if (subName.contains("_")){
				  				
				  				autoFill(subName,f.getType(),entry,subObj1);
				  				
				  			}else {
				  				Field subf = subObj1.getClass().getDeclaredField(subName);
					  			subf.setAccessible(true);
					  			subf.set(subObj1, entry.getValue());
							}
						}else {
							Class subClass = f.getType();
				  			Object subObj = subClass.newInstance();
				  			f.set(obj, subObj);
				  			
				  			if (subName.contains("_")){
				  				
				  				autoFill(subName,subClass,entry,subObj);
				  				
				  			}else {
				  				Field subf = subClass.getDeclaredField(subName);
					  			subf.setAccessible(true);
					  			subf.set(subObj, entry.getValue());
							}
				  			
				  			
						}
					}
				}else {
					Field f = clazz.getDeclaredField(key);
					f.setAccessible(true); 
					Annotation BinaryAnnotation = f.getAnnotation(BinaryDb.class);
					if (BinaryAnnotation!=null) {  
						byte[] bytes = (byte[]) entry.getValue();
						Object object = CommMisc.bytes2obj(bytes);
						f.set(obj, object);
					}else {
						f.set(obj, entry.getValue());
					}
					
				}
			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	private static void autoFill(String key,Class clazz,Map.Entry<String, Object> entry,Object obj)throws Exception{
		int index = key.indexOf("_");
  		String _indexChar = key.substring(index-1, index); 
  		if (Character.isDigit(_indexChar.charAt(0))){
  			int num = Integer.parseInt(_indexChar);
  			String name = key.substring(0, index-1); 
  			String subName = key.substring(index+1,key.length());
  			//System.out.println(subName);
  			Field f = clazz.getDeclaredField(name);
  			f.setAccessible(true); 
  			ParameterizedType pt = (ParameterizedType) f.getGenericType(); 
  			Class ptClazz = (Class) pt.getActualTypeArguments()[0];
  			Object obj2 = ptClazz.newInstance();
  			parseList(obj, entry, num, subName, f, ptClazz);
  			
  		}
	}
	

	private static void parseList(Object obj, Map.Entry<String, Object> entry, int num, String subName, Field f,Class ptClazz) throws Exception {
		
		Object subObj2 = f.get(obj);
		Class subClass = f.getType();
		if (subObj2 == null){
			subObj2 = subClass.newInstance();
			f.set(obj, subObj2);
		}
		
		Method sizeMethod = subClass.getMethod("size");
		int size = (int) sizeMethod.invoke(subObj2,null);
		if (size > (num-1)) {
			Method getMethod = subClass.getMethod("get", int.class);
			
			Object ptObj = getMethod.invoke(subObj2, num-1);
			if (ptObj == null) {
				
				Field ptf = ptClazz.getDeclaredField(subName);
				ptf.setAccessible(true); 
				ptf.set(ptObj, entry.getValue());
				
				//System.out.println(subClass);
				Method addMethod = subClass.getMethod("add",Object.class);
				addMethod.invoke(subObj2, ptObj);
				
			}else {
				Field ptf = ptClazz.getDeclaredField(subName);
				ptf.setAccessible(true); 
				ptf.set(ptObj, entry.getValue());
			}
		}else {
			for(int i=0;i<num;i++){
				Object ptObj = ptClazz.newInstance();
				Method addMethod = subClass.getMethod("add",Object.class);
				addMethod.invoke(subObj2, ptObj);
			}
			
            Method getMethod = subClass.getMethod("get", int.class);
			
			Object ptObj = getMethod.invoke(subObj2, num-1);
			if (ptObj == null) {
				
				Field ptf = ptClazz.getDeclaredField(subName);
				ptf.setAccessible(true); 
				ptf.set(ptObj, entry.getValue());
				
				//System.out.println(subClass);
				Method addMethod = subClass.getMethod("add",Object.class);
				addMethod.invoke(subObj2, ptObj);
				
			}else {
				Field ptf = ptClazz.getDeclaredField(subName);
				ptf.setAccessible(true); 
				ptf.set(ptObj, entry.getValue());
			}
		}
	}

}
