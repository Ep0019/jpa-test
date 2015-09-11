package com.lhy.netty.jaxb;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.lhy.netty.httpxml.Customer;

/**
 * @author: 李慧勇
 * @description:
 * @mail:jdlglhy@163.com
 * @2015年7月13日
 * @version 1.0
 */
public class MapAdapter extends XmlAdapter<MapElements[], Map<String, Customer>> {
	public MapElements[] marshal(Map<String, Customer> arg0) throws Exception {
        MapElements[] mapElements = new MapElements[arg0.size()];
 
        int i = 0;
        for (Map.Entry<String, Customer> entry : arg0.entrySet())
            mapElements[i++] = new MapElements(entry.getKey(), entry.getValue());
 
        return mapElements;
    }
 
    public Map<String, Customer> unmarshal(MapElements[] arg0) throws Exception {
        Map<String, Customer> r = new HashMap<String, Customer>();
        for (MapElements mapelement : arg0)
            r.put(mapelement.key, mapelement.value);
        return r;
    }
}
