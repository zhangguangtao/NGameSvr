package com.game.service.net.servlet;

import java.lang.reflect.Method;

import com.game.annotation.RequestMapping;

/**
 * an action invocation represents an Action class stored in the servlet
 * 
 * @author needmorecode
 *
 */
public class ActionInvocation {
	
	private String actionName;
	
	private Object object;
	
	private Method method;
	
	public ActionInvocation(Object object, Method method){
		this.object = object;
		this.method = method;
		RequestMapping classMapping = object.getClass().getAnnotation(RequestMapping.class);
		RequestMapping methodMapping =method.getAnnotation(RequestMapping.class);
		String clazzMapp = null;
		String mthodMpp = null;
		if (classMapping!=null) {
			clazzMapp = classMapping.value();
		}
		if (methodMapping!=null) {
			mthodMpp=  methodMapping.value();
		}
		if (clazzMapp != null  && mthodMpp != null ) {
			this.setActionName("/"+clazzMapp + "/" + mthodMpp);
		}else {
			String className = object.getClass().getSimpleName();
			className = (char)(className.charAt(0) - ('A' - 'a')) + className.substring(1, className.indexOf("Action"));
			String methodName = method.getName();
			this.setActionName("/"+className + "/" + methodName);
		}
	}
	
	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	
	

}
