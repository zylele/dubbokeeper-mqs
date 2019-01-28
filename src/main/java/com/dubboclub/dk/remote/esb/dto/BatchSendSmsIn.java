package com.dubboclub.dk.remote.esb.dto;


import com.dubboclub.dk.remote.esb.base.EsbBaseReq;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BatchSendSmsIn extends EsbBaseReq {

    private static final long serialVersionUID = -1356185102103305699L;
    @JsonProperty("file_name")
    private String fileName;  //文件名称  发送文件的绝对路径，如/share/CB/MSG/CB_SMS_20180308111516.txt
    @JsonProperty("send_type")
    private String sendType;  //消息类型 字典，取值{SMS、APP、EMAIL、WECHAT}，分别表示{短信、App消息、邮件消息、微信消息}，批量发送短信填写SMS即可
    @JsonProperty("send_use")
    private String sendUse;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getSendUse() {
        return sendUse;
    }

    public void setSendUse(String sendUse) {
        this.sendUse = sendUse;
    }
}
