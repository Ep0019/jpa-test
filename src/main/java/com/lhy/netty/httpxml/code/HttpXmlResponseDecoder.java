package com.lhy.netty.httpxml.code;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;

import java.util.List;

import com.lhy.netty.httpxml.msg.HttpXmlResponse;

/**
 * @author: 李慧勇
 * @description:
 * @mail:jdlglhy@163.com
 * @2015年7月14日
 * @version 1.0
 */
public class HttpXmlResponseDecoder extends AbstractHttpXmlDecoder<FullHttpResponse>{

	public HttpXmlResponseDecoder(Class<?> clazz) {
		super(clazz);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, FullHttpResponse msg,
			List<Object> out) throws Exception {
		Object object=decode0(ctx,msg.content());
		HttpXmlResponse response=new HttpXmlResponse(msg, object);
		out.add(response);
	}
	
}
