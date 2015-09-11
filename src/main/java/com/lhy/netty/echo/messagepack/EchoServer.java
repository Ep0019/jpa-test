package com.lhy.netty.echo.messagepack;

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
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * @author: 李慧勇
 * @description:messagepack编解码                                                                                                    
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
					ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2,0,2));
					ch.pipeline().addLast("decoder", new MsgDecoder());
					ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
					ch.pipeline().addLast("encoder",new MsgEncoder());
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
			ctx.close();
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			System.out.println(msg.getClass().getName());
			UserMessInfo u=(UserMessInfo) msg;
			System.out.println(u.getUserName());
			System.out.println("the server accept client message is:"+msg+"======"+ ++counter);
			ctx.writeAndFlush(msg);
		}                                                                                                   
	}
	public static void main(String[] args) throws InterruptedException {
		new EchoServer().bind(8080);
	}
}
