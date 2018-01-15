package com.game.logic.net;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.game.logic.net.Cs.AuthReq;
import com.game.logic.net.Cs.AuthRes;
import com.game.logic.net.Cs.DirReq;
import com.game.metaxml.Account;
import com.game.metaxml.CommMisc;
import com.game.metaxml.ProtoComm;
import com.game.service.message.CSMSG;
import com.game.spring.DataSourceType;

import io.netty.channel.ChannelHandlerContext;
@Scope(value="prototype")
@Service("AuthSvr")
public class AuthSvr extends BaseSvr{

	public AuthSvr() {
		
	}

	@Override
	public void initCmdMap() {
		map.put(ProtoComm.MSG__TYPE__AUTH_REQ, "authSvrMsg");
	}
	
	public void authSvrMsg(CSMSG stMsg)throws Exception{
		@SuppressWarnings("static-access")
		AuthReq pstReq = AuthReq.getDefaultInstance().parseFrom(stMsg.getBody());
		
		int Uin = pstReq.getUin();
		
		Account account = CommMisc.loadAccount(DataSourceType.DB_ACCOUNT, Uin);
		
		AuthRes.Builder authRes = AuthRes.newBuilder();
		
		authRes.setType(1);
		authRes.setAccountName(account.getRoleName());
		authRes.setUin(account.getUin());
		authRes.setEnc("ccccccccccccc");
		
		
		
		this.svrMsgSend(ProtoComm.MSG__TYPE__AUTH_RES,authRes);
	}

	@Override
	public String debugInfo() {
		String info = "AuthSvr";
		return info;
	}
	

}
