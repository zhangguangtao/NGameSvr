package com.game.service.message;

import java.util.Arrays;

public class CSMSG {
	
	private CSMSGHEAD Head;                           
	private byte[] Body;
	
	public CSMSGHEAD getHead() {
		return Head;
	}
	public void setHead(CSMSGHEAD head) {
		Head = head;
	}
	public byte[] getBody() {
		return Body;
	}
	public void setBody(byte[] body) {
		Body = body;
	}
	
	@Override
	public String toString() {
		return "CSMSG [Head=" + Head + ", Body=" + Arrays.toString(Body) + "]";
	}
	
	
	
	
	

}
