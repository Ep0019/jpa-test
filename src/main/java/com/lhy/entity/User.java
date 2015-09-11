package com.lhy.entity;

import io.searchbox.annotations.JestId;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.lhy.annotation.ElasticsSearchAno;
/**
 * user实体
 */
@Entity
@Table(name = "alq_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User{
	@JestId
	private Long id;
	private String userName = "";
	private String userPassword = "";// 用户密码
	@ElasticsSearchAno(isElasticsSearch=true)
	private String realName = "";// 真实名称
	private String mobile = "";// 电话号码
	private String tel = "";// 手机号码
	private String email = "";// 电子邮件
	@ElasticsSearchAno(isNeedIkSearch=true)
	@Column
	private String address = "";// 地址
	private Integer isLocked = 0;// 是否被锁定，0代表没有被锁定，1代表没锁定
	private Long times = 0L;// 登录的次数
	private Integer gender=0;//0未知1为男2为女
	private Role role;// 角色
	public Long getTimes() {
		return times;
	}

	public void setTimes(Long times) {
		this.times = times;
	}

	@Column(length = 1)
	public Integer getIsLocked() {
		return isLocked;
	}
	public void setIsLocked(Integer isLocked) {
		this.isLocked = isLocked;
	}

	@Column(length = 200)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(length = 100)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(length = 50)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(length = 50)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(length = 50)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(length = 200)
	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Column(length = 50)
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}
	@Id  
    @GeneratedValue(strategy = GenerationType.AUTO)  
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@ElasticsSearchAno(isElasticsSearch=true)
	@ManyToOne(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
}
