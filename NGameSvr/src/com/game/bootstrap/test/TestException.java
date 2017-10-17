package com.game.bootstrap.test;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
@Scope(value="prototype")//取消单例模式
@Service ("TestException")
public class TestException {
	
	private TestEx2 testEx2;
	
	public  int n = 1;
	
	public TestException() {
    
		testEx2 = new TestEx2();
		testEx2.say2();
	   
	}
	
	public void addN(){
		 this.n ++;
		 System.out.println(n);
	}
	
	public int getN(){
		return this.n;
	}
	
	public void say(){
		//System.out.println(1/0);
		testEx2.say3();
		
	}

}
