package com.lhy.netty.httpxml;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

import com.lhy.netty.httpxml.code.HttpXmlRequestEncoder;
import com.lhy.netty.httpxml.code.HttpXmlResponseDecoder;
import com.lhy.netty.httpxml.msg.HttpXmlRequest;
import com.lhy.netty.httpxml.msg.HttpXmlResponse;


/**
 * @author: 李慧勇
 * @description:netty http+xml协议栈开发     
 * @2015年7月9日
 * @version 1.0
 */
public class HttpXmlClient {
	
	public void connect(String host,int port) throws InterruptedException{
		EventLoopGroup groups=new NioEventLoopGroup();
		try{
			Bootstrap bs=new Bootstrap();
			bs.group(groups).channel(NioSocketChannel.class).
			option(ChannelOption.TCP_NODELAY, true).
			handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("http-decoder", new HttpResponseDecoder());
					ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
					ch.pipeline().addLast("xml-decoder", new HttpXmlResponseDecoder(Order.class));
					
					ch.pipeline().addLast("http-encoder", new HttpRequestEncoder());
					ch.pipeline().addLast("xml-encoder", new HttpXmlRequestEncoder());
					ch.pipeline().addLast(new HttpXmlClientHandler());
				}
			});
			ChannelFuture channelFuture=bs.connect(host, port).sync();
			channelFuture.channel().closeFuture().sync();
		}finally{
			groups.shutdownGracefully();
		}
	}
	public class HttpXmlClientHandler extends SimpleChannelInboundHandler<HttpXmlResponse>{

		
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
				throws Exception {
			cause.printStackTrace();
			ctx.close();
		}

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			HttpXmlRequest request=new HttpXmlRequest(null, getOrder());
			ctx.writeAndFlush(request);
		}
		public Order getOrder(){
			Order order=new Order();
			order.setTotal(120.0f);
			order.setOrderNumber(10l);
			
			Customer c=new Customer();
			c.setCustomerId(1l);
			c.setName("lihuiyong");
			order.setCustomer(c);
			
			Address address=new Address();
			address.setAddressId(1l);
			address.setCountry("中国");
			address.setCode("zh");
			address.setCity("厦门");
			address.setPostCode("3333333");
			address.setStreet1("one");
			address.setStreet2("two");
			order.setBillTo(address);
			order.setShipTo(address);
			return order;
		} 
		@Override
		protected void messageReceived(ChannelHandlerContext ctx,
				HttpXmlResponse msg) throws Exception {
			System.out.println("client receive response of http header is:"+msg.getResponse().headers().names());
			System.out.println("client receive response of http body is:"+msg.getBody());
		}
		
	}
	public static void main(String[] args) throws InterruptedException {
		new HttpXmlClient().connect("localhost", 8080);
	}
}
