package com.lhy.nio.test2;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang3.StringUtils;
/**
 * @author: 李慧勇
 * @description:netty AIO异步非阻塞 实现时间服务器（jdk1.7）
 * @mail:jdlglhy@163.com
 * @2015年7月8日
 * @version 1.0
 */
public class TimeServerAio implements Runnable {
	
	
	private AsynchronousServerSocketChannel asynServerSocketChannel;
	
	CountDownLatch countDownLatch;
	/*初始化*/
	public TimeServerAio(int port){
		try {
			asynServerSocketChannel=asynServerSocketChannel.open();
			asynServerSocketChannel.bind(new InetSocketAddress(port));
			System.out.println("时间服务器已启动，监听的端口为："+port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	@Override
	public void run() {
		countDownLatch=new CountDownLatch(1);
		doAccept();
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**  
	 * 处理客户端连接 
	 */
	public void doAccept(){
		asynServerSocketChannel.accept(this, new AcceptCompletionHandler());
	}
	
	/**  
	 * 接受客户端连接 
	 */
	public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, TimeServerAio>{

		@Override
		public void completed(AsynchronousSocketChannel result, TimeServerAio attachment) {
			/*当有别的客户端接入时，获得连接*/
			attachment.asynServerSocketChannel.accept(attachment,this);
			ByteBuffer readBuffer=ByteBuffer.allocate(1024);
			result.read(readBuffer, readBuffer, new readCompletionHandler(result));
		}
		@Override
		public void failed(Throwable exc, TimeServerAio attachment) {
			exc.printStackTrace();
			attachment.countDownLatch.countDown();
		}
	}
	/**  
	 * 读取客户端请求消息
	 */
	public class readCompletionHandler implements CompletionHandler<Integer,ByteBuffer>{

		private AsynchronousSocketChannel asynSsc;
		
		public readCompletionHandler(AsynchronousSocketChannel asynSsc){
			if(this.asynSsc == null){
				this.asynSsc=asynSsc;
			}
		}
		@Override
		public void completed(Integer result, ByteBuffer attachment) {
			attachment.flip();
			byte[] bytes=new byte[attachment.remaining()];
			attachment.get(bytes);
			try {
				String body=new String(bytes, "utf-8");
				System.out.println("服务器接受的查询指令为："+body);
				String currentTime="QUERY TIME ORDER".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"ERR QUERY ORDER";
				doWrite(currentTime);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
		}
		@Override
		public void failed(Throwable exc, ByteBuffer attachment) {
			exc.printStackTrace();
			try {
				this.asynSsc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/*服务器应答客户端消息*/
		public void doWrite(String body){
			if(!StringUtils.isEmpty(body)){
				byte[] bytes=body.getBytes();
				ByteBuffer writeBuffer=ByteBuffer.allocate(bytes.length);
				writeBuffer.put(bytes);
				writeBuffer.flip();
				asynSsc.write(writeBuffer,writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
					@Override
					public void completed(Integer result, ByteBuffer attachment) {
						while(attachment.hasRemaining()){
							asynSsc.write(attachment);
						}
					}
					@Override
					public void failed(Throwable exc, ByteBuffer attachment) {
						exc.printStackTrace();
						try {
							asynSsc.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			}
		}
	}
	
	
	public static void main(String[] args) {
		TimeServerAio timeServer=new TimeServerAio(8080);
		new Thread(timeServer).start();
	}
	
	
	
	
	
}
