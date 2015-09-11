package com.lhy.entity;

import io.searchbox.annotations.JestId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * role实体
 * 注解统一注解在get方法上面
 */
@Entity
@Table(name = "alq_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role{
	@JestId
	private Long id;
	private String name = "";// 角色名称
	private String descript = "";// 角色描述
	/*java1.8新特性测试
	public Role(String name,String descript){
		this.name=name;
		this.descript=descript;
	}*/
	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Id  
    @GeneratedValue(strategy = GenerationType.AUTO) 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
