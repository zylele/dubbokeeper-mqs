package com.dubboclub.dk.storage.model;

public class StatisticObject {
	private String txCode;
	private int totalNum;
	private double timeAvg;
	private String tiemMax;
	private String timeMin;
	private int success;
	private int fail;
	private String nowTime;
	private long totalTimePerTime;

	public String getNowTime() {
		return nowTime;
	}
	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}
	public String getTxCode() {
		return txCode;
	}
	public void setTxCode(String txCode) {
		this.txCode = txCode;
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
	public long getTotalTimePerTime() {
		return totalTimePerTime;
	}
	public void setTotalTimePerTime(long totalTimePerTime) {
		this.totalTimePerTime = totalTimePerTime;
	}

}
