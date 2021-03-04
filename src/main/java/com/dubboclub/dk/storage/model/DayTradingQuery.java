package com.dubboclub.dk.storage.model;

/**  
* @ClassName: DayTradingQuery  
* @Description:statistic_day表查询实体   
* @author zhangpengfei  
* @date 2019年3月11日   
*/
public class DayTradingQuery {
	private String dayTradingStartDate;
	private String dayTradingEndDate;
	private String findType;
	private String txCode;
	private String chnlCode;
	public String getDayTradingStartDate() {
		return dayTradingStartDate;
	}
	public void setDayTradingStartDate(String dayTradingStartDate) {
		this.dayTradingStartDate = dayTradingStartDate;
	}
	public String getDayTradingEndDate() {
		return dayTradingEndDate;
	}
	public void setDayTradingEndDate(String dayTradingEndDate) {
		this.dayTradingEndDate = dayTradingEndDate;
	}
	public String getFindType() {
		return findType;
	}
	public void setFindType(String findType) {
		this.findType = findType;
	}
	public String getTxCode() {
		return txCode;
	}
	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}
	public String getChnlCode() {
		return chnlCode;
	}
	public void setChnlCode(String chnlCode) {
		this.chnlCode = chnlCode;
	}

}
