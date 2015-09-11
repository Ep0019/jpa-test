package com.lhy.netty.echo.protobuf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import com.lhy.netty.echo.protobuf.ReqMsgProto.ReqMsg;
import com.lhy.netty.echo.protobuf.RespMsgProto.RespMsg.Builder;

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
public class EchoServer {
	
	public void bind(int port) throws InterruptedException{
		EventLoopGroup boss=new NioEventLoopGroup();
		EventLoopGroup worker=new NioEventLoopGroup();
		try{
			ServerBootstrap sbs=new ServerBootstrap();
			sbs.group(boss, worker).
			channel(NioServerSocketChannel.class).
			option(ChannelOption.SO_BACKLOG, 1024).
			childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
					ch.pipeline().addLast(new ProtobufDecoder(ReqMsgProto.ReqMsg.getDefaultInstance()));
					ch.pipeline().addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());
					ch.pipeline().addLast(new ProtobufEncoder());
					ch.pipeline().addLast(new EchoServerHandler());
				}
			});
			ChannelFuture f=sbs.bind(port).sync();
			System.out.println("服务器已启动监听端口为："+port);
			f.channel().closeFuture().sync();
		}finally{
			boss.shutdownGracefully();
			worker.shutdownGracefully();
		}
	}
	public class EchoServerHandler extends ChannelHandlerAdapter{
		
		private int counter;

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
				throws Exception {
			cause.printStackTrace();
			ctx.close();
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			ReqMsgProto.ReqMsg reqMsg=(ReqMsg) msg;
			System.out.println("the server accept client message is:"+reqMsg.getReqId()+"======"+ ++counter);
			Builder builder=RespMsgProto.RespMsg.newBuilder();
			builder.setReqId(reqMsg.getReqId());
			builder.setCode(reqMsg.getReqId());
			builder.setDesc("返回请求id为："+reqMsg.getReqId()+"的响应");
			ctx.writeAndFlush(builder.build());
		}                                                                                                   
	}
	public static void main(String[] args) throws InterruptedException {
		new EchoServer().bind(8080);
	}
}
