package com.game.metaxml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.context.annotation.EnableLoadTimeWeaving;

import com.game.annotation.BinaryDb;
import com.game.annotation.IgnoreDb;
import com.game.common.util.BeanUtil;
import com.game.db.bean.RoleDataProxy;
import com.game.db.dao.AccountDb;
import com.game.db.dao.BaseDb;
import com.game.db.dao.RoleDataDb;
import com.game.db.util.MapperUtils;
import com.game.metaxml.ProtoComm.RoleData;
import com.game.metaxml.ProtoComm.RoleMisc;
import com.game.spring.DataSourceContextHolder;
import com.game.spring.DataSourceType;

/**
 * 公共函数
 * 
 * @author zgt
 *
 */
public class CommMisc {

	
	
	/**
	 * 认证 
	 * @param database
	 * @param Uin
	 * @return
	 */
	public static Account loadAccount(DataSourceType database,int Uin){
		Account account = null;
		if (Uin <= 0) {
			account= new Account();
			account.setRoleName("test01");
			account.setPassword("123456");
			//创建 account
			DataSourceContextHolder.setDbType(database);
			AccountDb accountDb = BeanUtil.getBean(AccountDb.class);
			accountDb.insertAccount(account);
			
			
		}else {
			DataSourceContextHolder.setDbType(database);
			List<Account> accounts = CommMisc.table2Objs(database, "account", Account.class," Uin ="+Uin);
			if (accounts!=null && accounts.size()>0) {
				account =  accounts.get(0);
			}else {
				 account = new Account();
				 account.setRoleName("test01");
				 account.setPassword("123456");
				//创建 account
				DataSourceContextHolder.setDbType(database);
				AccountDb accountDb = BeanUtil.getBean(AccountDb.class);
				accountDb.insertAccount(account);
				return account;
			}
		}
		return account;
	}
	
	
	/**
	 * 获取 RoleData
	 * @param database
	 * @param Uin
	 * @return
	 */
	public static RoleData loadRoleData(DataSourceType database,int Uin){
		DataSourceContextHolder.setDbType(database);
		RoleDataDb baseDb = BeanUtil.getBean(RoleDataDb.class);
		List<RoleDataProxy> list = baseDb.selectRoleData(Uin);
		if (list == null || list.size()<1) {
			RoleData roleData = new RoleData();
			roleData.setUin(Uin);
			RoleMisc Misc = new RoleMisc();
			roleData.setMisc(Misc);
			RoleDataProxy roleDataProxy = new RoleDataProxy(roleData);
			baseDb.insertRoleData(roleDataProxy);
			return roleData;
		}else {
			RoleData roleData =list.get(0).build();
			return roleData;
		}
	}
	
	/**
	 * 更新 roleData
	 * @param database
	 * @param roleData
	 * @return
	 */
	public static int updateRoleData(DataSourceType database,RoleData roleData){
		DataSourceContextHolder.setDbType(database);
		RoleDataDb baseDb = BeanUtil.getBean(RoleDataDb.class);
		int index = baseDb.updateRoleData(new RoleDataProxy(roleData));
		return index;
	}
	
	

	/**
	 * 表转化对象 
	 * @param database
	 * @param table
	 * @param clazz
	 * @param where  sql 条件
	 * @return
	 */
	public static <T> List<T> table2Objs(DataSourceType database, String table, Class<T> clazz,String where) {
		DataSourceContextHolder.setDbType(database);
		BaseDb baseDb = BeanUtil.getBean(BaseDb.class);
		List<T> objects = new ArrayList<>();
		List<Map<String, Object>> dataList = baseDb.selectWhere(table, where);
		for (Map<String, Object> map : dataList) {
			T object = MapperUtils.mapperObj(clazz, map);
			objects.add(object);
		}
		return objects;
	}
	
	/**
	 * 表 转 化 成对象
	 * 
	 * @param database
	 *            数据库
	 * @param table
	 *            表
	 * @param clazz
	 *            类
	 * @return
	 */
	public static <T> List<T> table2Objs(DataSourceType database, String table, Class<T> clazz) {
		DataSourceContextHolder.setDbType(database);
		BaseDb baseDb = BeanUtil.getBean(BaseDb.class);
		List<T> objects = new ArrayList<>();
		List<Map<String, Object>> dataList = baseDb.selectAll(table);
		for (Map<String, Object> map : dataList) {
			T object = MapperUtils.mapperObj(clazz, map);
			objects.add(object);
		}
		return objects;
	}
	
	
	/**
	 * 表 转 map
	 * @param database
	 * @param table
	 * @return
	 */
	public static List<Map<String, Object>> getTable2Map(DataSourceType database, String table) {
		DataSourceContextHolder.setDbType(database);
		BaseDb baseDb = BeanUtil.getBean(BaseDb.class);
		List<Map<String, Object>> dataList = baseDb.selectAll(table);
		return dataList;
	}
	

	
	/**
	 * 查询 数据 转化成 map
	 * @param database
	 * @param table
	 * @param where 条件
	 * @return
	 */
	public static List<Map<String, Object>> getTable2Map(DataSourceType database, String table,String where) {
		DataSourceContextHolder.setDbType(database);
		BaseDb baseDb = BeanUtil.getBean(BaseDb.class);
		List<Map<String, Object>> dataList = baseDb.selectWhere(table, where);
		return dataList;
	}
	
	
	/**
	 * 删除 对象
	 * @param database
	 * @param table
	 * @param where
	 * @return
	 */
	public static int deleteDb(DataSourceType database, String table,String where){
		DataSourceContextHolder.setDbType(database);
		BaseDb baseDb = BeanUtil.getBean(BaseDb.class);
		int n = baseDb.deleteWhere(table, where);
		return n;
	}
	
	/**
	 * 根据对象 更新
	 * @param database
	 * @param table
	 * @param obj
	 * @param where
	 * @return
	 */
	public static int updateDb(DataSourceType database, String table,Object obj,String where){
		StringBuilder Column = new StringBuilder();
		try {
			@SuppressWarnings("rawtypes")
			Class clazz = obj.getClass();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				@SuppressWarnings("rawtypes")
				Class type = field.getType();
				boolean b = isBaseType(type);
				if (b == false) {
					throw new Exception();
				}
				String name = field.getName();
				Object value = field.get(obj);
				if (type.equals(String.class)) {
					value = "'"+value+"'";
				}
				
				Column.append(name+" = "+value+",");
			} 
			Column.deleteCharAt(Column.length()-1);
			
			//插入数据
			DataSourceContextHolder.setDbType(database);
			BaseDb baseDb = BeanUtil.getBean(BaseDb.class);
			int n = baseDb.update(table, Column.toString(), where);
			return n;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	
	public static int insertBinary(DataSourceType database, String table,Object obj){
		
		StringBuilder columnBuilder = new StringBuilder();
		StringBuilder columnDataBuilder = new StringBuilder();
		
		try {
			byte [] bytes = null;
			@SuppressWarnings("rawtypes")
			Class clazz = obj.getClass();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				@SuppressWarnings("rawtypes")
				Class type = field.getType();
				String name = field.getName();
				columnBuilder.append(name+",");
				
				Annotation IgnoreAnnotation = field.getAnnotation(IgnoreDb.class);
				if (IgnoreAnnotation != null) {
					continue;
				}
				
				Annotation BinaryAnnotation = field.getAnnotation(BinaryDb.class);
				if (BinaryAnnotation!=null) {  
					if (field.get(obj)!=null) {
						bytes = CommMisc.obj2bytes(field.get(obj));
					}
				}else {
					boolean b = isBaseType(type);
					if (b == false) {
						throw new Exception();
					}
					
					Object value = field.get(obj);
					if (type.equals(String.class)) {
						value = "'"+value+"'";
					}
					columnDataBuilder.append(value+",");
				}
				
			} 
			columnBuilder.deleteCharAt(columnBuilder.length()-1);
			columnDataBuilder.deleteCharAt(columnDataBuilder.length()-1);
			//插入数据
			DataSourceContextHolder.setDbType(database);
			BaseDb baseDb = BeanUtil.getBean(BaseDb.class);
			int n = baseDb.insertBinary(table, columnBuilder.toString(), columnDataBuilder.toString(),bytes);
			return n;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	public static int insertDb(DataSourceType database, String table,Object obj){
		return CommMisc.insertDb(database, table, obj,BaseDb.class);
	}
	
	/**
	 * 添加数据 
	 * @param database
	 * @param table
	 * @param obj  属性为基本类型 和 字符串 
	 * @return
	 */
	public static int insertDb(DataSourceType database, String table,Object obj,Class baseDbClazz){
		
		StringBuilder columnBuilder = new StringBuilder();
		StringBuilder columnDataBuilder = new StringBuilder();
		try {
			@SuppressWarnings("rawtypes")
			Class clazz = obj.getClass();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				
				Annotation IgnoreAnnotation = field.getAnnotation(IgnoreDb.class);
				if (IgnoreAnnotation != null) {
					continue;
				}
				
				@SuppressWarnings("rawtypes")
				Class type = field.getType();
				boolean b = isBaseType(type);
				if (b == false) {
					throw new Exception();
				}
				String name = field.getName();
				Object value = field.get(obj);
				columnBuilder.append(name+",");
				if (type.equals(String.class)) {
					value = "'"+value+"'";
				}
				columnDataBuilder.append(value+",");
			} 
			columnBuilder.deleteCharAt(columnBuilder.length()-1);
			columnDataBuilder.deleteCharAt(columnDataBuilder.length()-1);
			//插入数据
			DataSourceContextHolder.setDbType(database);
			BaseDb baseDb = (BaseDb) BeanUtil.getBean(baseDbClazz);
			int n = baseDb.insert(table, columnBuilder.toString(), columnDataBuilder.toString());
//			Object baseDb = BeanUtil.getBean(baseDbClazz);
//			
//			Method method = baseDb.getClass().getMethod("insert", String.class,String.class,String.class);
//			int n = (int) method.invoke(baseDb, table, columnBuilder.toString(), columnDataBuilder.toString());
			
			return n;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	/**
	 * 表 转化 成 xml struct tools 使用
	 * @return
	 */
	public static String table2XmlStruct(DataSourceType database, String ... tables)throws Exception{
		
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("metalib");  
		    
		for(String table:tables){
			List<Map<String, Object>> list = CommMisc.getTable2Map(database, table);
			if (list!=null && list.size()>0) {
			    Element struct = root.addElement("struct"); //添加root的子节点 
			    struct.addAttribute("name", table);  
			    struct.addAttribute("desc", "生成  "+table);  
			    Map<String, Object> map = list.get(0);
				
				Set<String> ks = map.keySet();
		        Iterator<String> it = ks.iterator();
		        while (it.hasNext()) {
		            String key = it.next();
		            Object value = map.get(key);
		            String type = "int";
		            if(value!=null && !isNumeric(value.toString())){
		        	   type = "String";
		            }
		            Element entry = struct.addElement("entry"); //添加root的子节点 
		            entry.addAttribute("name", key);  
		            entry.addAttribute("type", type);  
		            entry.addAttribute("desc", "属性  "+key);  
		        }
		    }
		}
		//输出全部原始数据，在编译器中显示  
        OutputFormat format = OutputFormat.createPrettyPrint();  
        format.setEncoding("UTF-8");//根据需要设置编码  
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLWriter writer = new XMLWriter(out, format);  
        document.normalize();  
        writer.write(document);    
        writer.close(); 
        return new String(out.toByteArray());
	}
	
	
	
	/**
	 * list 转 成 map 
	 * 根据 字段名称的获取值 作为 map 的 key
	 * @param list
	 * @param type Map key的类型 
	 * @param fieldKey 字段 名称   
	 * @return
	 */
	public static <K,T> HashMap<K, T> list2Map(List<T> list,Class<K> type,String fieldKey){
		try {
			HashMap<K, T> map = new HashMap<>();
			for (T t : list) {
				K keyValue = null;
				Field[] fields = t.getClass().getDeclaredFields();
				go: for (Field field : fields) {
					field.setAccessible(true);
					if (fieldKey.equals(field.getName()) && type.equals(field.getType())) {
						keyValue = (K) field.get(t);
						break go;
					}
				}
				if (keyValue!=null) {
					map.put(keyValue, t);
				}
			} 
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	/**
	 * 对象转 2进制
	 * 
	 * @param object
	 * @return
	 */
	public static byte[] obj2bytes(Object object) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(outputStream);
			out.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return outputStream.toByteArray();
	}

	/**
	 * 二进制 转 对象
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object bytes2obj(byte[] bytes) {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		ObjectInputStream in = null;
		Object obj = null;
		try {
			in = new ObjectInputStream(inputStream);
			obj = in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	/**
	 * obj 转 xml
	 * 
	 * @param obj
	 * @return
	 */
	public static String obj2xml(Object obj) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			JAXBContext jbt = JAXBContext.newInstance(obj.getClass());
			Marshaller ms = jbt.createMarshaller();
			ms.marshal(obj, out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		String xml = new String(out.toByteArray());
		try {
			xml = formatXML(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml;
	}

	/**
	 * xml 转 obj
	 * 
	 * @param xml
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T xml2obj(String xml, Class<T> clazz) {
		T obj = null;
		try {
			JAXBContext unjbt = JAXBContext.newInstance(clazz);
			Unmarshaller unms = unjbt.createUnmarshaller();
			obj = (T) unms.unmarshal(new StringReader(xml));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * xml 格式化
	 * 
	 * @param inputXML
	 * @return
	 * @throws Exception
	 */
	public static String formatXML(String inputXML) throws Exception {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new StringReader(inputXML));
		String requestXML = null;
		XMLWriter writer = null;
		if (document != null) {
			try {
				StringWriter stringWriter = new StringWriter();
				OutputFormat format = new OutputFormat(" ", true);
				writer = new XMLWriter(stringWriter, format);
				writer.write(document);
				writer.flush();
				requestXML = stringWriter.getBuffer().toString();
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
					}
				}
			}
		}
		return requestXML;
	}
	
	/*----io 流工具-----------------------------------------------------------------------*/
	public static boolean writeStr2File(String txt,File file){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(txt);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static String readStr2File(File file){
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = reader.readLine())!=null){
				builder.append(line);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return builder.toString();
	}
	
	//===============================================================
	
	/**
	 * 判断是基本类型  算上了String
	 * @param type
	 * @return
	 */
	private static boolean isBaseType(Class type){
		boolean b = false;
		Class [] baseTypes = new Class[]{byte.class,short.class,int.class,long.class,float.class,double.class,boolean.class,char.class,String.class};
		for (Class clazz : baseTypes) {
			if (clazz.equals(type)) {
				b = true;
			}
		}
		return b;
	}
	
	private static boolean isNumeric(String str){
		  for (int i = 0; i < str.length(); i++){
		   //System.out.println(str.charAt(i));
		   if (!Character.isDigit(str.charAt(i))){
		    return false;
		   }
		  }
		  return true;
	}

}
