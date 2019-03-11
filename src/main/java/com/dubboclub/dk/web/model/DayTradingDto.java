package com.dubboclub.dk.web.model;

/**  
* @ClassName: DayTradingDto  
* @Description:日交易量峰值统计   
* @author zhangpengfei  
* @date 2019年3月11日   
*/
public class DayTradingDto {
	
	private String startTime;
	private int totalTimeNum;
	private long timestamp;
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
