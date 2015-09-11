package com.lhy.netty.httpxml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author: 李慧勇
 * @description:订单类
 * @mail:jdlglhy@163.com
 * @2015年7月10日
 * @version 1.0
 */
@XmlRootElement(name="order")
public class Order {

	private Long orderNumber;
	
	private Float total;
	
	private Address billTo;
	
	private Address shipTo;
	
	private Shipping shipping;
	
	private Customer customer;
	@XmlElement
	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}
	@XmlElement
	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}
	@XmlElement
	public Address getBillTo() {
		return billTo;
	}

	public void setBillTo(Address billTo) {
		this.billTo = billTo;
	}
	@XmlElement
	public Address getShipTo() {
		return shipTo;
	}

	public void setShipTo(Address shipTo) {
		this.shipTo = shipTo;
	}
	@XmlElement
	public Shipping getShipping() {
		return shipping;
	}

	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}
	@XmlElement
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
}
