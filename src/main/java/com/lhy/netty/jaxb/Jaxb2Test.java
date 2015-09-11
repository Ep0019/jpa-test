package com.lhy.netty.jaxb;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lhy.netty.httpxml.Customer;
import com.lhy.netty.httpxml.Order;

/**
 * @author: 李慧勇
 * @description:http://hbiao68.iteye.com/blog/1958413
 * @mail:jdlglhy@163.com
 * @2015年7月13日
 * @version 1.0
 */
public class Jaxb2Test {
	
	private JAXBContext context = null;
    
    private StringWriter writer = null;
    private StringReader reader = null;
    
    /*private Address bean = null;*/
    private Order order=null;
    private ListBean listBean;
    
    @Before
    public void init() {
        /*Address bean = new Address();
        bean.setAddressId(1l);
        bean.setCity("1121");
        bean.setCode("45");
        bean.setPostCode("asdfadsf");
        bean.setStreet1("111");
        bean.setStreet2("22222");
        bean.setCountry("zg");
    	order =new Order();
    	order.setOrderNumber(1l);
    	order.setTotal(120.0f);
    	order.setBillTo(bean);
    	order.setShipTo(bean);*/
    	Customer c=new Customer();
    	c.setCustomerId(1l);
    	c.setName("lihuiyong");
    	listBean=new ListBean();
    	List<Object> list=new ArrayList<Object>();
    	list.add(c);
    	Customer c2=new Customer();
    	c2.setCustomerId(1l);
    	c2.setName("lihuiyong");
    	list.add(c2);
    	listBean.setList(list);
    	/*order.setCustomer(c);*/
    	/*order.setShipping(Shipping.DOMESTIC_EXPRESS);*/
        try {
            context = JAXBContext.newInstance(ListBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @After
    public void destory() {
        context = null;
        order = null;
        try {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.gc();
    }
    
    public void fail(Object o) {
        System.out.println(o);
    }
    
    public void failRed(Object o) {
        System.err.println(o);
    }
    @Test
    public void testBean2XML() {
        try {
            //下面代码演示将对象转变为xml
            Marshaller mar = context.createMarshaller();
            writer = new StringWriter();
            mar.marshal(order, writer);
            fail(writer);
            
            //下面代码演示将上面生成的xml转换为对象
            reader = new StringReader(writer.toString());
            Unmarshaller unmar = context.createUnmarshaller();
            order = (Order)unmar.unmarshal(reader);
            fail(order);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testBeanListXML() {
        try {
        	 context = JAXBContext.newInstance(ListBean.class);
             //下面代码演示将对象转变为xml
             Marshaller mar = context.createMarshaller();
             writer = new StringWriter();
             mar.marshal(listBean, writer);
             fail(writer);
             
             //下面代码演示将上面生成的xml转换为对象
             reader = new StringReader(writer.toString());
             Unmarshaller unmar = context.createUnmarshaller();
             listBean = (ListBean)unmar.unmarshal(reader);
             fail(listBean.getList().get(0));
             fail(listBean.getList().get(1));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testBeanMapXML() {
    	MapBean mapBean =new MapBean();
    	HashMap<String,Customer> map=new HashMap<String, Customer>();
    	Customer c=new Customer();
    	c.setCustomerId(1l);
    	c.setName("lihuiyong");
    	Customer c2=new Customer();
    	c2.setCustomerId(1l);
    	c2.setName("lihuiyong");
    	map.put("1", c);
    	map.put("2", c2);
    	mapBean.setMap(map);
        try {
        	 context = JAXBContext.newInstance(MapBean.class);
             //下面代码演示将对象转变为xml
             Marshaller mar = context.createMarshaller();
             writer = new StringWriter();
             mar.marshal(mapBean, writer);
             fail(writer);
             
             //下面代码演示将上面生成的xml转换为对象
             reader = new StringReader(writer.toString());
             Unmarshaller unmar = context.createUnmarshaller();
             mapBean = (MapBean)unmar.unmarshal(reader);
             fail(mapBean.getMap());

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
