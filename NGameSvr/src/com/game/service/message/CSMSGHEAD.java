package com.game.service.message;

public class CSMSGHEAD {

	 private short Cmd;                            
	 private short HeadLen;                        
	 private short BodyLen;
	 
	 
	 
	public CSMSGHEAD(short cmd,short bodyLen) {
		super();
		Cmd = cmd;
		HeadLen = 8;
		BodyLen = bodyLen;
	}
	
	
	
	public short getCmd() {
		return Cmd;
	}



	public void setCmd(short cmd) {
		Cmd = cmd;
	}



	public short getHeadLen() {
		return HeadLen;
	}



	public void setHeadLen(short headLen) {
		HeadLen = headLen;
	}



	public short getBodyLen() {
		return BodyLen;
	}



	public void setBodyLen(short bodyLen) {
		BodyLen = bodyLen;
	}



	@Override
	public String toString() {
		return "CSMSGHEAD [Cmd=" + Cmd + ", HeadLen=" + HeadLen + ", BodyLen=" + BodyLen + "]";
	}
	 
	 
	
	
}
