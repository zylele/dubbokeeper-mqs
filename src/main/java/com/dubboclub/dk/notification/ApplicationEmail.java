/**
 * 
 */
package com.dubboclub.dk.notification;

import java.io.Serializable;

/**
 * Copyright: Copyright (c) 2018 东华软件股份公司
 * 
 * @Description: 该类的功能描述
 *
 * @author: 黄祖真
 * @date: 2018年12月12日 上午8:58:41
 *
 */
public class ApplicationEmail  implements Serializable {
    /** 收件人 **/
    private String addressee;
    /** 抄送给 **/
    private String cc;
    /** 邮件主题 **/
    private String subject;
    /** 邮件内容 **/
    private String content;

    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
