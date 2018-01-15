package com.game.logic.net;

import java.net.SocketAddress;
import java.util.List;

import javax.mail.Address;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.game.common.util.BeanUtil;
import com.game.db.bean.RoleDataProxy;
import com.game.db.dao.RoleDataDb;
import com.game.logic.net.Cs.CsHeartReq;
import com.game.logic.net.Cs.CsHeartRes;
import com.game.logic.net.Cs.LoginReq;
import com.game.logic.net.Cs.LoginRes;
import com.game.logic.net.Cs.RegShopBoughtInfo;
import com.game.logic.net.Cs.RegTaskInfo;
import com.game.logic.net.Cs.RegTaskRsp;
import com.game.metaxml.ProtoComm;
import com.game.metaxml.ProtoComm.Player;
import com.game.service.message.CSMSG;

import io.netty.channel.ChannelHandlerContext;
@Scope(value="prototype")
@Service("ZoneSvr")
public class ZoneSvr extends BaseSvr{
	
	
	private ZonePlayer zonePlayer;
	private Player pstPlayer;
	
	private ZONESVRENV pstEnv = ZONESVRENV.getInstance();
	
	public ZoneSvr(){
		
	}
	
	@Override
	public void init(ChannelHandlerContext channelHandler) {
		super.init(channelHandler);
		zonePlayer = new ZonePlayer(pstEnv,channelHandler);
	}
	
	
	
	public Player getPstPlayer() {
		return pstPlayer;
	}



	public void initCmdMap(){
		//登陆请求
		map.put(ProtoComm.MSG__TYPE__LOGIN_REQ, "playerLoginReq");
		//心跳 请求
		map.put(ProtoComm.MSG__TYPE__HEART_REQ, "playerHeartReq");
		//RegTaskReq 注册7日活动
		map.put(ProtoComm.MSG__TYPE__REG_TASK_REQ, "regTaskReq");
		//邮件
		map.put(ProtoComm.MSG__TYPE__MAIL_REQ, "mailOpReq");
	}
	
	
	
	/*---start 业务逻辑-------------------------------------------------------------------------------------------*/
	
	public void mailOpReq(CSMSG stMsg)throws Exception{
		ZoneMail.getInstance().mailOpReq(this,pstPlayer, stMsg);
	}
	
	public void playerLoginReq(CSMSG stMsg)throws Exception{
		
		//解析出 proto
		@SuppressWarnings("static-access")
		LoginReq pstReq =  LoginReq.getDefaultInstance().parseFrom(stMsg.getBody());
		System.out.println(pstReq);
		//GameChannelManger.getInstance().putChannel(pstReq.getUin(), this.channelHandler);
		
		SocketAddress address = channelHandler.channel().remoteAddress();
		System.out.println(address);
		System.out.println(channelHandler.channel().id().asLongText());
        System.out.println(channelHandler.channel().id().asShortText());
		if (pstPlayer == null){
			pstPlayer = zonePlayer.getPlayerByUin(pstReq.getUin());
		}
		
		if (pstPlayer!=null) {
			ZONESVRENV.getInstance().getUins().put(pstReq.getUin(), channelHandler);
		}
		
		//回复数据
		LoginRes.Builder res = LoginRes.newBuilder();
		res.setSucc(1);
		res.setMemID(1);
		res.setSvrTime(8);
		res.setIsSelectCamp(1);
		res.setLoginSeq(5);
		res.setRegTaskDay(9);
		
		
		this.svrMsgSend(ProtoComm.MSG__TYPE__LOGIN_RES,res);
		
	}
	
	/**
	 * 心跳检测
	 * @param stMsg
	 */
	public void playerHeartReq(CSMSG stMsg){
		System.out.println("心跳检测 playerHeartReq:"+stMsg.getHead().getCmd());
		CsHeartReq pstReq = (CsHeartReq) this.parserProto(stMsg, Cs.CsHeartReq.getDefaultInstance());
		System.out.println(pstReq);
		
		
		CsHeartRes.Builder heartRes = CsHeartRes.newBuilder();
		int longtime = (int) System.currentTimeMillis();
		heartRes.setSvrTime(longtime);
		heartRes.setMessageCount(0);
		
		this.svrMsgSend(ProtoComm.MSG__TYPE__HEART_RES,heartRes);
	}
	
	
	/**
	 * 七日活动
	 * @param stMsg
	 */
	public void regTaskReq(CSMSG stMsg){
		RegTaskRsp.Builder stRes = RegTaskRsp.newBuilder();
		stRes.setSucc(0);
		stRes.setDay(2);
		
//		for (int i = 0; i < 5; i++) {
//			stRes.addBought(i);
//		}
		
		for (int i = 0; i < 95; i++) {
			RegTaskInfo.Builder taskInfo = RegTaskInfo.newBuilder();
			taskInfo.setId(8);
			taskInfo.setValue(1);
			taskInfo.setState(1);
			
			stRes.addInfos(taskInfo);
		}
		
//		for (int i = 0; i < 5; i++) {
//			RegShopBoughtInfo.Builder shopBoughInfo = RegShopBoughtInfo.newBuilder();
//			shopBoughInfo.setId(10);
//			shopBoughInfo.setCount(2);
//		}
		
		this.svrMsgSend(ProtoComm.MSG__TYPE__REG_TASK_RSP,stRes);
	}
	
	/*-----end 结束业务逻辑------------------------------------------------------------------------------------------------*/
	
	@Override
	public String debugInfo() {
		String info = "ZoneSvr";
		return info;
	}
}
