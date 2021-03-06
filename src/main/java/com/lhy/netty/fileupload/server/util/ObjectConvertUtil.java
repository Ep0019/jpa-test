package com.lhy.netty.fileupload.server.util;

import com.alibaba.fastjson.JSON;
import com.lhy.netty.fileupload.server.model.Event;
import com.lhy.netty.fileupload.server.model.RecvieMessage;
import com.lhy.netty.fileupload.server.model.RequestFile;
import com.lhy.netty.fileupload.server.model.ResponseFile;
import com.lhy.netty.fileupload.server.model.SecureModel;

/**
 * 传输数据转化包
 * @author Administrator
 *
 */
public class ObjectConvertUtil {
	
	public static String convertModle(SecureModel secureModel){
		RecvieMessage recevie = new RecvieMessage();
		recevie.setData(JSON.toJSONString(secureModel));
		recevie.setMsgType(Event.MESSAGE_TYPE_SECURE_MODEL);
		return JSON.toJSONString(recevie);
	}
	
	public static String convertModle(ResponseFile response){
		RecvieMessage recevie = new RecvieMessage();
		recevie.setData(JSON.toJSONString(response));
		recevie.setMsgType(Event.MESSAGE_TYPE_RESPONSE_FILE);
		return JSON.toJSONString(recevie);
	}
	
	public static String convertModle(RequestFile requst){
		RecvieMessage recevie = new RecvieMessage();
		recevie.setData(JSON.toJSONString(requst));
		recevie.setMsgType(Event.MESSAGE_TYPE_REQUEST_FILE);
		return JSON.toJSONString(recevie);
	}
	
	public static Object convertModle(String recviejson){
		RecvieMessage recvie = (RecvieMessage) JSON.parseObject(recviejson,RecvieMessage.class);
		Object obj = null ;
		switch(recvie.getMsgType()){
			case Event.MESSAGE_TYPE_SECURE_MODEL:
				obj = (SecureModel) JSON.parseObject(recvie.getData().toString(),SecureModel.class);
				break;
			case Event.MESSAGE_TYPE_REQUEST_FILE:
				obj = (RequestFile) JSON.parseObject(recvie.getData().toString(),RequestFile.class);
				break;
			case Event.MESSAGE_TYPE_RESPONSE_FILE:
				obj = (ResponseFile) JSON.parseObject(recvie.getData().toString(),ResponseFile.class);
				break;
		}
		return obj ;
	}
	
	public static String request(Object obj){
		if( obj instanceof SecureModel ) {
			SecureModel secureModel = (SecureModel)obj;
			return convertModle(secureModel);
		} else if ( obj instanceof RequestFile) {
			RequestFile requestFile = (RequestFile)obj;
			return convertModle(requestFile);
		} else if ( obj instanceof ResponseFile) {
			ResponseFile responseFile = (ResponseFile)obj;
			return convertModle(responseFile);
		} else {
			return null ;
		}
		
	}
	
	
}
