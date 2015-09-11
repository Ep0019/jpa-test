package com.lhy.nio.test1;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
/**
 * @author: 李慧勇
 * @description:netty 多路复用选择器 实现时间服务器
 * @mail:jdlglhy@163.com
 * @2015年7月8日
 * @version 1.0
 */
public class TimeServer implements Runnable {
	
	private ServerSocketChannel serverSocketChannel;
	
	private Selector selector;
	
	private volatile boolean stop;
	
	
	/*初始化*/
	public TimeServer(int port){
		try {
			serverSocketChannel=serverSocketChannel.open();
			serverSocketChannel.socket().bind(new InetSocketAddress(8080), 1024);
			serverSocketChannel.configureBlocking(false);
			selector=Selector.open();
			serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
			System.out.println("时间服务器已启动，监听的端口为："+port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void run() {
		while(!stop){
			try {
				selector.select(1000);
				Set<SelectionKey> selectionKeys=selector.selectedKeys();
				Iterator<SelectionKey> iter=selectionKeys.iterator();
				SelectionKey key=null;
				while(iter.hasNext()){
					key=iter.next();
					iter.remove();
					try{
						handleKey(key);
					}catch(IOException ex){
						ex.printStackTrace();
						if(key != null){
							key.cancel();
							if(key.channel() != null){
								key.channel().close();
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(selector != null){
			try {
				selector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*处理io事件*/
	private void handleKey(SelectionKey key) throws IOException{
		if(key.isValid()){
			if(key.isAcceptable()){
				ServerSocketChannel serSocketChannel=(ServerSocketChannel) key.channel();
				SocketChannel sc=serSocketChannel.accept();
				sc.configureBlocking(false);
				sc.register(selector, SelectionKey.OP_READ);
			}
			if(key.isReadable()){
				SocketChannel socketChannel=(SocketChannel) key.channel();
				ByteBuffer readBuffer=ByteBuffer.allocate(1024);
				int readBytes=socketChannel.read(readBuffer);
				if(readBytes > 0){
					readBuffer.flip();
					byte[] bytes=new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String body=new String(bytes, "utf-8");
					System.out.println("服务器接收到的查询指令为："+body);
					String currentTime="QUERY TIME ORDER".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"ERR QUERY ORDER";
					writeResponse(socketChannel,currentTime);
				}else if(readBytes < 0){
					key.cancel();
					socketChannel.close();
				}else{
					/*未读到任何数据*/
				}
			}
		}
	}
	/*写数据到客户端*/
	public void writeResponse(SocketChannel socketChannel,String currentTime) throws IOException{
		if(!StringUtils.isEmpty(currentTime)){
			byte[] bytes=currentTime.getBytes();
			ByteBuffer writeBuffer=ByteBuffer.allocate(bytes.length);
			writeBuffer.put(bytes);
			writeBuffer.flip();
			socketChannel.write(writeBuffer);
		}
	}
	public void stop(){
		this.stop=true;
	}
	
	
	
	
	
	public static void main(String[] args) {
		TimeServer timeServer=new TimeServer(8080);
		new Thread(timeServer).start();
	}
	
	
	
	                                                                                                                                       
	
}
