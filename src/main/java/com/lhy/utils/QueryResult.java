package com.lhy.utils;
import java.util.List;

/**   
 * @description   
 * @author 李慧勇
 * @email jdlglhy@163.com  
 * @version 1.0
 * 2015年6月23日 上午9:12:36   
 */
public class QueryResult<T> {

	/**
	* 总记录列表
	*/
	private List<T> resultList;
	
	/**
	* 总记录数
	*/
	private Long totalRecord;
	
	
	public List<T> getResultList() {
		return resultList;
	}
	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
	public Long getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(Long totalRecord) {
		this.totalRecord = totalRecord;
	}
}
