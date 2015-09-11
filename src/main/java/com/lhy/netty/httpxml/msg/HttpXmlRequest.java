package com.lhy.netty.httpxml.msg;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author: 李慧勇
 * @description:请求封装类
 * @mail:jdlglhy@163.com
 * @2015年7月14日
 * @version 1.0
 */
public class HttpXmlRequest {
	
	private FullHttpRequest request;
	
	private Object body;
	
	public HttpXmlRequest(FullHttpRequest request,Object body){
		this.request=request;
		this.body=body;
	}

	public FullHttpRequest getRequest() {
		return request;
	}

	public void setRequest(FullHttpRequest request) {
		this.request = request;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}
	
}
