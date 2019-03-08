package com.dubboclub.dk.storage.model;

public class TradingStatisticPo {
	private int id;
	private String txCode;
	private String txName;
	private String txType;
	private String nowTime;
	private int totalNum;
	private double timeAvg;
	private String tiemMax;
	private String timeMin;
	private int success;
	private int fail;
	private String serviceName;
	private String sourceType;
	private String failRate;
	
	public String getFailRate() {
		return failRate;
	}
	public void setFailRate(String failRate) {
		this.failRate = failRate;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTxCode() {
		return txCode;
	}
	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}
	public String getTxName() {
		return txName;
	}
	public void setTxName(String txName) {
		this.txName = txName;
	}
	public String getTxType() {
		return txType;
	}
	public void setTxType(String txType) {
		this.txType = txType;
	}
	public String getNowTime() {
		return nowTime;
	}
	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public double getTimeAvg() {
		return timeAvg;
	}
	public void setTimeAvg(double timeAvg) {
		this.timeAvg = timeAvg;
	}
	public String getTiemMax() {
		return tiemMax;
	}
	public void setTiemMax(String tiemMax) {
		this.tiemMax = tiemMax;
	}
	public String getTimeMin() {
		return timeMin;
	}
	public void setTimeMin(String timeMin) {
		this.timeMin = timeMin;
	}
	public int getSuccess() {
		return success;
	}
	public void setSuccess(int success) {
		this.success = success;
	}
	public int getFail() {
		return fail;
	}
	public void setFail(int fail) {
		this.fail = fail;
	}
}

