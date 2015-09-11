package com.lhy.annotation;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.lhy.entity.User;

/**
 * @author: 李慧勇
 * @description:
 * @mail:jdlglhy@163.com
 * @2015年7月27日
 * @version 1.0
 */
public class GetJsonData{

	public static String getBuilderJson(User user) {
		String json = "";
		try {
			XContentBuilder contentBuilder = XContentFactory.jsonBuilder().startObject();
			contentBuilder.field("id",user.getId());
			contentBuilder.field("address", user.getAddress());
			contentBuilder.field("email",user.getEmail());
			contentBuilder.field("realName", user.getRealName());
			contentBuilder.field("mobile",user.getMobile());
			contentBuilder.field("roleName", user.getRole().getName());
			contentBuilder.field("descript",user.getRole().getDescript());
			contentBuilder.field("roleId",user.getRole().getId());
			json = contentBuilder.endObject().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

}
