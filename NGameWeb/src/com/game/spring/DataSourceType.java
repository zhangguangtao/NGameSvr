package com.game.spring;
/**
 * 数据源 
 * @author zgt
 *
 */
public enum DataSourceType {
	
	DB_ACCOUNT("account"),
	DS_ADMIN("ds_admin"),
	DS_RESDB("resdb");
	
	private String database;
	
	private DataSourceType(String database){
		this.database = database;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	
	

//	public static String SOURCE_MOP="ds_mop";
//	
//	public static String DS_ADMIN="ds_admin";
//	
//	public static String DS_PARTNER="ds_partner";
	
	
}
