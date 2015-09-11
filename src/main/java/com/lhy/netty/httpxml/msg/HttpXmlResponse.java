package com.lhy.netty.httpxml.msg;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @author: 李慧勇
 * @description:响应封装类
 * @mail:jdlglhy@163.com
 * @2015年7月14日
 * @version 1.0
 */
public class HttpXmlResponse {

	private FullHttpResponse response;
	
	private Object body;
	
	public HttpXmlResponse(FullHttpResponse response,Object body){
		this.response=response;
		this.body=body;
	}

	public FullHttpResponse getResponse() {
		return response;
	}

	public void setResponse(FullHttpResponse response) {
		this.response = response;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}
	
}
