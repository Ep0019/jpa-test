package com.lhy.netty.jaxb;

import javax.xml.bind.annotation.XmlElement;

import com.lhy.netty.httpxml.Customer;

/**
 * @author: 李慧勇
 * @description:
 * @mail:jdlglhy@163.com
 * @2015年7月13日
 * @version 1.0
 */
public class MapElements {
	@XmlElement
    public String key;
    
    @XmlElement
    public Customer value;
 
    @SuppressWarnings("unused")
    private MapElements() {
    } // Required by JAXB
 
    public MapElements(String key, Customer value) {
        this.key = key;
        this.value = value;
    }

}
