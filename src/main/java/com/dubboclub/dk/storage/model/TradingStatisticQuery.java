package com.dubboclub.dk.storage.model;

public class TradingStatisticQuery {
	private String tradingStartDate;
	private String tradingEndDate;
	private String type;
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
