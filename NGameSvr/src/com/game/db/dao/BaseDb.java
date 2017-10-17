package com.game.db.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface BaseDb {
	
	public List<Map<String, Object>> selectAll(@Param("table") String table);
	
	public List<Map<String, Object>>  selectWhere(@Param("table") String table,@Param("where") String where);
	
	public int deleteWhere(@Param("table") String table,@Param("where") String where);
	
	public int insert(@Param("table") String table,@Param("column") String column,@Param("columnData") String columnData);
	
	public int update(@Param("table") String table,@Param("setColumn") String setColumn,@Param("where") String where);
	
	public int insertBinary(@Param("table") String table,@Param("column") String column,@Param("columnData") String columnData,@Param("bytes") byte[] bytes);
	
	
}
