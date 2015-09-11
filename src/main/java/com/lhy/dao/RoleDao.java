package com.lhy.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lhy.entity.Role;

/**
 * @author: 李慧勇
 * @description:继承JpaRepository（空的接口）
 * @mail:jdlglhy@163.com
 * @version 1.0
 */
public interface RoleDao extends JpaRepository<Role, Long>{
	
	@Query
	("FROM Role role WHERE role.name=:roleName")
	public List<Role> getRoleByName(@Param("roleName")String roleName);

}
