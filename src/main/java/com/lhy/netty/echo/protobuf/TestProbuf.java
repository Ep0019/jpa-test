package com.lhy.netty.echo.protobuf;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lhy.netty.echo.protobuf.ReqMsgProto.ReqMsg.Builder;

/**
 * @author: 李慧勇
 * @description:protobuf测试
 * @mail:jdlglhy@163.com
 * @2015年7月10日
 * @version 1.0
 */
public class TestProbuf {
	
	private static byte[] encode(ReqMsgProto.ReqMsg req){
		return req.toByteArray();
	}
	
	private static ReqMsgProto.ReqMsg decoder(byte[] bytes) throws InvalidProtocolBufferException{
		return ReqMsgProto.ReqMsg.parseFrom(bytes);
	}
	
	private static ReqMsgProto.ReqMsg createReqMsg(){
		Builder builder=ReqMsgProto.ReqMsg.newBuilder();
		builder.setProductName("netty");
		builder.setReqId(1);
		builder.setUserName("lihuiyong");
		List<String> addresses=new ArrayList<String>();
		addresses.add("xiamen");
		addresses.add("quanzhou");
		addresses.add("fujian");
		builder.addAllAddress(addresses);
		ReqMsgProto.ReqMsg reqMsg=builder.build();
		return reqMsg;
	}
	
	public static void main(String[] args) throws InvalidProtocolBufferException {
		ReqMsgProto.ReqMsg reqMsg=createReqMsg();
		System.out.println("--before encoder--");
		System.out.println(reqMsg.toString());
		System.out.println("--after encoder--");
		ReqMsgProto.ReqMsg reqMsg2=decoder(encode(reqMsg));
		System.out.println(reqMsg2.equals(reqMsg));
		
	}
	
}
