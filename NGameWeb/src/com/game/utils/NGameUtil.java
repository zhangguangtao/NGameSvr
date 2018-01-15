package com.game.utils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.json.JsonObject;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.game.db.dao.BaseDb;
import com.game.db.dao.DbOperation;
import com.game.spring.BeanUtil;
import com.game.transmission.TableStruct;

public class NGameUtil {

	@SuppressWarnings("rawtypes")
	public static String insert(Object obj) {
		Class clazz = obj.getClass();
		String table = clazz.getSimpleName();
		Field field[] = clazz.getDeclaredFields();
		StringBuilder column = new StringBuilder();
		StringBuilder columnData = new StringBuilder();
		for (int i = 0; i < field.length; i++) {
			Field f = field[i];
			f.setAccessible(true);
			String key = f.getName();
			Object value = null;
			try {
				value = f.get(obj);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if (value != null) {
				column.append(key + ",");
				if (value instanceof String) {
					columnData.append("'" + value + "',");
				} else {
					columnData.append(value + ",");
				}
			}
		}
		column.deleteCharAt(column.length() - 1);
		columnData.deleteCharAt(columnData.length() - 1);

		TableStruct tableStruct = new TableStruct();
		tableStruct.setOperation("insert");
		tableStruct.setTable(table);
		tableStruct.setColumn(column.toString());
		tableStruct.setColumnData(columnData.toString());

		return dbSubmitSql(tableStruct);

	}

	@SuppressWarnings("rawtypes")
	public static String update(Object obj, String where) {
		Class clazz = obj.getClass();
		String table = clazz.getSimpleName();
		Field field[] = clazz.getDeclaredFields();
		StringBuilder setColumn = new StringBuilder();

		for (int i = 0; i < field.length; i++) {
			Field f = field[i];
			f.setAccessible(true);
			String key = f.getName();
			Object value = null;
			try {
				value = f.get(obj);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if (value != null) {
				if (value instanceof String) {
					setColumn.append(key + "=" + "'" + value + "',");
				} else {
					setColumn.append(key + "=" + value + ",");
				}
			}
		}
		setColumn.deleteCharAt(setColumn.length() - 1);
		TableStruct tableStruct = new TableStruct();
		tableStruct.setOperation("update");
		tableStruct.setTable(table);
		tableStruct.setSetColumn(setColumn.toString());
		tableStruct.setWhere(where);
		return dbSubmitSql(tableStruct);

	}
	
	
	@SuppressWarnings("rawtypes")
	public static void deleteWhere(Class clazz, String where) {
        if (TextUtils.isEmpty(where)) {
            return;
        }
        String table = clazz.getSimpleName();
        TableStruct tableStruct = new TableStruct();
        tableStruct.setOperation("deleteWhere");
        tableStruct.setTable(table);
        tableStruct.setWhere(where);
        dbSubmitSql(tableStruct);

    }

	public static<T> List<T> selectWhere(Class<T> clazz, String where) {
        if (TextUtils.isEmpty(where)) {
            return null;
        }
        String table = clazz.getSimpleName();
        TableStruct tableStruct = new TableStruct();
        tableStruct.setOperation("selectWhere");
        tableStruct.setTable(table);
        tableStruct.setWhere(where);
        String json = dbSubmitSql(tableStruct);
        return JSONArray.parseArray(json, clazz);
    }

	public static<T> List<T> selectAll(Class<T> clazz) {
        String table = clazz.getSimpleName();
        TableStruct tableStruct = new TableStruct();
        tableStruct.setOperation("selectAll");
        tableStruct.setTable(table);
        String json = dbSubmitSql(tableStruct);
        return JSONArray.parseArray(json, clazz);
    }
    
	public static<T> List<T> limit(Class<T> clazz,String offset,String rows) {
        if (Integer.parseInt(rows) == 0) {
            return null;
        }
        String table = clazz.getSimpleName();
        TableStruct tableStruct = new TableStruct();
        tableStruct.setOperation("limit");
        tableStruct.setTable(table);
        tableStruct.setOffset(offset);
        tableStruct.setRows(rows);
        String json = dbSubmitSql(tableStruct);
        return JSONArray.parseArray(json, clazz);
    }
	
	public static <T> int count(Class<T> clazz){
		String table = clazz.getSimpleName();
        TableStruct tableStruct = new TableStruct();
        tableStruct.setOperation("count");
        tableStruct.setTable(table);
        String count = dbSubmitSql(tableStruct);
        return Integer.parseInt(count);
	}
	
    
	public static String dbSubmitSql(TableStruct struct) {
		if (DbOperation.insert.getOperation().equals(struct.getOperation())) {
			int index = BeanUtil.getBean(BaseDb.class).insert(struct.getTable(), struct.getColumn(),
					struct.getColumnData());
			return String.valueOf(index);
		}

		if (DbOperation.deleteWhere.getOperation().equals(struct.getOperation())) {
			int index = BeanUtil.getBean(BaseDb.class).deleteWhere(struct.getTable(), struct.getWhere());
			return String.valueOf(index);
		}
		
		if (DbOperation.count.getOperation().equals(struct.getOperation())) {
			int index = BeanUtil.getBean(BaseDb.class).count(struct.getTable());
			return String.valueOf(index);
		}

		if (DbOperation.update.getOperation().equals(struct.getOperation())) {
			int index = BeanUtil.getBean(BaseDb.class).update(struct.getTable(), struct.getSetColumn(),
					struct.getWhere());
			return String.valueOf(index);
		}

		if (DbOperation.selectAll.getOperation().equals(struct.getOperation())) {
			List<Map<String, Object>> list = BeanUtil.getBean(BaseDb.class).selectAll(struct.getTable());
			return JSONObject.toJSONString(list);

		}

		if (DbOperation.selectWhere.getOperation().equals(struct.getOperation())) {
			List<Map<String, Object>> list = BeanUtil.getBean(BaseDb.class).selectWhere(struct.getTable(),
					struct.getWhere());
			return JSONObject.toJSONString(list);
		}
		
		if (DbOperation.limit.getOperation().equals(struct.getOperation())) {
			List<Map<String, Object>> list = BeanUtil.getBean(BaseDb.class).limit(struct.getTable(), struct.getOffset(), struct.getRows());
			
			return JSONObject.toJSONString(list);
		}
		
		
		return null;

	}

}
