package com.lhy.entity;

import com.lhy.annotation.ElasticsSearchAno;

import io.searchbox.annotations.JestId;


public class Home{
	@JestId
	private Long id;
	@ElasticsSearchAno(isNeedIkSearch=true)
	private String title;//标题  满五唯一 免税学区房
	@ElasticsSearchAno(isNeedIkSearch=true)
	private String vlillageName;//小区名称   珠江帝景
	@ElasticsSearchAno(isNeedIkSearch=true)
	private String square;//平方米 117平方米 
	@ElasticsSearchAno(isNeedIkSearch=true)
	private String houseType;//户型 2室2厅
	@ElasticsSearchAno(isNeedIkSearch=true)
	private String time;//年代 2004年建板楼
	@ElasticsSearchAno(isNeedIkSearch=true)
	private String feature;//特点 满5年唯一 独家 有钥匙
	@ElasticsSearchAno(isNeedIkSearch=false)
	private String mydefine;
	
	public String getMydefine() {
		return mydefine;
	}

	public void setMydefine(String mydefine) {
		this.mydefine = mydefine;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVlillageName() {
		return vlillageName;
	}

	public void setVlillageName(String vlillageName) {
		this.vlillageName = vlillageName;
	}

	public String getSquare() {
		return square;
	}

	public void setSquare(String square) {
		this.square = square;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}
	
}
