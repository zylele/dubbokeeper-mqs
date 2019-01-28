package com.dubboclub.dk.remote.esb.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.dubboclub.dk.remote.esb.dto.EsbResAppHead;
import com.dubboclub.dk.remote.esb.dto.EsbResSysHead;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Create on 2017/7/21
 *对外接口系统报文返回信息
 * @author chenweipu
 */
@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class EsbBaseRes implements Serializable {
  private static final long serialVersionUID = -6686872904687558362L;
  @JsonProperty("SYS_HEAD")
  private EsbResSysHead sysHead;
  @JsonProperty("APP_HEAD")
  private EsbResAppHead appHead;
  
  public EsbBaseRes() {
	  sysHead = new EsbResSysHead();
	  appHead = new EsbResAppHead();
  }


  private EsbResSysHead.Ret getRet() {
    if (sysHead != null && sysHead.getRet() != null && sysHead.getRet().size() >= 1) {
      return sysHead.getRet().get(0);
    }
    return null;
  }

  public String getRetCode() {
    EsbResSysHead.Ret ret = getRet();
    if (ret != null) {
      return ret.getRetCode();
    }
    return null;
  }

  public String getRetMsg() {
    EsbResSysHead.Ret ret = getRet();
    if (ret != null) {
      return ret.getRetMsg();
    }
    return null;
  }

  public EsbResSysHead getSysHead() {
    return sysHead;
  }

  public void setSysHead(EsbResSysHead sysHead) {
    this.sysHead = sysHead;
  }

  public EsbResAppHead getAppHead() {
    return appHead;
  }

  public void setAppHead(EsbResAppHead appHead) {
    this.appHead = appHead;
  }

  public String getPgupOrPgdn() {
    return appHead.getPgupOrPgdn();
  }

  public void setPgupOrPgdn(String pgupOrPgdn) {
    appHead.setPgupOrPgdn(pgupOrPgdn);
  }

  public String getTotalNum() {
    return appHead.getTotalNum();
  }

  public void setTotalNum(String totalNum) {
    appHead.setTotalNum(totalNum);
  }

  public String getCurrentNum() {
    return appHead.getCurrentNum();
  }

  public void setCurrentNum(String currentNum) {
    appHead.setCurrentNum(currentNum);
  }

  public String getTotalRows() {
    return appHead.getTotalRows();
  }

  public void setTotalRows(String totalRows) {
    appHead.setTotalRows(totalRows);
  }

  public String getTotalFlag() {
    return appHead.getTotalFlag();
  }

  public void setTotalFlag(String totalFlag) {
    appHead.setTotalFlag(totalFlag);
  }
  public String getRetStatus() {
    return sysHead.getRetStatus();
  }

  public void setRetStatus(String retSatus) {
    sysHead.setRetStatus(retSatus);
  }

  public void setRet(List<EsbResSysHead.Ret> ret) {
    sysHead.setRet(ret);
  }

  public String getServiceId() {
    return sysHead.getServiceId();
  }

  public void setServiceId(String serviceId) {
    sysHead.setServiceId(serviceId);
  }

  public String getSceneId() {
    return sysHead.getSceneId();
  }

  public void setSceneId(String sceneId) {
    sysHead.setSceneId(sceneId);
  }

  public String getSourceType() {
    return sysHead.getSourceType();
  }

  public void setSourceType(String sourceType) {
    sysHead.setSourceType(sourceType);
  }

  public String getPrvdSysId() {
    return sysHead.getPrvdSysId();
  }

  public void setPrvdSysId(String prvdSysId) {
    sysHead.setPrvdSysId(prvdSysId);
  }

  public String getRunDate() {
    return sysHead.getRunDate();
  }

  public void setRunDate(String runDate) {
    sysHead.setRunDate(runDate);
  }

  public String getTranDate() {
    return sysHead.getTranDate();
  }

  public void setTranDate(String tranDate) {
    sysHead.setTranDate(tranDate);
  }

  public String getSeqNo() {
    return sysHead.getSeqNo();
  }

  public void setSeqNo(String seqNo) {
    sysHead.setSeqNo(seqNo);
  }

  public String getReference() {
    return sysHead.getReference();
  }

  public void setReference(String reference) {
    sysHead.setReference(reference);
  }

  public String getSourceBranchNo() {
    return sysHead.getSourceBranchNo();
  }

  public void setSourceBranchNo(String sourceBranchNo) {
    sysHead.setSourceBranchNo(sourceBranchNo);
  }

  public String getDestBranchNo() {
    return sysHead.getDestBranchNo();
  }

  public void setDestBranchNo(String destBranchNo) {
    sysHead.setDestBranchNo(destBranchNo);
  }

  public String getFilePath() {
    return sysHead.getFilePath();
  }

  public void setFilePath(String filePath) {
    sysHead.setFilePath(filePath);
  }

  public String getMacValue() {
    return sysHead.getMacValue();
  }

  public void setMacValue(String macValue) {
    sysHead.setMacValue(macValue);
  }

  public String getThreadNo() {
    return sysHead.getThreadNo();
  }

  public void setThreadNo(String threadNo) {
    sysHead.setThreadNo(threadNo);
  }

  public String getCostTime() {
    return sysHead.getCostTime();
  }

  public void setCostTime(String costTime) {
    sysHead.setCostTime(costTime);
  }

  public List<Map<String, Object>> getExtendArray() {
    return sysHead.getExtendArray();
  }

  public void setExtendArray(List<Map<String, Object>> extentArray) {
    sysHead.setExtendArray(extentArray);
  }

  public String getExtendField() {
    return sysHead.getExtendField();
  }

  public void setExtendField(String extendField) {
    sysHead.setExtendField(extendField);
  }

  public String getMacOrgId() {
    return sysHead.getMacOrgId();
  }

  public void setMacOrgId(String macOrgId) {
    sysHead.setMacOrgId(macOrgId);
  }

  public String getPrvdSysSvrid() {
    return sysHead.getPrvdSysSvrid();
  }

  public void setPrvdSysSvrid(String prvdSysSvrid) {
    sysHead.setPrvdSysSvrid(prvdSysSvrid);
  }
}
