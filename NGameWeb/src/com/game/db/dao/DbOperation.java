package com.game.db.dao;

public enum DbOperation {
	
	//insert  update  deleteWhere  selectWhere  selectAll
	insert("insert"),
	update("update"),
	deleteWhere("deleteWhere"),
	selectWhere("selectWhere"),
	selectAll("selectAll"),
	limit("limit"),
	count("count");
	
	private String operation;
	
	private DbOperation(String operation){
		this.operation = operation;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	

}
