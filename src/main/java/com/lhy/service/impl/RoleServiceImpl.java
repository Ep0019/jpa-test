package com.lhy.service.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lhy.aspect.SystemServiceLog;
import com.lhy.dao.RoleDao;
import com.lhy.entity.Role;
import com.lhy.service.RoleService;

/**
 * @author: 李慧勇
 * @description:角色业务接口实现
 * @mail:jdlglhy@163.com
 * @version 1.0
 */
@Service
@Transactional(readOnly=true)
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleDao roleDao;
	
	@PersistenceContext
	private EntityManager manager;
	
	
	
	

	@Override
	@Transactional(readOnly=false)
	@SystemServiceLog(description = "保存角色")  
	@CacheEvict(value="mallListCache",allEntries=true)
	public boolean saveAndUpdate(Role role) {
		System.out.println("向数据库插入数据。。。。");
		try{
			roleDao.save(role);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
	@Override
	@Transactional(readOnly=false)
	public void saveBatch(List<Role> list) {
		  for (int i = 0; i < list.size(); i++) {
			  	manager.persist(list.get(i)); //变成托管状态
			    if (i % 10 == 0) {
			    	manager.flush(); //变成持久化状态
			    	manager.clear(); //变成游离状态
			   }
		  }
	 }
	@Override
	@Transactional(readOnly=false)
	public void updateBatch(List<Role> list) {
		  for (int i = 0; i < list.size(); i++) {
			  manager.merge(list.get(i)); //变成托管状态
		   if (i % 10 == 0) {
			   manager.flush(); //变成持久化状态
			   manager.clear(); //变成游离状态
		   }
		  }
	 }

	@Override
	@Transactional(readOnly=false)
	public boolean delete(Long roleId) {
		try{
			roleDao.delete(roleId);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public Role get(Long roleId) {
		return roleDao.findOne(roleId);
	}

	@Override
	public void insert1() {
		System.out.println("insert1====无参数");
	}
	@Override
	public void insert2(String sql) {
		System.out.println("insert2====有参数："+sql);
	}
	@Override
	public Role insert3() {
		Role role=new Role();
		role.setName("aop");
		System.out.println("insert3====有返回值："+role.getName()+"hhhhh");
		return role;
	}

	@Override
	public void insert4(Role role) {
		System.out.println("insert4=====拦截参数为：role的方法");
	}
	
	
	@Override
	public Role insert5(Role role) {
		System.out.println("insert5=====拦截参数为：role的方法");
		return role;
	}

	@Override
	public void insert6(String arg1, Long arg2) {
		System.out.println("insert6=====拦截两个参数的方法,参数1："+arg1+"==参数2："+arg2);
	}
	@Override
	@Cacheable(value = "mallListCache")
	public List<Role> getRoleListByName(String roleName) {
		return roleDao.getRoleByName(roleName);
	}
	
	@Override
	@CacheEvict(value="mallListCache",allEntries=true)
	public void flush(){
		System.out.println("清空了缓存");
	}
	
	
	
	
	
	
}
