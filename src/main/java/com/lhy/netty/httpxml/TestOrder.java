package com.lhy.netty.httpxml;

import java.io.StringReader;
import java.io.StringWriter;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

/**
 * @author: 李慧勇
 * @description:测试绑定关系(此代码执行报错，原因是jibx需要处理xml和java对象的绑定关系)
 * @mail:jdlglhy@163.com
 * @2015年7月10日
 * @version 1.0
 */
public class TestOrder {
	
	
	public static void main(String[] args) throws JiBXException {
		IBindingFactory factory=BindingDirectory.getFactory(Customer.class);
		StringWriter writer = null;
	    StringReader reader = null;
	    Customer customer=null;
	    customer=new Customer();
	    customer.setCustomerId(1l);
	    customer.setName("lihuiyong");
		writer = new StringWriter();
        // marshal 编组
        IMarshallingContext mctx = factory.createMarshallingContext();
        mctx.setIndent(2);
        mctx.marshalDocument(customer, "UTF-8", null, writer);
        System.out.println(writer);
        
        reader = new StringReader(writer.toString());
        //unmarshal 解组
        IUnmarshallingContext uctx = factory.createUnmarshallingContext();
        Customer acc = (Customer) uctx.unmarshalDocument(reader, null);
        System.out.println(acc);
        //fail(acc);

	}
}
