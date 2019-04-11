package com.dubboclub.dk.storage.model;

/**  
* @ClassName: DayTradingPo  
* @Description:statistic_day表实体   
* @author zhangpengfei  
* @date 2019年3月11日   
*/
public class DayTradingPo {
	
	private String startTime;
	private String nowTime;
	private int totalTimeNum;
	private int success;
	private int fail;
	private long timestamp;
	private String txCode;
	private String chnlCode;
	
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
	public String getChnlCode() {
		return chnlCode;
	}
	public void setChnlCode(String chnlCode) {
		this.chnlCode = chnlCode;
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
	public String getNowTime() {
		return nowTime;
	}
	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}

}
