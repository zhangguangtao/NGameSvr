package com.game.transmission;

public class TableStruct {
	
	private String table;
	private String where;
	private String column;
	private String columnData;
	private String setColumn;
	private String operation;//insert  update  deleteWhere  selectWhere  selectAll limit
	
	private String offset;
	private String rows;
	
	public String getOffset() {
		return offset;
	}
	public void setOffset(String offset) {
		this.offset = offset;
	}
	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getWhere() {
		return where;
	}
	public void setWhere(String where) {
		this.where = where;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getColumnData() {
		return columnData;
	}
	public void setColumnData(String columnData) {
		this.columnData = columnData;
	}
	public String getSetColumn() {
		return setColumn;
	}
	public void setSetColumn(String setColumn) {
		this.setColumn = setColumn;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	
	
	

}
