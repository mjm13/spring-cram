package com.meijm.basis.event;

import org.springframework.context.ApplicationEvent;

import java.util.Map;


public class CustomObjectEvent extends ApplicationEvent {

	/**
	 * 参数
	 */
	private Map<String,String> obj;


	/**
	 * 构造方法
	 *
	 * @param source 事件源
	 * @param obj   参数
	 */
	public CustomObjectEvent(Object source, Map<String,String> obj) {
		super(source);
		this.obj = obj;
	}

	/**
	 * 获取类型
	 *
	 * @return 获取类型
	 */
	public Map<String,String> getObject() {
		return obj;
	}
}