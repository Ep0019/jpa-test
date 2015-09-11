package com.lhy.nio.test2;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;


/**
 * @author: 李慧勇
 * @description:netty AIO异步非阻塞  实现时间服务器（jdk1.7）
 * @mail:jdlglhy@163.com
 * @2015年7月8日
 * @version 1.0
 */
public class TimeClientAio implements CompletionHandler<Void, TimeClientAio>,Runnable{
	
	private AsynchronousSocketChannel asynchronousSocketChannel;
	
    CountDownLatch countDownLatch;
    
    private int port;
    
    private String host;
	
	public TimeClientAio(String host,int port) {
		this.port=port;
		this.host=host;
		try {
			asynchronousSocketChannel=AsynchronousSocketChannel.open();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	@Override
	public void run() {
		countDownLatch=new CountDownLatch(1);
		asynchronousSocketChannel.connect(new InetSocketAddress(host, port), this, this);
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			asynchronousSocketChannel.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	@Override
	public void completed(Void result, TimeClientAio attachment) {
		String req="QUERY TIME ORDER";
		byte[] bytes=req.getBytes();
		ByteBuffer writeBuffer=ByteBuffer.allocate(bytes.length);
		writeBuffer.put(bytes);
		writeBuffer.flip();
		asynchronousSocketChannel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {

			@Override
			public void completed(Integer result, ByteBuffer attachment) {
				if(attachment.hasRemaining()){
					asynchronousSocketChannel.write(attachment, attachment, this);
				}else{
					ByteBuffer readBuffer=ByteBuffer.allocate(1024);
					asynchronousSocketChannel.read(readBuffer, readBuffer, new CompletionHandler<Integer,ByteBuffer>() {

						@Override
						public void completed(Integer result,
								ByteBuffer attachment) {
							attachment.flip();
							byte[] bytes=new byte[attachment.remaining()];
							attachment.get(bytes);
							String body=null;
							try {
								body=new String(bytes, "utf-8");
								System.out.println("客户端接受到服务器返回的数据："+body);
								countDownLatch.countDown();
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void failed(Throwable exc, ByteBuffer attachment) {
							exc.printStackTrace();
							try {
								asynchronousSocketChannel.close();
								countDownLatch.countDown();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				exc.printStackTrace();
				try {
					asynchronousSocketChannel.close();
					countDownLatch.countDown();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void failed(Throwable exc, TimeClientAio attachment) {
		exc.printStackTrace();
		countDownLatch.countDown();
	}
	public static void main(String[] args) {
		for(int i=0;i<1000;i++){
			TimeClientAio timeClient=new TimeClientAio("127.0.0.1", 8080);
			new Thread(timeClient).start();
		}
	}
}
