package com.game.db.bean;
/**
 * 上传文件 表
 * @author zgt
 *
 */
public class FileUpTable {
	
	private int id;
	private String fileName;
	private String savePath;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	@Override
	public String toString() {
		return "FileUpTable [id=" + id + ", fileName=" + fileName + ", savePath=" + savePath + "]";
	}
	
	

}
