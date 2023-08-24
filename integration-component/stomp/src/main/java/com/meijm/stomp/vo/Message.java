package com.meijm.stomp.vo;

import lombok.Data;

/**
 * @author mjm
 * @createtime $date$ $time$
 **/
@Data
public class Message {
	//发送者
	private String username;
	//发送者头像
	private String avatar;
	//消息体
	private String content;
	//发时间
	private String sendTime;
	// 接受者
	private String receiver;
}
