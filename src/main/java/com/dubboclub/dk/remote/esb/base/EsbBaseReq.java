package com.dubboclub.dk.remote.esb.base;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.dubboclub.dk.remote.esb.dto.EsbReqAppHead;
import com.dubboclub.dk.remote.esb.dto.EsbReqSysHead;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Create on 2017/7/21
 * 对外接口系统报文请求信息
 * @author chenweipu
 */
@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class EsbBaseReq implements Serializable {
	private static final long serialVersionUID = 3840227014884148287L;

	private EsbReqSysHead sysHead;
	private EsbReqAppHead appHead;

	public EsbBaseReq() {
		sysHead = new EsbReqSysHead();
		appHead = new EsbReqAppHead();
	}

	public EsbBaseReq(ReqHead head) {
		sysHead = new EsbReqSysHead();
		appHead = new EsbReqAppHead();
		sysHead.setTxcode(head.getTxCode());
		sysHead.setTranMode("ONLINE");
		sysHead.setSourceType(head.getChnlType());
		sysHead.setBranchId("90001");
		sysHead.setTranDate(getCurDateFlag());
		sysHead.setTranTimestamp(getCurTimestamp());
		sysHead.setUserLang("CHINESE");
		sysHead.setSeqNo(head.getChnlTrcNo());
		sysHead.setSystemId("IBS");
		sysHead.setCompany("“”");
		sysHead.setFilePath(head.getFilePath());
		appHead.setCurrentNum(head.getCurrentNum());
		appHead.setPgupOrPgdn(head.getPgUpOrPgDn());
		appHead.setTotalFlag(head.getTotalFlag());
		appHead.setTotalNum(head.getPageSize());
	}

	public EsbReqSysHead getSysHead() {
		return sysHead;
	}

	public void setSysHead(EsbReqSysHead sysHead) {
		this.sysHead = sysHead;
	}

	public EsbReqAppHead getAppHead() {
		return appHead;
	}

	public void setAppHead(EsbReqAppHead appHead) {
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

	public String getTotalFlag() {
		return appHead.getTotalFlag();
	}

	public void setTotalFlag(String totalFlag) {
		appHead.setTotalFlag(totalFlag);
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

	public String getTxcode() {
		return sysHead.getTxcode();
	}

	public void setTxcode(String txcode) {
		sysHead.setTxcode(txcode);
	}

	public String getTranMode() {
		return sysHead.getTranMode();
	}

	public void setTranMode(String tranMode) {
		sysHead.setTranMode(tranMode);
	}

	public String getSourceType() {
		return sysHead.getSourceType();
	}

	public void setSourceType(String sourceType) {
		sysHead.setSourceType(sourceType);
	}

	public String getBranchId() {
		return sysHead.getBranchId();
	}

	public void setBranchId(String branchId) {
		sysHead.setBranchId(branchId);
	}

	public String getUserId() {
		return sysHead.getUserId();
	}

	public void setUserId(String userId) {
		sysHead.setUserId(userId);
	}

	public String getTransDate() {
		return sysHead.getTranDate();
	}

	public void setTranDate(String transDate) {
		sysHead.setTranDate(transDate);
	}

	public String getTranTimestamp() {
		return sysHead.getTranTimestamp();
	}

	public void setTranTimestamp(String tranTimestamp) {
		sysHead.setTranTimestamp(tranTimestamp);
	}

	public String getSeqNo() {
		return sysHead.getSeqNo();
	}

	public void setSeqNo(String seqNo) {
		sysHead.setSeqNo(seqNo);
	}

	public String getSystemId() {
		return sysHead.getSystemId();
	}

	public void setSystemId(String systemId) {
		sysHead.setSystemId(systemId);
	}

	public String getCompany() {
		return sysHead.getCompany();
	}

	public void setCompany(String company) {
		sysHead.setCompany(company);
	}

	public String getUserLang() {
		return sysHead.getUserLang();
	}

	public void setUserLang(String userLang) {
		sysHead.setUserLang(userLang);
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

	public String getWsId() {
		return sysHead.getWsId();
	}

	public void setWsId(String wsId) {
		sysHead.setWsId(wsId);
	}

	public String getProgramId() {
		return sysHead.getProgramId();
	}

	public void setProgramId(String programId) {
		sysHead.setProgramId(programId);
	}

	public String getAuthUserId() {
		return sysHead.getAuthUserId();
	}

	public void setAuthUserId(String authUserId) {
		sysHead.setAuthUserId(authUserId);
	}

	public String getAuthFlag() {
		return sysHead.getAuthFlag();
	}

	public void setAuthFlag(String authFlag) {
		sysHead.setAuthFlag(authFlag);
	}

	public String getApprUserId() {
		return sysHead.getApprUserId();
	}

	public void setApprUserId(String apprUserId) {
		sysHead.setApprUserId(apprUserId);
	}

	public String getApprFlag() {
		return sysHead.getApprFlag();
	}

	public void setApprFlag(String apprFlag) {
		sysHead.setApprFlag(apprFlag);
	}

	public String getVersion() {
		return sysHead.getVersion();
	}

	public void setVersion(String version) {
		sysHead.setVersion(version);
	}

	public String getThreadNo() {
		return sysHead.getThreadNo();
	}

	public void setThreadNo(String threadNo) {
		sysHead.setThreadNo(threadNo);
	}

	public List<Map<String, Object>> getExtendArray() {
		return sysHead.getExtendArray();
	}

	public void setExtendArray(List<Map<String, Object>> extendArray) {
		sysHead.setExtendArray(extendArray);
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

	public String getCnsmSysSvrid() {
		return sysHead.getCnsmSysSvrid();
	}

	public void setCnsmSysSvrid(String cnsmSysSvrid) {
		sysHead.setCnsmSysSvrid(cnsmSysSvrid);
	}

	public String getServiceCode() {
		return sysHead.getServiceCode();
	}

	public void setServiceCode(String serviceCode) {
		sysHead.setServiceCode(serviceCode);
	}

	public String getMessageType() {
		return sysHead.getMessageType();
	}

	public void setMessageType(String messageType) {
		sysHead.setMessageType(messageType);
	}

	public String getMessageCode() {
		return sysHead.getMessageCode();
	}

	public void setMessageCode(String messageCode) {
		sysHead.setMessageCode(messageCode);
	}

	public String getSrcSysSvrid() {
		return sysHead.getSrcSysSvrid();
	}

	public void setSrcSysSvrid(String srcSysSvrid) {
		sysHead.setSrcSysSvrid(srcSysSvrid);
	}
	/**
	 * 获取带有时分秒的时间标志，只有数字没有分割符号的。yyyyMMdd
	 * 
	 * @return
	 */
	public String getCurDateFlag() {
		Calendar today = Calendar.getInstance();
		SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyyMMdd");
		return formatDateTime.format(today.getTime());
	}

	/**
	 * 获取带有毫秒的时间标志
	 * 
	 * @return
	 */
	public String getCurTimestamp() {
		Calendar today = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("HHmmssSSS");
		return fmt.format(today.getTime());
	}
}
