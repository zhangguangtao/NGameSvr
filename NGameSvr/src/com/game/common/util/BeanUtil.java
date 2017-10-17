package com.game.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class BeanUtil implements ApplicationContextAware {

    private static ApplicationContext ctx;
    @Override
    public void setApplicationContext(ApplicationContext arg0)throws BeansException {
    	System.out.println("spring 初始化");
        ctx = arg0;
    }

    public static Object getBean(String beanName) {
        if(ctx == null){
            throw new NullPointerException();
        }
        return ctx.getBean(beanName);
    }
    
    public static <T> T getBean(Class<T> clazz) {
        if(ctx == null){
            throw new NullPointerException();
        }
        return ctx.getBean(clazz);
    }




}