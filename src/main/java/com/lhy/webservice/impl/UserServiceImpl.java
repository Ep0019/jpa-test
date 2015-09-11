package com.lhy.webservice.impl;

import javax.jws.WebService;

import org.springframework.stereotype.Service;

import com.lhy.webservice.UserService;

/**
 * @author: 李慧勇
 * @description:
 * @mail:jdlglhy@163.com
 * @2015年6月29日
 * @version 1.0
 */
@WebService(endpointInterface="com.lhy.webservice.UserService")
@Service
public class UserServiceImpl implements UserService{
	
	 public boolean exitUser(){
		 System.out.println("success");
		 return true;
	 }

}
