package com.meijm.basis.event;

import org.springframework.context.ApplicationEvent;


public class CustomAsyncEvent extends ApplicationEvent {

	private static final long serialVersionUID = -4180050946434009635L;

	/**
	 * 类型
	 */
	private String  type;


	/**
	 * 构造方法
	 *
	 * @param source 事件源
	 * @param type   类型
	 */
	public CustomAsyncEvent(Object source, String type) {
		super(source);
		this.type = type;
	}

	/**
	 * 获取类型
	 *
	 * @return 获取类型
	 */
	public String getType() {
		return type;
	}
}