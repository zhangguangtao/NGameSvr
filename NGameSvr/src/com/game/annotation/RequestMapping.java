package com.game.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 映射
 * @author zgt
 *
 */
@Retention(RetentionPolicy.RUNTIME)  
public @interface RequestMapping {
	String value() default "";
}
