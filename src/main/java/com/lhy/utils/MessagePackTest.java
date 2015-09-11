package com.lhy.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.msgpack.MessagePack;
import org.msgpack.template.Template;
import org.msgpack.template.Templates;

import com.lhy.serializable.UserInfo;

/**
 * @author: 李慧勇
 * @description:messagepack test
 * @mail:jdlglhy@163.com
 * @2015年7月9日
 * @version 1.0
 */
public class MessagePackTest {
	public static void main(String[] args) throws IOException {
		System.out.println(File.separatorChar+"-----"+File.separator+"----"+System.getProperty("user.dir"));
		List<String> src=new ArrayList<String>();
		src.add("asdfasdfadsf1");
		src.add("asdfasdfadsf2");
		src.add("asdfasdfadsf3");
		src.add("asdfasdfadsf4");
		src.add("asdfasdfadsf5");
		MessagePack msgPack=new MessagePack();
		byte[] bytes=msgPack.write(src);
		System.out.println(bytes.length);
		@SuppressWarnings({ "unchecked", "deprecation" })
		List<String> decoder=msgPack.read(bytes, Templates.tList(Templates.tString()));
		for(String d:decoder){
			System.out.println(d);
		}
		
		
		
		List<UserInfo> userInfos=new ArrayList<UserInfo>();
		for(int i=0;i<10;i++){
			UserInfo userInfo=new UserInfo();
			userInfo.buildUserId(Long.parseLong(String.valueOf(i))).buildUserName("zhangs"+i);
			userInfos.add(userInfo);
		}
		MessagePack msgPack2=new MessagePack();
		byte[] bytes2=msgPack.write(userInfos);
		System.out.println(bytes2.length);
		
		Template<UserInfo> tElm = msgPack2.lookup(UserInfo.class);

		List<UserInfo> decoder2=msgPack2.read(bytes2, Templates.tList(tElm));
		for(UserInfo u:decoder2){
			System.out.println(u.getUserName());
		}
	}
}
