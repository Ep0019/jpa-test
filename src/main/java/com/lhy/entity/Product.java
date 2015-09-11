
package com.lhy.entity;
import io.searchbox.annotations.JestId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.lhy.annotation.ElasticsSearchAno;

/**
 * 商品表(elasticsearch测试)
 */
@Entity
@Table(name = "alq_product",indexes={@Index(columnList="name",name="name")})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product{
	
	@JestId
	private Long id;
	private String sellerName;//卖家名称
	
	@ElasticsSearchAno(isNeedIkSearch=true)
	private String productNum;//商品货号
	private String productCode;//商品编号（自动生成zte+商品类型id+basejar时间戳）
	
	private String productBrandName;//品牌名称
	
	@ElasticsSearchAno(isNeedIkSearch=true)
	private String brandName;//类型下面的品牌
	
	@ElasticsSearchAno(isNeedIkSearch=true)
	private String name;//商品名字 唯一
	private String subtitle;
	private String keywords;
	private String metaDescription;
	
	@ElasticsSearchAno(isNeedIkSearch=true)
	private String originPlace;//原产地
	private Integer isSpecification=0;//是否有规格 0无 1有
	private Long storeNum=0l;//总库存
	
	@ElasticsSearchAno(isNeedIkSearch=true)
	private String describer;//商品描述
	
	@ElasticsSearchAno(isNeedIkSearch=true)
	private String details;//商品详情
	@Id  
    @GeneratedValue(strategy = GenerationType.AUTO) 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getProductNum() {
		return productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductBrandName() {
		return productBrandName;
	}

	public void setProductBrandName(String productBrandName) {
		this.productBrandName = productBrandName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public String getOriginPlace() {
		return originPlace;
	}

	public void setOriginPlace(String originPlace) {
		this.originPlace = originPlace;
	}

	public Integer getIsSpecification() {
		return isSpecification;
	}

	public void setIsSpecification(Integer isSpecification) {
		this.isSpecification = isSpecification;
	}

	public Long getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(Long storeNum) {
		this.storeNum = storeNum;
	}

	public String getDescriber() {
		return describer;
	}

	public void setDescriber(String describer) {
		this.describer = describer;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	
}

