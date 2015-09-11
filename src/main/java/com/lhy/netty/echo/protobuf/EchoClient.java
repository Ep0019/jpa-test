package com.lhy.netty.echo.protobuf;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.util.ArrayList;
import java.util.List;

import com.lhy.netty.echo.protobuf.ReqMsgProto.ReqMsg.Builder;
import com.lhy.netty.echo.protobuf.RespMsgProto.RespMsg;
/**
 * @author: 李慧勇
 * @description:protobuf编解码 
 * 1.protobuf 利用工具生成ReqMsgProto和RespMsgProto类
 * 2.添加protobug的解码和编码器new ProtobufDecoder(ReqMsgProto.ReqMsg.getDefaultInstance()); ch.pipeline().addLast(new ProtobufEncoder());                                                                                            
 * 3.ProtobufVarint32FrameDecoder和ProtobufVarint32LengthFieldPrepender解决半包问题
 * @mail:jdlglhy@163.com
 * @2015年7月9日
 * @version 1.0
 */
public class EchoClient {
	
	public void connect(String host,int port) throws InterruptedException{
		EventLoopGroup groups=new NioEventLoopGroup();
		try{
			Bootstrap bs=new Bootstrap();
			bs.group(groups).channel(NioSocketChannel.class).
			option(ChannelOption.TCP_NODELAY, true).
			handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
					ch.pipeline().addLast(new ProtobufDecoder(RespMsgProto.RespMsg.getDefaultInstance()));
					ch.pipeline().addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());
					ch.pipeline().addLast(new ProtobufEncoder());
					ch.pipeline().addLast(new EchoClientHandler());
				}
			});
			ChannelFuture channelFuture=bs.connect(host, port).sync();
			channelFuture.channel().closeFuture().sync();
		}finally{
			groups.shutdownGracefully();
		}
	}
	public class EchoClientHandler extends ChannelHandlerAdapter{
		
		private int counter;
		
		
		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			for(int i=0;i<1000;i++){
				ctx.write(getMsgReq(i));
			}
			ctx.flush();
		}
		private ReqMsgProto.ReqMsg getMsgReq(int i){
				Builder builder=ReqMsgProto.ReqMsg.newBuilder();
				builder.setProductName("netty"+i);
				builder.setReqId(i);
				builder.setUserName("lihuiyong"+i);
				List<String> addresses=new ArrayList<String>();
				addresses.add("xiamen"+i);
				addresses.add("quanzhou"+i);
				addresses.add("fujian"+i);
				builder.addAllAddress(addresses);
				return builder.build();
		} 
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			RespMsgProto.RespMsg respMsg=(RespMsg) msg;
			System.out.println("client accept server return message is:"+respMsg.getDesc()+"===="+ ++counter);
		}
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
				throws Exception {
			cause.printStackTrace();
			ctx.close();
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx)
				throws Exception {
			ctx.flush();
		}
		
	}
	public static void main(String[] args) throws InterruptedException {
		new EchoClient().connect("localhost", 8080);
	}
}
