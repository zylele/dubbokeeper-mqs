package com.dubboclub.dk.storage.model;

/**  
* @ClassName: StatisticObject  
* @Description:   
* @author zhangpengfei  
* @date 2019年3月11日   
*/
public class StatisticObject {
	private String txCode;
	private int totalNum;
	private double timeAvg;
	private String tiemMax;
	private String timeMin;
	private int success;
	private int fail;
	private String nowTime;
	private String startTime;
	private long totalTimePerTime;
	private int totalDayTimePer;
	private long timestamp;
	private String serviceName;
	private String sourceType;

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
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public int getTotalDayTimePer() {
		return totalDayTimePer;
	}
	public void setTotalDayTimePer(int totalDayTimePer) {
		this.totalDayTimePer = totalDayTimePer;
	}
	public String getNowTime() {
		return nowTime;
	}
	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
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
