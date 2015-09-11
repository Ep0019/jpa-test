package com.lhy.netty.httpxml.code;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

import java.util.List;

import com.lhy.netty.httpxml.msg.HttpXmlRequest;

/**
 * @author: 李慧勇
 * @description:请求消息解码类  把byteBuf封装的xml转换成java object对象
 * @mail:jdlglhy@163.com
 * @2015年7月14日
 * @version 1.0
 */
public class HttpXmlRequestDecoder extends AbstractHttpXmlDecoder<FullHttpRequest>{

	public HttpXmlRequestDecoder(Class<?> clazz) {
		super(clazz);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, FullHttpRequest msg,
			List<Object> out) throws Exception {
		if(!msg.decoderResult().isSuccess()){
			sendError(ctx, HttpResponseStatus.BAD_REQUEST);
			return;
		}
		Object object=decode0(ctx,msg.content());
		HttpXmlRequest request=new HttpXmlRequest(msg, object);
		out.add(request);
	}
	private static void sendError(ChannelHandlerContext ctx,HttpResponseStatus status){
		FullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer("Failure:"+status.toString()+"\r\n", CharsetUtil.UTF_8));
		response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain; charset=UTF-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
	
}
