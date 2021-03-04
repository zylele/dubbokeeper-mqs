package com.dubboclub.dk.web.model;

/**  
* @ClassName: DayTradingDto  
* @Description:日交易量峰值统计   
* @author zhangpengfei  
* @date 2019年3月11日   
*/
public class DayTradingDto {
	
	private String startTime;
	private String nowTime;
	private int totalTimeNum;
	private int success;
	private int fail;
	private long timestamp;
	public String getNowTime() {
		return nowTime;
	}
	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
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
	private String txCode;
	public String getTxCode() {
		return txCode;
	}
	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public int getTotalTimeNum() {
		return totalTimeNum;
	}
	public void setTotalTimeNum(int totalTimeNum) {
		this.totalTimeNum = totalTimeNum;
	}

}
