package com.lhy.nio.test1;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


/**
 * @author: 李慧勇
 * @description:netty 多路复用选择器 实现时间服务器
 * @mail:jdlglhy@163.com
 * @2015年7月8日
 * @version 1.0
 */
public class TimeClient implements Runnable{
	
	private SocketChannel socketChannel;
	
	private Selector selector;
	
	private volatile boolean stop;
	
	private String host;
	
	private int port;
	
	
	public TimeClient(String host,int port) {
		this.port=port;
		this.host=host;
		try {
			socketChannel=SocketChannel.open();
			socketChannel.configureBlocking(false);
			selector=Selector.open();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void run() {
		try {
			connect();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
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
	/*连接服务器*/
	public void connect() throws IOException{
		if(socketChannel.connect(new InetSocketAddress(host, port))){
			socketChannel.register(selector, SelectionKey.OP_READ);
			doWrite(socketChannel);
		}else{
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
		}
	}
	/*向服务器发送请求消息*/
	public void doWrite(SocketChannel socketChannel) throws IOException{
		String req="QUERY TIME ORDER";
		byte[] bytes=req.getBytes();
		ByteBuffer writeBuffer=ByteBuffer.allocate(bytes.length);
		writeBuffer.put(bytes);
		writeBuffer.flip();
		socketChannel.write(writeBuffer);
		if(!writeBuffer.hasRemaining()){
			System.out.println(socketChannel.getLocalAddress()+"客户端发送命令成功！");
		}
	}
	private void handleKey(SelectionKey key) throws IOException{
		SocketChannel soc=(SocketChannel) key.channel();
		if(key.isValid()){
			if(key.isConnectable()){
				if(soc.finishConnect()){
					soc.register(selector,SelectionKey.OP_READ);
					doWrite(soc);
				}else{
					System.exit(1);
				}
			}
			if(key.isReadable()){
				ByteBuffer readBuffer=ByteBuffer.allocate(1024);
				int readBytes=soc.read(readBuffer);
				if(readBytes >0){
					readBuffer.flip();
					byte[] bytes=new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String body=new String(bytes, "utf-8");
					System.out.println("客户端接受的查询结果："+body);
					/*this.stop=true;*/
				}else if(readBytes < 0){
					key.cancel();
					soc.close();
				}else{
					/*未读到任何字节*/
				}
			}
		}
	}
	public void stop(){
		this.stop=true;
	}
	
	
	public static void main(String[] args) {
		TimeClient timeClient=new TimeClient("127.0.0.1", 8080);
		new Thread(timeClient).start();
	}
}
