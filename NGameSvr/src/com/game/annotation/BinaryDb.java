package com.game.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 入库 转为 二进制 存储
 * 从数据库 读取 转为 对象
 * @author zgt
 *
 */
@Retention(RetentionPolicy.RUNTIME)  
public @interface BinaryDb {

}
