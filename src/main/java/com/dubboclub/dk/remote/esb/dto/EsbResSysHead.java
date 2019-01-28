package com.dubboclub.dk.remote.esb.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Create on 2017/7/19.
 * 对外接口系统报文头返回信息
 * @author luominggang
 */
@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class EsbResSysHead implements Serializable {
	private static final long serialVersionUID = -5106971922917567702L;

	/**
	 * 返回的错误码和错误信息
	 */
	@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
	public static class Ret implements Serializable {
		private static final long serialVersionUID = -8483640236885854344L;
		@JsonProperty("RET_CODE")
		private String retCode;
		@JsonProperty("RET_MSG")
		private String retMsg;

		public String getRetCode() {
			return retCode;
		}

		public void setRetCode(String retCode) {
			this.retCode = retCode;
		}

		public String getRetMsg() {
			return retMsg;
		}

		public void setRetMsg(String retMsg) {
			this.retMsg = retMsg;
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder("Ret{");
			sb.append("retCode='").append(retCode).append('\'');
			sb.append(", retMsg='").append(retMsg).append('\'');
			sb.append('}');
			return sb.toString();
		}
	}

	@JsonProperty("RET_STATUS")
	private String retStatus;
	@JsonProperty("RET")
	private List<Ret> ret;
	@JsonProperty("SERVICE_ID")
	private String serviceId;
	@JsonProperty("SCENE_ID")
	private String sceneId;
	@JsonProperty("SOURCE_TYPE")
	private String sourceType;
	@JsonProperty("PRVD_SYS_ID")
	private String prvdSysId;
	@JsonProperty("RUN_DATE")
	private String runDate;
	@JsonProperty("TRAN_DATE")
	private String tranDate;
	@JsonProperty("TRAN_TIMESTAMP")
	private String tranTimestamp;
	@JsonProperty("SEQ_NO")
	private String seqNo;
	@JsonProperty("REFERENCE")
	private String reference;
	@JsonProperty("SOURCE_BRANCH_NO")
	private String sourceBranchNo;
	@JsonProperty("DEST_BRANCH_NO")
	private String destBranchNo;
	@JsonProperty("FILE_PATH")
	private String filePath;
	@JsonProperty("MAC_VALUE")
	private String macValue;
	@JsonProperty("THREAD_NO")
	private String threadNo;
	@JsonProperty("COST_TIME")
	private String costTime;
	@JsonProperty("EXTEND_ARRAY")
	private List<Map<String, Object>> extendArray;
	@JsonProperty("EXTEND_FIELD")
	private String extendField;
	@JsonProperty("MAC_ORG_ID")
	private String macOrgId;
	@JsonProperty("PRVD_SYS_SVRID")
	private String prvdSysSvrid;

	public String getRetStatus() {
		return retStatus;
	}

	public void setRetStatus(String retStatus) {
		this.retStatus = retStatus;
	}

	public List<Ret> getRet() {
		return ret;
	}

	public void setRet(List<Ret> ret) {
		this.ret = ret;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getSceneId() {
		return sceneId;
	}

	public void setSceneId(String sceneId) {
		this.sceneId = sceneId;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getPrvdSysId() {
		return prvdSysId;
	}

	public void setPrvdSysId(String prvdSysId) {
		this.prvdSysId = prvdSysId;
	}

	public String getRunDate() {
		return runDate;
	}

	public void setRunDate(String runDate) {
		this.runDate = runDate;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public String getTranTimestamp() {
		return tranTimestamp;
	}

	public void setTranTimestamp(String tranTimestamp) {
		this.tranTimestamp = tranTimestamp;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getSourceBranchNo() {
		return sourceBranchNo;
	}

	public void setSourceBranchNo(String sourceBranchNo) {
		this.sourceBranchNo = sourceBranchNo;
	}

	public String getDestBranchNo() {
		return destBranchNo;
	}

	public void setDestBranchNo(String destBranchNo) {
		this.destBranchNo = destBranchNo;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getMacValue() {
		return macValue;
	}

	public void setMacValue(String macValue) {
		this.macValue = macValue;
	}

	public String getThreadNo() {
		return threadNo;
	}

	public void setThreadNo(String threadNo) {
		this.threadNo = threadNo;
	}

	public String getCostTime() {
		return costTime;
	}

	public void setCostTime(String costTime) {
		this.costTime = costTime;
	}

	public List<Map<String, Object>> getExtendArray() {
		return extendArray;
	}

	public void setExtendArray(List<Map<String, Object>> extendArray) {
		this.extendArray = extendArray;
	}

	public String getExtendField() {
		return extendField;
	}

	public void setExtendField(String extendField) {
		this.extendField = extendField;
	}

	public String getMacOrgId() {
		return macOrgId;
	}

	public void setMacOrgId(String macOrgId) {
		this.macOrgId = macOrgId;
	}

	public String getPrvdSysSvrid() {
		return prvdSysSvrid;
	}

	public void setPrvdSysSvrid(String prvdSysSvrid) {
		this.prvdSysSvrid = prvdSysSvrid;
	}
}
