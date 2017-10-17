package com.game.bootstrap.test;

import org.springframework.stereotype.Service;

@Service("aopSvr")
public class AopTest {
	
	public int add(int a,int b){
		System.out.println(a+b);
		int v = a+b/0;
		return a+b;
	}

}
