package com.lhy.netty.jaxb;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.lhy.netty.httpxml.Customer;

/**
 * @author: 李慧勇
 * @description:
 * @mail:jdlglhy@163.com
 * @2015年7月13日
 * @version 1.0
 */
@XmlRootElement
public class MapBean {
	
	private HashMap<String, Customer> map;
    
    @XmlJavaTypeAdapter(MapAdapter.class)
    public HashMap<String, Customer> getMap() {
        return map;
    }
    public void setMap(HashMap<String, Customer> map) {
        this.map = map;
    }
}
