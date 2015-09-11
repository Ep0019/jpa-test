package com.lhy.netty.entity;

import java.io.Serializable;

/**
 * @author: 李慧勇
 * @description:火车实体
 * @mail:jdlglhy@163.com
 * @2015年7月7日
 * @version 1.0
 */

public class Train implements Serializable{
	private static final long serialVersionUID = 1510326612440404416L;
	private String number;//火车车次
    private int ticketCounts;//余票数量
    public Train(String number,int ticketCounts){
        this.number=number;
        this.ticketCounts=ticketCounts;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public int getTicketCounts() {
        return ticketCounts;
    }
    public void setTicketCounts(int ticketCounts) {
        this.ticketCounts = ticketCounts;
    }

}
