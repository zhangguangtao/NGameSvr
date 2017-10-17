package com.game.db.dao;

import org.apache.ibatis.annotations.Param;

import com.game.metaxml.Account;

public interface AccountDb {

	public int insertAccount(Account account);
	
	
	
	public int insert(@Param("table") String table,@Param("column") String column,@Param("columnData") String columnData);
}
