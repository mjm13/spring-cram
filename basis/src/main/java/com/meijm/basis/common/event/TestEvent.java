package com.meijm.basis.common.event;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * Event - 通知
 *
 * @author bangmuju Team
 * @version 5.0
 */
public class TestEvent extends ApplicationEvent {

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
	public TestEvent(Object source, String type) {
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