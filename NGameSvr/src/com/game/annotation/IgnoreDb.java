package com.game.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 插入数据库时 忽略 字段  
 * @author zgt
 *
 */
@Retention(RetentionPolicy.RUNTIME) 
public @interface IgnoreDb {

}
