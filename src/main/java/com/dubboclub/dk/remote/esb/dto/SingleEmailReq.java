package com.dubboclub.dk.remote.esb.dto;


import com.dubboclub.dk.remote.esb.base.EsbBaseReq;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 发送邮件，请求消息平台系统请求体(100001)
 * @author caizhongpeng
 *
 */
public class SingleEmailReq extends EsbBaseReq{

	private static final long serialVersionUID = 3544181142652974230L;
	
	    @JsonProperty("scene_code")
	    private String sceneCode;	//场景码
	    @JsonProperty("cif_type")
	    private String cifType;		//客户类型
	    @JsonProperty("cif_no")
	    private String cifNo;		//客户号
	    @JsonProperty("acc_no")
	    private String accNo;		//客户账号
	    @JsonProperty("mobiles")
	    private String mobiles;		//手机号
	    @JsonProperty("content_data")
	    private String contentData;	//内容数据
	    @JsonProperty("use")
	    private String use;			//用途
	    @JsonProperty("send_time")
	    private String sendTime;	//发送时间
		public String getSceneCode() {
			return sceneCode;
		}
		public void setSceneCode(String sceneCode) {
			this.sceneCode = sceneCode;
		}
		public String getCifType() {
			return cifType;
		}
		public void setCifType(String cifType) {
			this.cifType = cifType;
		}
		public String getCifNo() {
			return cifNo;
		}
		public void setCifNo(String cifNo) {
			this.cifNo = cifNo;
		}
		public String getAccNo() {
			return accNo;
		}
		public void setAccNo(String accNo) {
			this.accNo = accNo;
		}
		public String getMobiles() {
			return mobiles;
		}
		public void setMobiles(String mobiles) {
			this.mobiles = mobiles;
		}
		public String getContentData() {
			return contentData;
		}
		public void setContentData(String contentData) {
			this.contentData = contentData;
		}
		public String getUse() {
			return use;
		}
		public void setUse(String use) {
			this.use = use;
		}
		public String getSendTime() {
			return sendTime;
		}
		public void setSendTime(String sendTime) {
			this.sendTime = sendTime;
		}
	    
	    

}
