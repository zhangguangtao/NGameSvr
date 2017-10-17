package com.game.bootstrap.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Test02 {
	
	public static class Test002Action{
		
	}

	public static void main(String[] args) {
		
		
		Test002Action test3 = new Test002Action();
		String className = test3.getClass().getSimpleName();
		System.out.println(className);
		className = (char)(className.charAt(0) - ('A' - 'a')) + className.substring(1, className.indexOf("Action"));
		System.out.println(className);
		
		
		String url = "/uuu/tt/Test?ddd=ddd";
		
		String strs [] =  url.split("\\?");
		for (String string : strs) {
			System.out.println(string);
		}
		for (String string : strs[0].split("/")) {
			System.out.println(string);
		}
		
		//Pattern p = Pattern.compile(".*/command=(.*)\\?(.*)"); 
		
		Pattern p = Pattern.compile(".*/(.*)\\?(.*)"); 
    	
    	Matcher m = p.matcher(url); 
    	if (m.matches()){
    		System.out.println( m.group(1));
    		System.out.println( m.group(2));
    	}else {
			System.out.println("error");
		}
		
	}
	
}
