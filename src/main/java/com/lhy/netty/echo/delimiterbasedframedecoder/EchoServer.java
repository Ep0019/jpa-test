package com.lhy.netty.echo.delimiterbasedframedecoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author: 李慧勇
 * @description:Echo服务器 tcp粘包拆包解决之道（一）
 * ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, buff));
 * 自定义分割符解决TCP粘包拆包问题
 *  ch.pipeline().addLast(new StringDecoder());
 *     流对象转化为字符串                                                                                                           
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
			option(ChannelOption.SO_BACKLOG, 100).
			childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					 /*ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, buff));
					  自定义分割符解决TCP粘包拆包问题*/
					ByteBuf buff=Unpooled.copiedBuffer("$_".getBytes());
					ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, buff));
					ch.pipeline().addLast(new StringDecoder());
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

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
				throws Exception {
			ctx.close();
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			System.out.println(msg);
			String body=(String) msg;
			System.out.println("server accept client message is:"+body);
			String resp="my name is lihuiyong."+"$_";
			ByteBuf writeBuff=Unpooled.copiedBuffer(resp.getBytes());
			ctx.writeAndFlush(writeBuff);
		}                                                                                                   
	}
	public static void main(String[] args) throws InterruptedException {
		new EchoServer().bind(8080);
	}
}
