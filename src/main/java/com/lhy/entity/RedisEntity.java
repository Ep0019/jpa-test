package com.lhy.entity;

import java.io.Serializable;
public class RedisEntity implements Serializable{
	private static final long serialVersionUID=-1L;
	private Long id;
	private String name = "";// 角色名称
	private String descript = "";// 角色描述
	
	
	public RedisEntity(Long id,String name,String descript) {
		this.id=id;
		this.name=name;
		this.descript=descript;
	}
	
	@Override
	public String toString() {
		return "RedisEntity [id=" + id + ", name=" + name + ", descript="
				+ descript + "]";
	}

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
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
