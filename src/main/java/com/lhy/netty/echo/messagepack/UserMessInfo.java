package com.lhy.netty.echo.messagepack;

import org.msgpack.annotation.Message;

/**
 * @author: 李慧勇
 * @description:UserMessInfo
 * @mail:jdlglhy@163.com
 * @2015年7月9日
 * @version 1.0
 */
@Message
public class UserMessInfo{

	private Long userId;
	
	private String userName;
	
	private int userAge;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}
	
}
