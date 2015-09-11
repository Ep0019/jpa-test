package com.lhy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author: 李慧勇
 * @description:
 * @mail:jdlglhy@163.com
 * @version 1.0
 */
@Controller
@RequestMapping("main")
public class MainController {
	
	@RequestMapping("index")
	public String index(){
		return "after/ind";
	}
	@RequestMapping("home")
	public String home(){
		return "after/home";
	}
	@RequestMapping("left")
	public String left(){
		return "after/left";
	}
	
	
	@RequestMapping("test")
	public String test(){
		return "easyui/easyui";
	}
	
	
	@RequestMapping("datagrid")
	public String datagrid(){
		return "after/datagrid";
	}
	@RequestMapping("websocket")
	public String websocket(){
		return "after/websocket";
	}
	
	
	@RequestMapping("pic")
	public String pic(){
		return "after/pic";
	}
	/**
	 * @Title: getReqAndResp
	 * @Description:springMvc获取request和response
	 * @param: @return   
	 * @return: String   
	 * @throws
	 */
	@RequestMapping("getmvcrequestandresponse")
	public String getReqAndResp(){
		HttpServletRequest request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpServletResponse response=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
		return "after/pic";
	}
	@RequestMapping("to-baidumap")
	public String goToBaiduMap(){
		return "test/baidu";
	}
	
	
	@RequestMapping("to-baidumap2")
	public String goToBaiduMap2(){
		return "test/baidu2";
	}
	
	@RequestMapping("to-baidumap3")
	public String goToBaiduMap3(){
		return "test/baidu3";
	}
	
	@RequestMapping("to-baidumap4")
	public String goToBaiduMap4(){
		return "test/baidusearch";
	}
	@RequestMapping("to-baidumap6")
	public String goToBaiduMap6(){
		return "test/baidu7";
	}
	@RequestMapping("baidusearch2")
	public String goToBaiduSearch2(){
		return "test/baidusearch2";
	}
	@RequestMapping("baidusearch3")
	public String goToBaiduSearch3(){
		return "test/baidusearch3";
	}
	@RequestMapping("baidusearch4")
	public String goToBaiduSearch4(){
		return "test/baidusearch4";
	}
	@RequestMapping("baidusearch5")
	public String goToBaiduSearch5(){
		return "test/baidusearch5";
	}
	@RequestMapping("fangtianxia1")
	public String goToFangTianxia(){
		return "test/fangtianxia1";
	}
	@RequestMapping("fangtianxia2")
	public String goToFangTianxia2(){
		return "test/fangtianxia2";
	}
	@RequestMapping("fangtianxia3")
	public String goToFangTianxia3(){
		return "test/fangtianxia3";
	}
	@RequestMapping("fangtianxia4")
	public String goToFangTianxia4(){
		return "test/fangtianxia4";
	}
	@RequestMapping("fangtianxia5")
	public String goToFangTianxia5(){
		return "test/fangtianxia5";
	}
	
 
 
 
 
}
