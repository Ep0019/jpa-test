package com.lhy.netty.echo.LineBasedFrameDecoder;

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
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author: 李慧勇
 * @description:Echo服务器 tcp粘包拆包解决之道（一）
 * ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
 * 以"\n,\r\n,System.getProperty(line.separator)"来标识一个完整的信息报
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
					ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
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
			String body=(String) msg;
			System.out.println("server accept client message is:"+body);
			String resp="my name is lihuiyong."+"\n";
			ByteBuf writeBuff=Unpooled.copiedBuffer(resp.getBytes());
			ctx.writeAndFlush(writeBuff);
		}                                                                                                   
	}
	public static void main(String[] args) throws InterruptedException {
		new EchoServer().bind(8080);
	}
}
