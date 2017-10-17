package com.game.db.bean;

import com.game.metaxml.CommMisc;
import com.game.metaxml.ProtoComm.RoleData;
import com.game.metaxml.ProtoComm.RoleMisc;

/**
 * 数据代理类 目的 序列化
 * 
 * @author zgt
 *
 */
public class RoleDataProxy {

	private int Uin;
	private int RoleID; /* global unique role id */
	private String RoleName;
	private int Lvl;
	private int IconID;
	private int CreateTime;
	private int LastLogin;
	private int LastLogout;
	private int Money;
	private int Gold;
	private int RMB;
	private int Tili;
	private int MaxTili;
	private int Exp;
	private int WorldID; /* Ver.2674 */
	private int SectionID; /* Ver.2702 */
	private byte[] Misc;

	public RoleDataProxy() {
	}

	public RoleDataProxy(RoleData roleData) {
		this.Uin = roleData.getUin();
		this.RoleID = roleData.getRoleID();
		this.RoleName = roleData.getRoleName();
		this.Lvl = roleData.getLvl();
		this.IconID = roleData.getIconID();
		this.CreateTime = roleData.getCreateTime();
		this.LastLogin = roleData.getLastLogin();
		this.LastLogout = roleData.getLastLogout();
		this.Money = roleData.getMoney();
		this.Gold = roleData.getGold();
		this.RMB = roleData.getRMB();
		this.Tili = roleData.getTili();
		this.MaxTili = roleData.getMaxTili();
		this.Exp = roleData.getExp();
		this.WorldID = roleData.getWorldID();
		this.SectionID = roleData.getSectionID();

		byte[] bytes = CommMisc.obj2bytes(roleData.getMisc());
		this.Misc = bytes;

	}

	public RoleData build() {
		RoleData roleData = new RoleData();

		roleData.setUin(this.Uin);
		roleData.setRoleID(this.RoleID);
		roleData.setRoleName(this.RoleName);

		roleData.setLvl(this.Lvl);
		roleData.setIconID(this.IconID);
		roleData.setCreateTime(this.CreateTime);
		roleData.setLastLogin(this.LastLogin);
		roleData.setLastLogout(this.LastLogout);
		roleData.setMoney(this.Money);
		roleData.setGold(this.Gold);
		roleData.setRMB(this.RMB);
		roleData.setTili(this.Tili);
		roleData.setMaxTili(this.MaxTili);
		roleData.setExp(this.Exp);
		roleData.setWorldID(this.WorldID);
		roleData.setSectionID(this.SectionID);

		RoleMisc misc = (RoleMisc) CommMisc.bytes2obj(this.Misc);

		System.out.println(misc);
		roleData.setMisc(misc);

		return roleData;

	}

	public int getUin() {
		return Uin;
	}

	public void setUin(int uin) {
		Uin = uin;
	}

	public int getRoleID() {
		return RoleID;
	}

	public void setRoleID(int roleID) {
		RoleID = roleID;
	}

	public String getRoleName() {
		return RoleName;
	}

	public void setRoleName(String roleName) {
		RoleName = roleName;
	}

	public int getLvl() {
		return Lvl;
	}

	public void setLvl(int lvl) {
		Lvl = lvl;
	}

	public int getIconID() {
		return IconID;
	}

	public void setIconID(int iconID) {
		IconID = iconID;
	}

	public int getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(int createTime) {
		CreateTime = createTime;
	}

	public int getLastLogin() {
		return LastLogin;
	}

	public void setLastLogin(int lastLogin) {
		LastLogin = lastLogin;
	}

	public int getLastLogout() {
		return LastLogout;
	}

	public void setLastLogout(int lastLogout) {
		LastLogout = lastLogout;
	}

	public int getMoney() {
		return Money;
	}

	public void setMoney(int money) {
		Money = money;
	}

	public int getGold() {
		return Gold;
	}

	public void setGold(int gold) {
		Gold = gold;
	}

	public int getRMB() {
		return RMB;
	}

	public void setRMB(int rMB) {
		RMB = rMB;
	}

	public int getTili() {
		return Tili;
	}

	public void setTili(int tili) {
		Tili = tili;
	}

	public int getMaxTili() {
		return MaxTili;
	}

	public void setMaxTili(int maxTili) {
		MaxTili = maxTili;
	}

	public int getExp() {
		return Exp;
	}

	public void setExp(int exp) {
		Exp = exp;
	}

	public int getWorldID() {
		return WorldID;
	}

	public void setWorldID(int worldID) {
		WorldID = worldID;
	}

	public int getSectionID() {
		return SectionID;
	}

	public void setSectionID(int sectionID) {
		SectionID = sectionID;
	}

	public byte[] getMisc() {
		return Misc;
	}

	public void setMisc(byte[] misc) {
		Misc = misc;
	}

}
