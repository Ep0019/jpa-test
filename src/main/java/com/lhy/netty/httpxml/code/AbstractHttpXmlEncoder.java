package com.lhy.netty.httpxml.code;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/**
 * @author: 李慧勇
 * @description:jaxb把java对象转化为xml。并封装成netty的byteBuf
 * @mail:jdlglhy@163.com
 * @2015年7月14日
 * @version 1.0
 */
public abstract class AbstractHttpXmlEncoder<T> extends MessageToMessageEncoder<T>{

	JAXBContext context = null;
    StringWriter writer = null;
    
	protected ByteBuf encode0(ChannelHandlerContext ctx,Object body)
			throws Exception {
		context = JAXBContext.newInstance(body.getClass());
		Marshaller mar = context.createMarshaller();
		writer = new StringWriter();
		mar.marshal(body, writer);
		String xmlStr=writer.toString();
		writer.close();
		writer = null;
		ByteBuf encodeBuf=Unpooled.copiedBuffer(xmlStr,CharsetUtil.UTF_8);
		return encodeBuf;
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		if(writer != null){
			writer.close();
			writer=null;
		}
	}
	
}
