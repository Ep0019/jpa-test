package com.lhy.serializable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

import org.msgpack.annotation.Message;

/**
 * @author: 李慧勇
 * @description:userInfo序列化实体
 * @mail:jdlglhy@163.com
 * @2015年7月9日
 * @version 1.0
 */
@Message
public class UserInfo implements Serializable{
 
	private static final Long serialVersionUID=1l;
	
	private Long userId;
	
	private String userName;
	
	public UserInfo buildUserName(String userName){
		this.userName=userName;
		return this;
	}
	
	public UserInfo buildUserId(Long userId){
		this.userId=userId;
		return this;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public byte[] codeC(){
		ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
		byte[] userNameValue=this.userName.getBytes();
		byteBuffer.putInt(userNameValue.length);
		byteBuffer.put(userNameValue);
		byteBuffer.putLong(this.userId);
		byteBuffer.flip();
		userNameValue=null;
		byte[] result=new byte[byteBuffer.remaining()];
		byteBuffer.get(result);
		return result;
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		UserInfo userInfo=new UserInfo();
		userInfo=userInfo.buildUserId(1l).buildUserName("zhangsan");
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		ObjectOutputStream oos=new ObjectOutputStream(bos);
		oos.writeObject(userInfo);
		oos.flush();
		oos.close();
		byte[] b=bos.toByteArray();
		System.out.println("jdk serializa length is:"+b.length);
		bos.close();
		System.out.println("------------华丽分割线--------------");
		System.out.println("the byte array serializa is:"+userInfo.codeC().length);
		
                                                                                                                                		
		
		
	}
	
}
