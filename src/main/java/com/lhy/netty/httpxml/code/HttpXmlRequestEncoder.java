package com.lhy.netty.httpxml.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

import java.net.InetAddress;
import java.util.List;

import com.lhy.netty.httpxml.msg.HttpXmlRequest;

/**
 * @author: 李慧勇
 * @description:jaxb把java对象转化为xml。并封装成netty的byteBuf
 * @mail:jdlglhy@163.com
 * @2015年7月14日
 * @version 1.0
 */
public class HttpXmlRequestEncoder extends AbstractHttpXmlEncoder<HttpXmlRequest>{

	@Override
	protected void encode(ChannelHandlerContext ctx, HttpXmlRequest msg,
			List<Object> out) throws Exception {
		ByteBuf body=encode0(ctx,msg.getBody());
		FullHttpRequest request=msg.getRequest();
		if(request == null){
			request=new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/do", body);
			/*netty5.0*/
			HttpHeaders headers=request.headers();
			headers.set(HttpHeaderNames.HOST,InetAddress.getLocalHost().getHostAddress());
			headers.set(HttpHeaderNames.CONNECTION,HttpHeaderValues.CLOSE);
			headers.set(HttpHeaderNames.ACCEPT_ENCODING,HttpHeaderValues.GZIP.toString()+','+HttpHeaderValues.DEFLATE.toString());
			headers.set(HttpHeaderNames.ACCEPT_CHARSET,"ISO-8859-1,utf-8;q=0.7,*;q=0.7");
			headers.set(HttpHeaderNames.ACCEPT_LANGUAGE,"zh");
			headers.set(HttpHeaderNames.USER_AGENT,"netty xml http side");
			headers.set(HttpHeaderNames.ACCEPT,"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		}
		HttpHeaderUtil.setContentLength(request, body.readableBytes());
		out.add(request);
	}
}
