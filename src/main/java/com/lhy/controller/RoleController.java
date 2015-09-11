package com.lhy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lhy.aspect.SystemControllerLog;
import com.lhy.entity.Role;
import com.lhy.service.RoleService;

/**
 * @author: 李慧勇
 * @description:
 * @mail:jdlglhy@163.com
 * @2015年7月6日
 * @version 1.0
 */
@Controller
@RequestMapping("role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("add-role")
	@SystemControllerLog(description = "添加角色")
	public void addRole(){
		Role role=new Role();
		role.setName("aop6");
		role.setDescript("aop6日志管理");
		System.out.println(roleService.saveAndUpdate(role));
		
	}
	@RequestMapping("delete-role")
	@SystemControllerLog(description = "删除角色")  
	public void deleteRole(){
		Role role=roleService.get(1l);
		System.out.println(roleService.delete(role.getId()));
		
	}
	@RequestMapping("flush")
	public void flush(){
		roleService.flush();
	}
	@RequestMapping("get-role-list")
	public void getRoleByName(@RequestParam("roleName")String roleName){
		roleService.getRoleListByName(roleName);
	}
	
	@RequestMapping("repeat-form")
	public void TestRepeatForm(@Valid Role role,HttpServletResponse response) throws IOException, InterruptedException{
		   Thread.sleep(3000);
		  response.setContentType("text/plain;charset=UTF-8");    
		  PrintWriter out = null; 
		  out = response.getWriter();
		  roleService.saveAndUpdate(role);
		  System.out.println("sussess");
		  out.print("1");  
		  out.close();  

	}
	@RequestMapping("goto-addrole")
	public String gotoAddRole(Model model,HttpServletRequest request){
		String token=UUID.randomUUID().toString();
		model.addAttribute("token", token);
		request.getSession(true).setAttribute("token",token);
		return "after/form";
	}
	
}
