package com.lhy.netty.httpxml.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.util.List;

import com.lhy.netty.httpxml.msg.HttpXmlResponse;

/**
 * @author: 李慧勇
 * @description:jaxb把java对象转化为xml。并封装成netty的byteBuf
 * @mail:jdlglhy@163.com
 * @2015年7月14日
 * @version 1.0
 */
public class HttpXmlResponseEncoder extends AbstractHttpXmlEncoder<HttpXmlResponse>{

	@Override
	protected void encode(ChannelHandlerContext ctx, HttpXmlResponse msg,
			List<Object> out) throws Exception {
		ByteBuf body=encode0(ctx,msg.getBody());
		FullHttpResponse response=msg.getResponse();
		if(response == null){
			response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, body);
		}else{
			response=new DefaultFullHttpResponse(msg.getResponse().protocolVersion(),msg.getResponse().status(),body);
		}
		response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/xml");
		HttpHeaderUtil.setContentLength(response, body.readableBytes());
		out.add(response);
	}
}
