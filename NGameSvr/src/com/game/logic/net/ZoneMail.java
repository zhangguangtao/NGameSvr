package com.game.logic.net;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.game.logic.net.Cs.CsItemOne;
import com.game.logic.net.Cs.CsMail;
import com.game.logic.net.Cs.CsMailReq;
import com.game.logic.net.Cs.CsMailRes;
import com.game.metaxml.CommMisc;
import com.game.metaxml.ProtoComm;
import com.game.metaxml.ProtoComm.HMail;
import com.game.metaxml.ProtoComm.HMailDetail;
import com.game.metaxml.ProtoComm.MailInfo;
import com.game.metaxml.ProtoComm.MoneyOne;
import com.game.metaxml.ProtoComm.Player;
import com.game.service.message.CSMSG;
import com.game.spring.DataSourceType;

/**
 * 邮件系统
 * @author zgt
 */
public class ZoneMail {
	
	private final int MAIL__CLT__OP__MAIL_READ = 1;
	private final int MAIL__CLT__OP__MAIL_GET =2;
	private final int MAIL__CLT__OP__MAIL_INFO =3;
	private final int MAIL__CLT__OP__MAIL_DEL =4;
	
	private final int MAIL__SVR__OP__MAIL_SVR_GET = 3;
	
	private final int MAIL_DEL_HOUR = 3;
	private final int MAIL_DEL_MIN = 30;
	
	private ZONESVRENV pstEnv = ZONESVRENV.getInstance();
	
	public static ZoneMail zoneMail;
	
	public static ZoneMail getInstance(){
		if (zoneMail == null) {
			zoneMail = new ZoneMail();
		}
		return zoneMail;
	}
	
	private ZoneMail(){
		
	}
	
	/**
	 * 阅读邮件   领取物品等
	 */
	public void mailOpReq(BaseSvr baseSvr,Player pstPlayer,CSMSG pstMsg) throws Exception {
		@SuppressWarnings("static-access")
		CsMailReq pstReq = CsMailReq.getDefaultInstance().parseFrom(pstMsg.getBody());
		int Wid = pstReq.getWid();
		switch (pstReq.getMsgType()) {
		case MAIL__CLT__OP__MAIL_READ:
			//邮件读取 
			mailRead(baseSvr,pstPlayer);
			break;
		case MAIL__CLT__OP__MAIL_GET:
			//领取奖励
			mailReward(baseSvr,pstPlayer, Wid);
			break;
		case MAIL__CLT__OP__MAIL_INFO:
			//领取全部奖励
			mailRewardAll(baseSvr,pstPlayer);
			break;
		case MAIL__CLT__OP__MAIL_DEL:
			mailDel(baseSvr,pstPlayer, Wid);
			break;

		default:
			break;
		}
		
	}
	
	/**
	 * 一键领取奖励
	 * @param baseSvr
	 * @param pstPlayer
	 */
	private void mailRewardAll(BaseSvr baseSvr, Player pstPlayer) {
		long iCurr = pstEnv.getSvrTime();
		String where =String.format("DelTime > %d and  Recv in ( %d , 0 )",iCurr,pstPlayer.getStRoleData().getUin()); 
		List<HMail> list =  CommMisc.table2Objs(DataSourceType.DB_ACCOUNT, "HMail", HMail.class,where);
		MailInfo mailInfo = pstPlayer.getStRoleData().getMisc().getMailInfo();
		ArrayList<BigInteger> wids =  mailInfo.getMailWid();
		for (BigInteger wid : wids) {
			for (int i = list.size(); i< 0; i--) {
				HMail mail = list.get(i);
				if (mail.getWid().equals(wid)) {
					list.remove(mail);
				}
			}
		}
		int succ = -1;
		ArrayList<BigInteger> sendWids = new ArrayList<>();
		if (list!=null&&list.size()>0) {
			for (HMail mail : list) {
				addOutWid(pstPlayer, mail.getWid().intValue());
				sendWids.add(mail.getWid());
				//给player 添加奖励
				
			}
			succ = 0;
		}else {
			succ = -1;
		}
		this.mailGetNotify(baseSvr, sendWids, succ);
		
	}

	private void mailDel(BaseSvr baseSvr,Player pstPlayer, int wid) {
		CommMisc.deleteDb(DataSourceType.DB_ACCOUNT, "HMail"," Wid = "+wid);
	}

	
	/**
	 * 领取单个奖励
	 * @param baseSvr
	 * @param pstPlayer
	 * @param wid
	 */
	private void mailReward(BaseSvr baseSvr,Player pstPlayer, int wid) {
		int succ = -1;
		List<HMail> list =  CommMisc.table2Objs(DataSourceType.DB_ACCOUNT, "HMail", HMail.class," Wid = "+wid);
		if (list!=null&&list.size()>0) {
			HMail mail = list.get(0);
			//给player 添加奖励
			
			addOutWid(pstPlayer, wid);
			succ = 0;
		}else {
			succ = -1;
		}
		ArrayList<BigInteger> sendWids = new ArrayList<>();
		sendWids.add(new BigInteger(String.valueOf(wid)));
		this.mailGetNotify(baseSvr, sendWids, succ);
	}
	
	
	/**
	 * 添加领取过的wid
	 */
	private void addOutWid(Player pstPlayer,int wid){
		MailInfo mailInfo = pstPlayer.getStRoleData().getMisc().getMailInfo();
		ArrayList<BigInteger> wids =  mailInfo.getMailWid();
		if (wids!=null) {
			wids.add(new BigInteger(String.valueOf(wid)));
		}else {
			ArrayList<BigInteger> _wids = new ArrayList<>();
			_wids.add(new BigInteger(String.valueOf(wid)));
			mailInfo.setMailWid(_wids);
		}
	}
	

	/**
	 * 读取邮件
	 * @param baseSvr
	 * @param pstPlayer
	 * @param wid
	 * @return
	 */
	private void mailRead(BaseSvr baseSvr,Player pstPlayer) {
		//List<HMail> list =  CommMisc.table2Objs(DataSourceType.DB_ACCOUNT, "HMail", HMail.class," Wid = "+wid);
//		MailInfo mailInfo = pstPlayer.getStRoleData().getMisc().getMailInfo();
//		ArrayList<BigInteger> wids =  mailInfo.getMailWid();
//		this.mailGetNotify(baseSvr, wids, 0);
		this.mailPlayerLogin(baseSvr, pstPlayer);
	}

	/**
	 * 根据时间删除邮件
	 */
	public void mailDelByTime(){
		// 凌晨3点30分清除下过期邮件
		// 搞个人少的时间清理邮件
		long time = pstEnv.getSvrTime();
		Date date = new Date(time);
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);
		int h = calendar.get(Calendar.HOUR_OF_DAY);
		int m = calendar.get(Calendar.MINUTE);
		if (h == MAIL_DEL_HOUR && m == MAIL_DEL_MIN ) {
			String where = " DelTime+5  < CURTIME() ";
			CommMisc.deleteDb(DataSourceType.DB_ACCOUNT, "HMail", where);
		}
	}
	
	
	/**
	 * 数据写入数据库中
	 * @param hMail
	 */
	public void mailInsertDbReq(HMail hMail){
		CommMisc.insertBinary(DataSourceType.DB_ACCOUNT, "HMail", hMail);
		
		
	}
	
	/**
	 * 角色登陆垃取邮件信息
	 */
	public void mailPlayerLogin(BaseSvr baseSvr,Player pstPlayer){
		long iCurr = pstEnv.getSvrTime();
		String where =String.format("DelTime > %d and  Recv in ( %d , 0 )",iCurr,pstPlayer.getStRoleData().getUin()); 
		List<HMail> list =  CommMisc.table2Objs(DataSourceType.DB_ACCOUNT, "HMail", HMail.class,where);
		MailInfo mailInfo = pstPlayer.getStRoleData().getMisc().getMailInfo();
		ArrayList<BigInteger> wids =  mailInfo.getMailWid();
		List<HMail> sendList = new ArrayList<>();
		if (wids!=null) {
			for (HMail mail : list) {
				boolean isSend = true;
				for (BigInteger wid : wids) {
					BigInteger mailWid = mail.getWid();
					if (mailWid.equals(wid)) {
						isSend = false;
					}
				}
			    //发给客户端
				if (isSend) {
					sendList.add(mail);
				}
			}
		}
		
		//清理 mailinfo 中的wid
	   for(int i=wids.size();i<0;i--){
		    boolean isIn = false;
			for (HMail hMail : list) {
				if (hMail.getWid().equals(wids.get(i))) {
					isIn = true;
				}
			}
			if (isIn == false) {
				wids.remove(i);
			}
	   }
		
		for (BigInteger wid : wids) {
			for (int i = list.size(); i< 0; i--) {
				HMail mail = list.get(i);
				if (mail.getWid().equals(wid)) {
					list.remove(mail);
				}
			}
		}
		
		ArrayList<CsMail>  sendMails = new ArrayList<>();
		for (HMail mail : sendList) {
			CsMail.Builder csMail =  CsMail.newBuilder();
			csMail.setWID(mail.getWid().intValue());
			
			csMail.setDelTime(mail.getDelTime());
			csMail.setTitle(mail.getTitle());
			csMail.setSend(mail.getSend());
			csMail.setSendTime(mail.getSendTime());
			csMail.setType(mail.getType());
			csMail.setText(mail.getDetail().getText());
			
			ArrayList<CsItemOne>  ones = new ArrayList<>();
			
			for (MoneyOne one : mail.getDetail().getMoneys()) {
				CsItemOne.Builder item =  CsItemOne.newBuilder();
				item.setCount(one.getNum());
				item.setType(one.getType());
				item.setId(one.getId());
				ones.add(item.build());
			}
			csMail.addAllItems(ones);
			sendMails.add(csMail.build());
		}
		
		CsMailRes.Builder mailRes = CsMailRes.newBuilder();
		mailRes.setMsgType(MAIL__SVR__OP__MAIL_SVR_GET);
		mailRes.setSucc(0);
		mailRes.addAllMails(sendMails);
		//mailRes.addAllWids(sendwids);
		
		baseSvr.svrMsgSend(ProtoComm.MSG__TYPE__MAIL_RES, mailRes);
	}
	
	/**
	 * 发送给客户端
	 */
	public void mailGetNotify(BaseSvr baseSvr,ArrayList<BigInteger> wids,int Succ){
		if (wids == null){
			return;
		}
		ArrayList<Integer> _wids = new ArrayList<>();
		for (BigInteger bigint : wids) {
			_wids.add(bigint.intValue());
		}
		CsMailRes.Builder mailRes = CsMailRes.newBuilder();
		mailRes.setMsgType(MAIL__SVR__OP__MAIL_SVR_GET);
		mailRes.setSucc(Succ);
		mailRes.addAllWids(_wids);
		baseSvr.svrMsgSend(ProtoComm.MSG__TYPE__MAIL_RES, mailRes);
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		//ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		
		
		HMail hMail = new HMail();
		BigInteger wid = new BigInteger("5");
		hMail.setWid(wid);
		hMail.setRecv(10);
		hMail.setSend(5);
		hMail.setDelTime(555);
		hMail.setFlag(4);
		hMail.setTitle("zhang");
		hMail.setSend(5558);
		hMail.setType(1);
		HMailDetail detail = new HMailDetail();
		
		hMail.setDetail(detail);
		
		//List<HMail> list =  CommMisc.table2Objs(DataSourceType.DB_ACCOUNT, "HMail", HMail.class," Wid = 1 ");
		//System.out.println(list.get(0).getDetail().getArmID());
		
		//ZoneMail.getInstance().mailSendSysOne(hMail);
		
		long iCurr = System.currentTimeMillis()+1000;
		System.out.println(iCurr);
		String where =String.format(" Recv=%d  && DelTime > %d", 5,iCurr); 
		System.out.println(where);
		
		
	
		
	}
	
}
