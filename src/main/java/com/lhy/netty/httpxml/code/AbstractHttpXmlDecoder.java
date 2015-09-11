package com.lhy.netty.httpxml.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.io.StringReader;
import java.nio.charset.Charset;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 * @author: 李慧勇
 * @description:请求消息解码类  把byteBuf封装的xml转换成java object对象
 * @mail:jdlglhy@163.com
 * @2015年7月14日
 * @version 1.0
 */
public abstract class AbstractHttpXmlDecoder<T> extends MessageToMessageDecoder<T>{

	private JAXBContext context = null;
    private StringReader reader = null;
    private Class<?> clazz;
    private final static String CHARSET_NAME="UTF-8";
    private final static Charset UTF_8=Charset.forName(CHARSET_NAME);
    protected AbstractHttpXmlDecoder(Class<?> clazz) {
		this.clazz=clazz;
   	}
	protected Object decode0(ChannelHandlerContext ctx,ByteBuf body)
			throws Exception {
	     context = JAXBContext.newInstance(clazz);
	     String content=body.toString(UTF_8);
	     System.out.println("the body is:"+content);
	     reader = new StringReader(content.toString());
	     Unmarshaller unmar = context.createUnmarshaller();
         Object result= unmar.unmarshal(reader);
         reader.close();
         reader=null;
		 return result;
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		if(reader != null){
			reader.close();
			reader=null;
		}
	}
	
}
