package com.game.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Test {
	
	public static String data="hello world";  
	
	public static void main(String[] args)throws Exception {
//		System.out.println(MD5.getMessageDigest(data.getBytes()));
//		String string = Common.encryptJson(data);
//		System.out.println(string);
//		System.out.println(Common.decryptJson(string));
		
		String dString = Common.encryptAESJson(data);
		System.out.println(dString);
		System.out.println(Common.decryptAESJson(dString));
		
		 getKey();
	}
	
	/** 
	 * 随机生成秘钥 
	 */  
	public static void getKey(){    
	    try {    
	        KeyGenerator kg = KeyGenerator.getInstance("AES");    
	        kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256    
	        SecretKey sk = kg.generateKey();    
	        byte[] b = sk.getEncoded();    
	        String s = byteToHexString(b);    
	        System.out.println(s);    
	        System.out.println("十六进制密钥长度为"+s.length());    
	        System.out.println("二进制密钥的长度为"+s.length()*4);    
	    } catch (NoSuchAlgorithmException e) {    
	        e.printStackTrace();    
	        System.out.println("没有此算法。");    
	    }    
	}    
	
	/** 
	 * byte数组转化为16进制字符串 
	 * @param bytes 
	 * @return 
	 */  
	public static String byteToHexString(byte[] bytes){       
	    StringBuffer sb = new StringBuffer();       
	    for (int i = 0; i < bytes.length; i++) {       
	         String strHex=Integer.toHexString(bytes[i]);   
	         if(strHex.length() > 3){       
	                sb.append(strHex.substring(6));       
	         } else {    
	              if(strHex.length() < 2){    
	                 sb.append("0" + strHex);    
	              } else {    
	                 sb.append(strHex);       
	              }       
	         }    
	    }    
	   return  sb.toString();       
	}  

	//生成密钥对  
    public static KeyPair genKeyPair(int keyLength) throws Exception{  
        KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");  
        keyPairGenerator.initialize(1024);        
        return keyPairGenerator.generateKeyPair();  
    } 
	
}
