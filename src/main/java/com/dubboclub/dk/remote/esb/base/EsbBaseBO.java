package com.dubboclub.dk.remote.esb.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 公共请求头
 * Create on 2017/5/9
 *
 * @author chenweipu
 */

public class EsbBaseBO implements Serializable{
  private static final long serialVersionUID = -1647595979670254275L;

  private String pgUpOrPgDn;
  private String pageSize;
  @JsonProperty("CURRENT_NUM")
  private String currentNum;
  @JsonProperty("TOTAL_FLAG")
  private String totalFlag;

  
  @JsonProperty("MAC_VALUE")
  private String macValue;
  @JsonProperty("EXTEND_FIELD")
  private String extendField;
  @JsonProperty("PGUP_OR_PGDN")
  private String pgupOrDgdn;
  @JsonProperty("TOTAL_NUM")
  private String totalNum;
  
  private String txCode;
  private String serviceId;
  private String sceneId;
  @JsonProperty("TRAN_MODE")
  private String tranMode;
  private String chnlType;
  private String txBrNo;
  private String tellerNo;
  private String chnlDate;
  private String chnlTime;
  @JsonProperty("USER_LANG")
  private String userLang;
  private String chnlTrcNo;
  @JsonProperty("SYSTEM_ID")
  private String systemId;
  private String comBrNo;
  @JsonProperty("SOURCE_BRANCH_NO")
  private String sourceBranchNo;
  @JsonProperty("DEST_BRANCH_NO")
  private String destBranchNo;
  @JsonProperty("FILE_PATH")
  private String filePath;
  
  private String signType;
  @JsonProperty("WS_ID")
  private String wsId;
  @JsonProperty("PROGRAM_ID")
  private String programId;
  @JsonProperty("AUTH_USER_ID")
  private String authUserId;
  @JsonProperty("AUTH_FLAG")
  private String auth_flag;
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
  @JsonProperty("SIGN")
  private String sign;
  @JsonProperty("MAC_ORG_ID")
  private String macOrgId;
  @JsonProperty("CNSM_SYS_SVRID")
  private String cnsmSysSvrid;
  @JsonProperty("SRC_SYS_SVRID")
  private String srcSysSvrid;
  @JsonProperty("MESSAGE_TYPE")
  private String messageType;
  @JsonProperty("MESSAGE_CODE")
  private String messageCode;
  @JsonProperty("SERVICE_CODE")
  private String serviceCode;
  @JsonProperty("SOURCE_TYPE")
	private String sourceType;
  @JsonProperty("BRANCH_ID")
	private String branchId;
  @JsonProperty("USER_ID")
	private String userId;
  @JsonProperty("TRAN_DATE")
	private String tranDate;
  @JsonProperty("TRAN_TIMESTAMP")
	private String tranTimeStamp;
  @JsonProperty("SEQ_NO")
	private String seqNo;
  @JsonProperty("COMPANY")
	private String company;

  public String getMacValue() {
  	return macValue;
  }

	public void setMacValue(String macValue) {
  	this.macValue = macValue;
  }

	public String getExtendField() {
  	return extendField;
  }

	public void setExtendField(String extendField) {
  	this.extendField = extendField;
  }

	public String getPgupOrDgdn() {
  	return pgupOrDgdn;
  }

	public void setPgupOrDgdn(String pgupOrDgdn) {
  	this.pgupOrDgdn = pgupOrDgdn;
  }

	public String getTotalNum() {
  	return totalNum;
  }

	public void setTotalNum(String totalNum) {
  	this.totalNum = totalNum;
  }

	public String getServiceCode() {
  	return serviceCode;
  }

	public void setServiceCode(String serviceCode) {
  	this.serviceCode = serviceCode;
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

	public String getTranTimeStamp() {
  	return tranTimeStamp;
  }

	public void setTranTimeStamp(String tranTimeStamp) {
  	this.tranTimeStamp = tranTimeStamp;
  }

	public String getSeqNo() {
  	return seqNo;
  }

	public void setSeqNo(String seqNo) {
  	this.seqNo = seqNo;
  }

	public String getCompany() {
  	return company;
  }

	public void setCompany(String company) {
  	this.company = company;
  }

	public String getPgUpOrPgDn() {
    return pgUpOrPgDn;
  }

  public void setPgUpOrPgDn(String pgUpOrPgDn) {
    this.pgUpOrPgDn = pgUpOrPgDn;
  }

  public String getPageSize() {
    return pageSize;
  }

  public void setPageSize(String pageSize) {
    this.pageSize = pageSize;
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

  public String getTxCode() {
    return txCode;
  }

  public void setTxCode(String txCode) {
    this.txCode = txCode;
  }

  public String getTranMode() {
    return tranMode;
  }

  public void setTranMode(String tranMode) {
    this.tranMode = tranMode;
  }

  public String getChnlType() {
    return chnlType;
  }

  public void setChnlType(String chnlType) {
    this.chnlType = chnlType;
  }

  public String getTxBrNo() {
    return txBrNo;
  }

  public void setTxBrNo(String txBrNo) {
    this.txBrNo = txBrNo;
  }

  public String getTellerNo() {
    return tellerNo;
  }

  public void setTellerNo(String tellerNo) {
    this.tellerNo = tellerNo;
  }

  public String getChnlDate() {
    return chnlDate;
  }

  public void setChnlDate(String chnlDate) {
    this.chnlDate = chnlDate;
  }

  public String getChnlTime() {
    return chnlTime;
  }

  public void setChnlTime(String chnlTime) {
    this.chnlTime = chnlTime;
  }

  public String getChnlTrcNo() {
    return chnlTrcNo;
  }

  public void setChnlTrcNo(String chnlTrcNo) {
    this.chnlTrcNo = chnlTrcNo;
  }

  public String getSystemId() {
    return systemId;
  }

  public void setSystemId(String systemId) {
    this.systemId = systemId;
  }

  public String getComBrNo() {
    return comBrNo;
  }

  public void setComBrNo(String comBrNo) {
    this.comBrNo = comBrNo;
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

  public String getSignType() {
    return signType;
  }

  public void setSignType(String signType) {
    this.signType = signType;
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

  public String getAuth_flag() {
    return auth_flag;
  }

  public void setAuth_flag(String auth_flag) {
    this.auth_flag = auth_flag;
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

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
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
  
  
}
