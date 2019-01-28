package com.dubboclub.dk.remote.esb.base;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 *  互金公共请求报文头
 * @author luominggang
 */
@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class ReqHead implements Serializable {
  private static final long serialVersionUID = -6551222948323591162L;

  @JsonProperty("tx_code")
  private String txCode;        //交易码
  @JsonProperty("chnl_type")
  private String chnlType;      //源发起方类型
  @JsonProperty("chnl_date")
  private String chnlDate;      //源发起方日期
  @JsonProperty("chnl_trc_no")
  private String chnlTrcNo;     //源发起方日期
  @JsonProperty("chnl_time")
  private String chnlTime;      //源发起方时间
  @JsonProperty("tx_br_no")
  private String txBrNo;        //交易机构
  @JsonProperty("teller_no")
  private String tellerNo;      //操作员
  @JsonProperty("cif_no")
  private String cifNo;         //客户号
  @JsonProperty("cif_type")
  private String cifType;       //客户类型
  @JsonProperty("tx_ip")
  private String txIp;          //发起IP
  @JsonProperty("device_no")
  private String deviceNo;      //发起设备编号
  @JsonProperty("area_code")
  private String areaCode;
  @JsonProperty("pg_up_or_pg_dn")
  private String pgUpOrPgDn;
  @JsonProperty("page_size")
  private String pageSize;
  @JsonProperty("current_num")
  private String currentNum;
  @JsonProperty("total_flag")
  private String totalFlag;
  @JsonProperty("sign_type")
  private String signType;      //签名算法类型
  @JsonProperty("sign")
  private String sign;          //签名串
  @JsonProperty("version_id")
  private String versionId;  
  @JsonProperty("file_path")
  private String filePath;
  @JsonProperty("verify_code")
  private String verifyCode;    //验证码
  @JsonProperty("verify_no")    
  private String verifyNo;      //验证码编号
  @JsonProperty("bus_type")
  private String busType;       //业务类型
  @JsonProperty("src_sys_id")
  private String srcSysId;
  @JsonProperty("sys_id")
  private String sysId;
  @JsonProperty("com_br_no")
  private String comBrNo;
  @JsonProperty("system_id")
  private String systemId;
  @JsonProperty("tran_mode")
  private String TRAN_MODE;     
  @JsonProperty("partner_id")
  private String partnerId;      //合作方编号
  @JsonProperty("order_no")
  private String orderNo;       //订单编号
  @JsonProperty("tx_desc")
  private String txDesc;        //交易描述
  @JsonProperty("brf")
  private String brf;           //摘要

  public String getTxDesc() {
    return txDesc;
  }

  public void setTxDesc(String txDesc) {
    this.txDesc = txDesc;
  }

  public String getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(String partnerId) {
    this.partnerId = partnerId;
  }

  public String getVerifyCode() {
  	return verifyCode;
  }

	public void setVerifyCode(String verifyCode) {
  	this.verifyCode = verifyCode;
  }

	public String getVerifyNo() {
  	return verifyNo;
  }

	public void setVerifyNo(String verifyNo) {
  	this.verifyNo = verifyNo;
  }

	public String getBusType() {
  	return busType;
  }

	public void setBusType(String busType) {
  	this.busType = busType;
  }

	public static long getSerialversionuid() {
  	return serialVersionUID;
  }

	public String getTxCode() {
    return txCode;
  }

  public void setTxCode(String txCode) {
    this.txCode = txCode;
  }

  public String getChnlType() {
    return chnlType;
  }

  public void setChnlType(String chnlType) {
    this.chnlType = chnlType;
  }

  public String getChnlDate() {
    return chnlDate;
  }

  public void setChnlDate(String chnlDate) {
    this.chnlDate = chnlDate;
  }

  public String getChnlTrcNo() {
    return chnlTrcNo;
  }

  public void setChnlTrcNo(String chnlTrcNo) {
    this.chnlTrcNo = chnlTrcNo;
  }

  public String getChnlTime() {
    return chnlTime;
  }

  public void setChnlTime(String chnlTime) {
    this.chnlTime = chnlTime;
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

  public String getCifNo() {
    return cifNo;
  }

  public void setCifNo(String cifNo) {
    this.cifNo = cifNo;
  }

  public String getCifType() {
    return cifType;
  }

  public void setCifType(String cifType) {
    this.cifType = cifType;
  }

  public String getTxIp() {
    return txIp;
  }

  public void setTxIp(String txIp) {
    this.txIp = txIp;
  }

  public String getDeviceNo() {
    return deviceNo;
  }

  public void setDeviceNo(String deviceNo) {
    this.deviceNo = deviceNo;
  }

  public String getAreaCode() {
    return areaCode;
  }

  public void setAreaCode(String areaCode) {
    this.areaCode = areaCode;
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

  public String getSignType() {
    return signType;
  }

  public void setSignType(String signType) {
    this.signType = signType;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public String getVersionId() {
    return versionId;
  }

  public void setVersionId(String versionId) {
    this.versionId = versionId;
  }
  
public String getSrcSysId() {
    return srcSysId;
  }

  public void setSrcSysId(String srcSysId) {
    this.srcSysId = srcSysId;
  }

  public String getSysId() {
    return sysId;
  }

  public void setSysId(String sysId) {
    this.sysId = sysId;
  }

  public String getComBrNo() {
    return comBrNo;
  }

  public void setComBrNo(String comBrNo) {
    this.comBrNo = comBrNo;
  }
  
  public String getFilePath() {
	return filePath;
}

public void setFilePath(String filePath) {
	this.filePath = filePath;
}


public String getSystemId() {
	return systemId;
}

public void setSystemId(String systemId) {
	this.systemId = systemId;
}

public String getTRAN_MODE() {
	return TRAN_MODE;
}

public void setTRAN_MODE(String tRAN_MODE) {
	TRAN_MODE = tRAN_MODE;
}

public String getOrderNo() {
	return orderNo;
}

public void setOrderNo(String orderNo) {
	this.orderNo = orderNo;
}



public String getBrf() {
	return brf;
}

public void setBrf(String brf) {
	this.brf = brf;
}

//public String getOriginJson() {
//    return originJson;
//}
//
//public void setOriginJson(String originJson) {
//    this.originJson = originJson;
//}

@Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("ReqHead{");
    sb.append("txCode='").append(txCode).append('\'');
    sb.append(", chnlType='").append(chnlType).append('\'');
    sb.append(", chnlDate='").append(chnlDate).append('\'');
    sb.append(", chnlTrcNo='").append(chnlTrcNo).append('\'');
    sb.append(", chnlTime='").append(chnlTime).append('\'');
    sb.append(", txBrNo='").append(txBrNo).append('\'');
    sb.append(", tellerNo='").append(tellerNo).append('\'');
    sb.append(", cifNo='").append(cifNo).append('\'');
    sb.append(", cifType='").append(cifType).append('\'');
    sb.append(", txIp='").append(txIp).append('\'');
    sb.append(", deviceNo='").append(deviceNo).append('\'');
    sb.append(", areaCode='").append(areaCode).append('\'');
    sb.append(", pgUpOrPgDn='").append(pgUpOrPgDn).append('\'');
    sb.append(", pageSize='").append(pageSize).append('\'');
    sb.append(", currentNum='").append(currentNum).append('\'');
    sb.append(", totalFlag='").append(totalFlag).append('\'');
    sb.append(", signType='").append(signType).append('\'');
    sb.append(", sign='").append(sign).append('\'');
    sb.append(", versionId='").append(versionId).append('\'');
    sb.append(", filePath='").append(filePath).append('\'');
    sb.append(", verifyCode='").append(verifyCode).append('\'');
    sb.append(", verifyNo='").append(verifyNo).append('\'');
    sb.append(", busType='").append(busType).append('\'');
    sb.append(", srcSysId='").append(srcSysId).append('\'');
    sb.append(", sysId='").append(sysId).append('\'');
    sb.append(", comBrNo='").append(comBrNo).append('\'');
    sb.append(", systemId='").append(systemId).append('\'');
    sb.append(", TRAN_MODE='").append(TRAN_MODE).append('\'');
    sb.append(", orderNo='").append(orderNo).append('\'');
    sb.append(", brf='").append(brf).append('\'');
//    sb.append(", originJson='").append(originJson).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
