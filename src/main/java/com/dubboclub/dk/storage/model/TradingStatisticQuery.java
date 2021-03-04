package com.dubboclub.dk.storage.model;

/**  
* @ClassName: TradingStatisticQuery  
* @Description:   
* @author zhangpengfei  
* @date 2019年3月11日   
*/
public class TradingStatisticQuery {
	private String tradingStartDate;
	private String tradingEndDate;
	private String type;
	private String chnlCode;
	public String getChnlCode() {
		return chnlCode;
	}
	public void setChnlCode(String chnlCode) {
		this.chnlCode = chnlCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTradingStartDate() {
		return tradingStartDate;
	}
	public void setTradingStartDate(String tradingStartDate) {
		this.tradingStartDate = tradingStartDate;
	}
	public String getTradingEndDate() {
		return tradingEndDate;
	}
	public void setTradingEndDate(String tradingEndDate) {
		this.tradingEndDate = tradingEndDate;
	}

	
}
