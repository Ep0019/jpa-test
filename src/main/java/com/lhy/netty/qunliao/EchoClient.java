package com.lhy.netty.qunliao;

import io.netty.bootstrap.Bootstrap;
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
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;


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
					ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
					ch.pipeline().addLast(new StringDecoder());
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
		
		private byte[] req;
		
		public EchoClientHandler(){
			this.req=("what is your name?"+"\n").getBytes();
		}
		
		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			ByteBuf writeBuff=null;
			for(int i=0;i<100;i++){
				writeBuff=Unpooled.buffer(req.length);
				writeBuff.writeBytes(req);
				ctx.writeAndFlush(writeBuff);
			}
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			String body =(String)msg;
			System.out.println("client accept server return message is:"+body);
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
				throws Exception {
			ctx.close();
		}
	}
	public static void main(String[] args) throws InterruptedException {
		new EchoClient().connect("localhost", 8080);
	}
}
