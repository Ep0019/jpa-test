package com.lhy.netty.echo.messagepack;

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
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: 李慧勇
 * @description:messagepack编解码 
 * 1.MsgDecoder和MsgEncoder分别为messagepack的解码编码类，负责对象的序列化和反序列化
 * 2.序列化的对象（这里以UserMessInfo为例，必须注解为@Message）
 * 3.LengthFieldBasedFrameDecoder(65535, 0, 2,0,2)和LengthFieldPrepender(2)解决tcp粘包问题
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
					ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2,0,2));
					ch.pipeline().addLast("decoder", new MsgDecoder());
					ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
					ch.pipeline().addLast("encoder",new MsgEncoder());
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
			List<UserMessInfo> list=rtnUserMessInfo();
			for(UserMessInfo us:list){
				ctx.write(us);
			}
			ctx.flush();
			list=null;
		}
		private List<UserMessInfo> rtnUserMessInfo(){
			List<UserMessInfo> list=new ArrayList<UserMessInfo>();
			UserMessInfo u=null;
			for(int i=0;i<100;i++){
				u=new UserMessInfo();
				u.setUserId(Long.parseLong(String.valueOf(i)));
				u.setUserAge(i);
				u.setUserName("zhang-->"+i);
				list.add(u);
			}
			return list;
		} 
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			UserMessInfo u=(UserMessInfo) msg;
			System.out.println("client accept server return message is:"+msg+"===="+ ++counter);
			System.out.println(u.getUserName());
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
				throws Exception {
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
