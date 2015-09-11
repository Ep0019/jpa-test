package com.lhy.netty.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.lhy.netty.httpxml.Customer;

/**
 * @author: 李慧勇
 * @description:
 * @mail:jdlglhy@163.com
 * @2015年7月13日
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
@XmlRootElement
public class ListBean {
 
	private String name;
	private List list;
	@XmlElements({
        @XmlElement(name = "customer", type = Customer.class)
    })
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
	
}
