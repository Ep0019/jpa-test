package com.lhy.netty.httpxml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author: 李慧勇
 * @description:customer
 * @mail:jdlglhy@163.com
 * @2015年7月10日
 * @version 1.0
 */
@XmlRootElement
public class Customer {

	private Long customerId;
	
	private String name;
	@XmlElement
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
