package com.dubboclub.dk.remote.esb.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Create on 2017/7/19.
 * 对外接口app报文头请求信息
 * @author luominggang
 */
@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class EsbReqAppHead implements Serializable {
	private static final long serialVersionUID = -4517778644417734316L;
	@JsonProperty("PGUP_OR_PGDN")
	private String pgupOrPgdn;
	@JsonProperty("TOTAL_NUM")
	private String totalNum;
	@JsonProperty("CURRENT_NUM")
	private String currentNum;
	@JsonProperty("TOTAL_FLAG")
	private String totalFlag;
	@JsonProperty("PAGE_START")
	private String pageStart;
	@JsonProperty("PAGE_END")
	private String pageEnd;

	public String getPgupOrPgdn() {
		return pgupOrPgdn;
	}

	public void setPgupOrPgdn(String pgupOrPgdn) {
		this.pgupOrPgdn = pgupOrPgdn;
	}

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public String getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(String currentNum) {
		this.currentNum = currentNum;
	}

	public String getTotalFlag() {
		return totalFlag;
	}

	public void setTotalFlag(String totalFlag) {
		this.totalFlag = totalFlag;
	}
	
	public String getPageStart() {
		return pageStart;
	}

	public void setPageStart(String pageStart) {
		this.pageStart = pageStart;
	}

	public String getPageEnd() {
		return pageEnd;
	}

	public void setPageEnd(String pageEnd) {
		this.pageEnd = pageEnd;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("EsbReqAppHead{");
		sb.append("pgupOrPgdn='").append(pgupOrPgdn).append('\'');
		sb.append(", totalNum='").append(totalNum).append('\'');
		sb.append(", currentNum='").append(currentNum).append('\'');
		sb.append(", totalFlag='").append(totalFlag).append('\'');
		sb.append('}');
		return sb.toString();
	}
}