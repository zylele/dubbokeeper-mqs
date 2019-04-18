package com.dubboclub.dk.remote.esb.dto;


import com.dubboclub.dk.remote.esb.base.EsbBaseReq;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * 单次消息发送请求实体
 * 
 * @author liumintao
 *
 */
public class SendSingleMsgIn  extends EsbBaseReq{
	@JsonProperty("scene_code")
	private String scene_code;  //场景码
	@JsonProperty("cif_type")
	private String cif_type;  //客户类型
	@JsonProperty("cif_no")
	private String cif_no;  //客户号
	@JsonProperty("acc_no")
	private String acc_no;   //客户账号
	@JsonProperty("mobiles")
	private String mobiles;  //手机号
	@JsonProperty("content_data")
	private String content_data;  //内容数据
	@JsonProperty("use")
	private String use; //用途
	@JsonProperty("send_time")
	private String send_time;  //发送时间
	@JsonProperty("")
	
	public String getScene_code() {
		return scene_code;
	}
	public void setScene_code(String scene_code) {
		this.scene_code = scene_code;
	}
	public String getCif_type() {
		return cif_type;
	}
	public void setCif_type(String cif_type) {
		this.cif_type = cif_type;
	}
	public String getCif_no() {
		return cif_no;
	}
	public void setCif_no(String cif_no) {
		this.cif_no = cif_no;
	}
	public String getAcc_no() {
		return acc_no;
	}
	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
	}

	public String getMobiles() {
		return mobiles;
	}
	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}
	public String getContent_data() {
		return content_data;
	}
	public void setContent_data(String content_data) {
		this.content_data = content_data;
	}
	public String getUse() {
		return use;
	}
	public void setUse(String use) {
		this.use = use;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	
}
