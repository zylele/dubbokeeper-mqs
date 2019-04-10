package com.dubboclub.dk.storage.model;

/**  
* @ClassName: BizWarningQuery  
* @Description:Biz_warning表查询实体   
* @author zhangpengfei  
* @date 2019年3月11日   
*/
public class BizWarningQuery {
	private String bizStartDate;
	private String bizEndDate;
	private String txCode;
	private String chnlType;
	public String getBizStartDate() {
		return bizStartDate;
	}
	public void setBizStartDate(String bizStartDate) {
		this.bizStartDate = bizStartDate;
	}
	public String getBizEndDate() {
		return bizEndDate;
	}
	public void setBizEndDate(String bizEndDate) {
		this.bizEndDate = bizEndDate;
	}
	public String getChnlType() {
		return chnlType;
	}
	public void setChnlType(String chnlType) {
		this.chnlType = chnlType;
	}
	public String getTxCode() {
		return txCode;
	}
	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

}
