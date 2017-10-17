package com.game.logic.net;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.game.logic.net.Cs.CsHeartRes;
import com.game.logic.net.Cs.DirRegionInfo;
import com.game.logic.net.Cs.DirReq;
import com.game.logic.net.Cs.DirRes;
import com.game.logic.net.Cs.DirWorldInfo;
import com.game.metaxml.Cspb;
import com.game.service.message.CSMSG;

import io.netty.channel.ChannelHandlerContext;
@Scope(value="prototype")
@Service("DirSvr")
public class DirSvr extends BaseSvr{

	public DirSvr() {
		
	}

	@Override
	public void initCmdMap() {
		//dir req
		map.put(Cspb.MSG__TYPE__DIR_REQ, "dirSvrMsg");
	}
	
	public void dirSvrMsg(CSMSG stMsg)throws Exception{
		@SuppressWarnings("static-access")
		DirReq pstReq =  DirReq.getDefaultInstance().parseFrom(stMsg.getBody());
		int LastVer = pstReq.getLastVer();
		System.out.println(LastVer);
		
		
		DirRegionInfo.Builder region = DirRegionInfo.newBuilder();
		region.setName("不知火舞");
		region.setUrl("http://192.168.0.77/patchs/");
		region.setStatFlag(25);
		
		DirWorldInfo.Builder World = DirWorldInfo.newBuilder();
		World.setName("众神之王");
		//World.setIP("182.254.231.116");
		World.setIP("192.168.1.145");
		World.setPort(9002);
		World.setWorldID(1);
		World.setRegionID(1);
		World.setRegionIdx(0);
		World.setNewFlag(1);
		World.setAuthIP("192.168.1.145");
		World.setAuthPort(6727);
		World.setStatFlag(1);
		
		
		DirRes.Builder dirRes = DirRes.newBuilder();
		dirRes.setLastVer(2);
		dirRes.addRegion(region);
		dirRes.addWorld(World);
		//CsHeartRes.Builder heartRes = CsHeartRes.newBuilder();
		
		this.svrMsgSend(Cspb.MSG__TYPE__DIR_RES,dirRes);
	}

	@Override
	public String debugInfo() {
		String info = "DirSvr";
		return info;
	}
	
}
