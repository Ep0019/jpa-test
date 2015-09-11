package com.lhy.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lhy.entity.Role;
/**
 * @author: 角色业务接口
 * @description:
 * @mail:jdlglhy@163.com
 * @version 1.0
 */
public interface RoleService{
	
	public void flush();
	
	public boolean saveAndUpdate(Role role);
	
	public boolean delete(Long roleId);
	
	public Role get(Long roleId);
	
	
	public List<Role> getRoleListByName(String roleName);
	
	public void insert1();
	public void insert2(String sql);
	public Role insert3();
	public void insert4(Role role);
	public Role insert5(Role role);
	public void insert6(String arg1,Long arg2);
	
	
	
	public void saveBatch(List<Role> list);
	public void updateBatch(List<Role> list);
	
	
}
