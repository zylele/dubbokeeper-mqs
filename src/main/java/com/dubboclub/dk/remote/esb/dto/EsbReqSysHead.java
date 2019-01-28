package com.dubboclub.dk.remote.esb.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Create on 2017/7/19.
 * 对外接口系统报文头请求信息
 * @author luominggang
 */
@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class EsbReqSysHead implements Serializable {
	private static final long serialVersionUID = -5141271188126407807L;
	@JsonProperty("TXCODE")
	private String txcode;
	@JsonProperty("SERVICE_ID")
	private String serviceId;
	@JsonProperty("SCENE_ID")
	private String sceneId;
	@JsonProperty("TRAN_MODE")
	private String tranMode;
	@JsonProperty("SOURCE_TYPE")
	private String sourceType;
	@JsonProperty("BRANCH_ID")
	private String branchId;
	@JsonProperty("USER_ID")
	private String userId;
	@JsonProperty("TRAN_DATE")
	private String tranDate;
	@JsonProperty("TRAN_TIMESTAMP")
	private String tranTimestamp;
	@JsonProperty("USER_LANG")
	private String userLang;
	@JsonProperty("SEQ_NO")
	private String seqNo;
	@JsonProperty("SYSTEM_ID")
	private String systemId;
	@JsonProperty("COMPANY")
	private String company;
	@JsonProperty("SOURCE_BRANCH_NO")
	private String sourceBranchNo;
	@JsonProperty("DEST_BRANCH_NO")
	private String destBranchNo;
	@JsonProperty("FILE_PATH")
	private String filePath;
	@JsonProperty("MAC_VALUE")
	private String macValue;
	@JsonProperty("WS_ID")
	private String wsId;
	@JsonProperty("PROGRAM_ID")
	private String programId;
	@JsonProperty("AUTH_USER_ID")
	private String authUserId;
	@JsonProperty("AUTH_FLAG")
	private String authFlag;
	@JsonProperty("APPR_USER_ID")
	private String apprUserId;
	@JsonProperty("APPR_FLAG")
	private String apprFlag;
	@JsonProperty("VERSION")
	private String version;
	@JsonProperty("THREAD_NO")
	private String threadNo;
	@JsonProperty("EXTEND_ARRAY")
	private List<Map<String, Object>> extendArray;
	@JsonProperty("EXTEND_FIELD")
	private String extendField;
	@JsonProperty("MAC_ORG_ID")
	private String macOrgId;
	@JsonProperty("CNSM_SYS_SVRID")
	private String cnsmSysSvrid;
	@JsonProperty("SRC_SYS_SVRID")
	private String srcSysSvrid;
	@JsonProperty("MESSAGE_TYPE")
	private String messageType;// 消息类型
	@JsonProperty("MESSAGE_CODE")
	private String messageCode;// 消息代码
	@JsonProperty("SERVICE_CODE")
	private String serviceCode;

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

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getTxcode() {
		return txcode;
	}

	public void setTxcode(String txcode) {
		this.txcode = txcode;
	}

	public String getTranMode() {
		return tranMode;
	}

	public void setTranMode(String tranMode) {
		this.tranMode = tranMode;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getUserLang() {
		return userLang;
	}

	public void setUserLang(String userLang) {
		this.userLang = userLang;
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

	public String getWsId() {
		return wsId;
	}

	public void setWsId(String wsId) {
		this.wsId = wsId;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getAuthUserId() {
		return authUserId;
	}

	public void setAuthUserId(String authUserId) {
		this.authUserId = authUserId;
	}

	public String getAuthFlag() {
		return authFlag;
	}

	public void setAuthFlag(String authFlag) {
		this.authFlag = authFlag;
	}

	public String getApprUserId() {
		return apprUserId;
	}

	public void setApprUserId(String apprUserId) {
		this.apprUserId = apprUserId;
	}

	public String getApprFlag() {
		return apprFlag;
	}

	public void setApprFlag(String apprFlag) {
		this.apprFlag = apprFlag;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getThreadNo() {
		return threadNo;
	}

	public void setThreadNo(String threadNo) {
		this.threadNo = threadNo;
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

	public String getCnsmSysSvrid() {
		return cnsmSysSvrid;
	}

	public void setCnsmSysSvrid(String cnsmSysSvrid) {
		this.cnsmSysSvrid = cnsmSysSvrid;
	}

	public String getSrcSysSvrid() {
		return srcSysSvrid;
	}

	public void setSrcSysSvrid(String srcSysSvrid) {
		this.srcSysSvrid = srcSysSvrid;
	}

}
