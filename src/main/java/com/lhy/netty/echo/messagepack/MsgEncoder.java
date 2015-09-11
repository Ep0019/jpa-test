package com.lhy.netty.echo.messagepack;

import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author: 李慧勇
 * @description:MessagePack encoder
 * @mail:jdlglhy@163.com
 * @2015年7月9日
 * @version 1.0
 */
public class MsgEncoder extends MessageToByteEncoder<Object>{

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out)
			throws Exception {
		MessagePack msgpPack=new MessagePack();
		byte[] bytes=msgpPack.write(msg);
		out.writeBytes(bytes);
	}

}
