package com.lhy.netty.httpxml;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import com.lhy.netty.httpxml.code.HttpXmlRequestDecoder;
import com.lhy.netty.httpxml.code.HttpXmlResponseEncoder;
import com.lhy.netty.httpxml.msg.HttpXmlRequest;
import com.lhy.netty.httpxml.msg.HttpXmlResponse;

/**
 * @author: 李慧勇
 * @description:netty http+xml协议栈开发                                                                                                 
 * @mail:jdlglhy@163.com
 * @2015年7月9日
 * @version 1.0
 */
public class HttpXmlServer {
	
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
					ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
					ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
					ch.pipeline().addLast("xml-decoder", new HttpXmlRequestDecoder(Order.class));
					
					ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
					ch.pipeline().addLast("xml-encoder", new HttpXmlResponseEncoder());
					ch.pipeline().addLast(new HttpXmlServerHandler());
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
	public class HttpXmlServerHandler extends SimpleChannelInboundHandler<HttpXmlRequest>{

		@Override
		protected void messageReceived(final ChannelHandlerContext ctx,
				HttpXmlRequest msg) throws Exception {
			HttpRequest request=msg.getRequest();
			Order order=(Order) msg.getBody();
			System.out.println(order.getBillTo());
			System.out.println(order.getCustomer());
			System.out.println(order.getOrderNumber());
			System.out.println("the server receive client is:"+order);
			//业务逻辑处理；；；；省略
			ChannelFuture f=ctx.writeAndFlush(new HttpXmlResponse(null, order));
			if(!HttpHeaderUtil.isKeepAlive(request)){
				f.addListener(new GenericFutureListener<Future<? super Void>>() {
					@Override
					public void operationComplete(Future<? super Void> future)
							throws Exception {
						ctx.close();
					}
				});
			}
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
				throws Exception {
			cause.printStackTrace();
			if(ctx.channel().isActive()){
				sendError(ctx,HttpResponseStatus.INTERNAL_SERVER_ERROR);
			}
		}
		private void sendError(ChannelHandlerContext ctx,HttpResponseStatus status){
			FullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer("Failure:"+status.toString()+"\r\n", CharsetUtil.UTF_8));
			response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain; charset=UTF-8");
			ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
		}
		
	}
	public static void main(String[] args) throws InterruptedException {
		new HttpXmlServer().bind(8080);
	}
}
