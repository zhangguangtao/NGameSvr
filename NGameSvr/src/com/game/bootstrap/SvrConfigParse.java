package com.game.bootstrap;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.game.metaxml.CommMisc;
import com.game.metaxml.ProtoComm.SvrConfig;

public class SvrConfigParse {

	private static SvrConfigParse svrConfigParse = null;
	
	private HashMap<String, SvrConfig> svrMap;
	
//	/**
//	 * 获取配置文件
//	 * @return
//	 */
//	public static Map<Object, Object> getSvrConfig(){
//		if (svrConfigParse == null) {
//			svrConfigParse =new SvrConfigParse("svr-config.xml");
//		}
//		return svrConfigParse.configMap;
//	}
	
	
	
	/**
	 * 获取服务配置
	 * DirSvr
	 * AuthSvr
	 * ZoneSvr
	 * @return
	 */
	public static Map<String, SvrConfig> getSvrMap(){
		if (svrConfigParse == null) {
			svrConfigParse =new SvrConfigParse("svr-config.xml");
		}
		return svrConfigParse.svrMap;
	}
	
	private String svrConfigPath;
    
	private Map<Object, Object> configMap; 
	private SvrConfigParse(String svrConfigPath) {
//		this.svrConfigPath = svrConfigPath;
//		try {
//			init();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		SvrConfig svr = new SvrConfig();
		svr.setSvrName("DirSvr");
		svr.setIP("192.168.1.145");
		svr.setPort(6721);
		
		SvrConfig svr2 = new SvrConfig();
		svr2.setSvrName("AuthSvr");
		svr2.setIP("192.168.1.145");
		svr2.setPort(6727);
		
		SvrConfig svr3 = new SvrConfig();
		svr3.setSvrName("ZoneSvr");
		svr3.setIP("192.168.1.145");
		svr3.setPort(9002);
		
		svrMap = new HashMap<>();
		svrMap.put("DirSvr", svr);
		svrMap.put("AuthSvr", svr2);
		svrMap.put("ZoneSvr", svr3);
	}

	public void init() throws Exception {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(svrConfigPath);
		configMap = xml2map(in);
	}
	
	
    private  Map<Object, Object> xml2map(InputStream in) {
        SAXReader saxReader = new SAXReader();   
		Document doc = null;
		try {
			doc = saxReader.read(in);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
        Map<Object, Object> map = new HashMap<Object, Object>();
        if (doc == null)
            return map;
        Element rootElement = doc.getRootElement();
        element2map(rootElement,map);
        return map;
    }
	
    private  void element2map(Element elmt, Map<Object, Object> map) {
        if (null == elmt) {
            return;
        }
        String name = elmt.getName();
        if (elmt.isTextOnly()) {
            map.put(name, elmt.getText());
        } else {
            Map<Object, Object> mapSub = new HashMap<Object, Object>();
            List<Element> elements = (List<Element>) elmt.elements();
            for (Element elmtSub : elements) {
                element2map(elmtSub, mapSub);
            }
            Object first = map.get(name);
            if (null == first) {
                map.put(name, mapSub);
            } else {
                if (first instanceof List<?>) {
                    ((List) first).add(mapSub);
                } else {
                    List<Object> listSub = new ArrayList<Object>();
                    listSub.add(first);
                    listSub.add(mapSub);
                    map.put(name, listSub);
                }
            }
        }
    }
	

//	public static void main(String[] args) {
//		Map<Object, Object> map = SvrConfigParse.getSvrConfig();
//		System.out.println(map);
//	}

}
