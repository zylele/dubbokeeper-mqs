package com.dubboclub.dk.remote.esb.base;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 公共输出头
 * Create on 2017/5/9
 *
 * @author chenweipu
 */

public class EsbBaseOutBO implements Serializable{

  private static final long serialVersionUID = -5586884476131906093L;

  /**
   * 返回的错误码和错误信息
   */
  @JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
  public static class Ret implements Serializable {
    private static final long serialVersionUID = -1355335131858064006L;
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

  private String pgUpOrPgDn;
  private String pageSize;
  private String serviceId;
  private String sceneId;
  private String origChnlType;
  private String prvdSysId;
  private String origChnlDate;
  private String txDate;
  private String txTime;
  private String origChnlTrcNo;
  private String signType;
  private List<Map<String,Object>> extendArray;
  private String sign;
  private String macOrgId;
  private String prvdSysSvrid;

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

  public String getTotalRows() {
    return totalRows;
  }

  public void setTotalRows(String totalRows) {
    this.totalRows = totalRows;
  }

  public String getTotalFlag() {
    return totalFlag;
  }

  public void setTotalFlag(String totalFlag) {
    this.totalFlag = totalFlag;
  }

  public String getRetStatus() {
    return retStatus;
  }

  public void setRetStatus(String retStatus) {
    this.retStatus = retStatus;
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

  public String getOrigChnlType() {
    return origChnlType;
  }

  public void setOrigChnlType(String origChnlType) {
    this.origChnlType = origChnlType;
  }

  public String getPrvdSysId() {
    return prvdSysId;
  }

  public void setPrvdSysId(String prvdSysId) {
    this.prvdSysId = prvdSysId;
  }

  public String getOrigChnlDate() {
    return origChnlDate;
  }

  public void setOrigChnlDate(String origChnlDate) {
    this.origChnlDate = origChnlDate;
  }

  public String getTxDate() {
    return txDate;
  }

  public void setTxDate(String txDate) {
    this.txDate = txDate;
  }

  public String getTxTime() {
    return txTime;
  }

  public void setTxTime(String txTime) {
    this.txTime = txTime;
  }

  public String getOrigChnlTrcNo() {
    return origChnlTrcNo;
  }

  public void setOrigChnlTrcNo(String origChnlTrcNo) {
    this.origChnlTrcNo = origChnlTrcNo;
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

  public String getSignType() {
    return signType;
  }

  public void setSignType(String signType) {
    this.signType = signType;
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

  public String getPrvdSysSvrid() {
    return prvdSysSvrid;
  }

  public void setPrvdSysSvrid(String prvdSysSvrid) {
    this.prvdSysSvrid = prvdSysSvrid;
  }
  
  @JsonProperty("RET_CODE")
	private String retCode;
	@JsonProperty("RET_MSG")
	private String retMsg;
	@JsonProperty("RET_STATUS")
	private String retStatus;// 交易状态
	@JsonProperty("RET")
	private List<Ret> ret;
	@JsonProperty("SERVICE_ID")// 交易结果
	private String serviceID;// 服务代码
	@JsonProperty("SCENE_ID")
	private String sceneID;
	@JsonProperty("SOURCE_TYPE")// 服务场景
	private String sourceType;
	@JsonProperty("PRVD_SYS_ID")// 渠道类型
	private String prvdSysID;
	@JsonProperty("RUN_DATE")// 提供方系统ID
	private String runDate;
	@JsonProperty("TRAN_DATE")// 系统运行日期	
	private String tranDate;
	@JsonProperty("TRAN_TIMESTAMP")// 交易日期
	private String tranTimestamp;	// 交易时间
	@JsonProperty("SEQ_NO")
	private String seqNo;
	@JsonProperty("REFERENCE")	// 渠道流水号
	private String reference;		// 交易参考号
	@JsonProperty("SOURCE_BRANCH_NO")
	private String sourceBranchNo;
	@JsonProperty("DEST_BRANCH_NO")		// 源节点编号
	private String destBranchNo;		// 目标节点编号
	@JsonProperty("FILE_PATH")
	private String filePath;		// 文件绝对路径
	@JsonProperty("MAC_VALUE")
	private String macValue;		// 传输密押
	@JsonProperty("THREAD_NO")
	private String threadNo;		// 线程编号
	@JsonProperty("COST_TIME")
	private String costTime;		// 服务耗时
	@JsonProperty("EXTEND_FIELD")
	private String extendField;		// 预留字段
	@JsonProperty("MAC_ORG_ID")
	private String macOrgID;		// MAC机构号
	@JsonProperty("PRVD_SYS_SVRID")
	private String prvdSysSvrID;		// 提供方系统服务器Id
	@JsonProperty("PGUP_OR_PGDN")
	private String pgupOrPgdn;		// 上翻/下翻标志
	@JsonProperty("TOTAL_NUM")
	private String totalNum;		// 本页记录总数
	@JsonProperty("CURRENT_NUM")
	private String currentNum;		// 当前记录号
	@JsonProperty("TOTAL_ROWS")
	private String totalRows;		// 总笔数
	@JsonProperty("TOTAL_FLAG")
	private String totalFlag;		// 汇总标志

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

	public List<Ret> getRet() {
  	return ret;
  }

	public void setRet(List<Ret> ret) {
  	this.ret = ret;
  }

	public String getServiceID() {
  	return serviceID;
  }

	public void setServiceID(String serviceID) {
  	this.serviceID = serviceID;
  }

	public String getSceneID() {
  	return sceneID;
  }

	public void setSceneID(String sceneID) {
  	this.sceneID = sceneID;
  }

	public String getSourceType() {
  	return sourceType;
  }

	public void setSourceType(String sourceType) {
  	this.sourceType = sourceType;
  }

	public String getPrvdSysID() {
  	return prvdSysID;
  }

	public void setPrvdSysID(String prvdSysID) {
  	this.prvdSysID = prvdSysID;
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

	public String getMacOrgID() {
  	return macOrgID;
  }

	public void setMacOrgID(String macOrgID) {
  	this.macOrgID = macOrgID;
  }

	public String getPrvdSysSvrID() {
  	return prvdSysSvrID;
  }

	public void setPrvdSysSvrID(String prvdSysSvrID) {
  	this.prvdSysSvrID = prvdSysSvrID;
  }

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
	
	
}
