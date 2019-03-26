package com.dubboclub.dk.storage.model;

public class TradingStatisticQueryTime {
	private String tradingStartDate;
	private String tradingEndDate;
	private String chnlCode;

	
	public String getChnlCode() {
		return chnlCode;
	}
	public void setChnlCode(String chnlCode) {
		this.chnlCode = chnlCode;
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
