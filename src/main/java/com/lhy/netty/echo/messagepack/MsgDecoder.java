package com.lhy.netty.echo.messagepack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

import org.msgpack.MessagePack;
import org.msgpack.template.Template;

/**
 * @author: 李慧勇
 * @description:MessageDecoder decoder
 * @mail:jdlglhy@163.com
 * @2015年7月9日
 * @version 1.0
 */
public class MsgDecoder extends MessageToMessageDecoder<ByteBuf>{

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf msg,
			List<Object> out) throws Exception {
		final byte[] bytes;
		final int length=msg.readableBytes();
		bytes=new byte[length];
		msg.getBytes(msg.readerIndex(), bytes,0, length);
		MessagePack msgPack=new MessagePack();
		Template<UserMessInfo> tElm = msgPack.lookup(UserMessInfo.class);
		out.add(msgPack.read(bytes,tElm));
	}
	
}
