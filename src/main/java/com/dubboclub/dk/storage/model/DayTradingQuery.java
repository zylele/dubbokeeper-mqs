package com.dubboclub.dk.storage.model;

/**  
* @ClassName: DayTradingQuery  
* @Description:statistic_day表查询实体   
* @author zhangpengfei  
* @date 2019年3月11日   
*/
public class DayTradingQuery {
	private long dayTradingStartDate;
	private long dayTradingEndDate;
	public long getDayTradingStartDate() {
		return dayTradingStartDate;
	}
	public void setDayTradingStartDate(long dayTradingStartDate) {
		this.dayTradingStartDate = dayTradingStartDate;
	}
	public long getDayTradingEndDate() {
		return dayTradingEndDate;
	}
	public void setDayTradingEndDate(long dayTradingEndDate) {
		this.dayTradingEndDate = dayTradingEndDate;
	}
	
	

}
