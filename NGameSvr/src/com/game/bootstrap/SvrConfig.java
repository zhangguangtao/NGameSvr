package com.game.bootstrap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
/**
 * 配置 读取 svrconfig.properties 文件   单例模式
 * @author zgt
 *
 */
@Service
public class SvrConfig {
	
	@Value("${dir_svr}")
	private String DirSvr;
	
	@Value("${dir_ip}")
	private String DirIp;
	
	@Value("${dir_port}")
	private int DirPort;
	
	
	@Value("${auth_svr}")
	private String AuthSvr;
	
	@Value("${auth_ip}")
	private String AuthIp;
	
	@Value("${auth_port}")
	private int AuthPort;
	
	
	@Value("${zone_svr}")
	private String ZoneSvr;
	
	@Value("${zone_ip}")
	private String ZoneIp;
	
	@Value("${zone_port}")
	private int ZonePort;

	public String getDirSvr() {
		return DirSvr;
	}

	public void setDirSvr(String dirSvr) {
		DirSvr = dirSvr;
	}

	public String getDirIp() {
		return DirIp;
	}

	public void setDirIp(String dirIp) {
		DirIp = dirIp;
	}

	public int getDirPort() {
		return DirPort;
	}

	public void setDirPort(int dirPort) {
		DirPort = dirPort;
	}

	public String getAuthSvr() {
		return AuthSvr;
	}

	public void setAuthSvr(String authSvr) {
		AuthSvr = authSvr;
	}

	public String getAuthIp() {
		return AuthIp;
	}

	public void setAuthIp(String authIp) {
		AuthIp = authIp;
	}

	public int getAuthPort() {
		return AuthPort;
	}

	public void setAuthPort(int authPort) {
		AuthPort = authPort;
	}

	public String getZoneSvr() {
		return ZoneSvr;
	}

	public void setZoneSvr(String zoneSvr) {
		ZoneSvr = zoneSvr;
	}

	public String getZoneIp() {
		return ZoneIp;
	}

	public void setZoneIp(String zoneIp) {
		ZoneIp = zoneIp;
	}

	public int getZonePort() {
		return ZonePort;
	}

	public void setZonePort(int zonePort) {
		ZonePort = zonePort;
	}

	@Override
	public String toString() {
		return "SvrConfig [DirSvr=" + DirSvr + ", DirIp=" + DirIp + ", DirPort=" + DirPort + ", AuthSvr=" + AuthSvr
				+ ", AuthIp=" + AuthIp + ", AuthPort=" + AuthPort + ", ZoneSvr=" + ZoneSvr + ", ZoneIp=" + ZoneIp
				+ ", ZonePort=" + ZonePort + "]";
	}
	
	

	

}
