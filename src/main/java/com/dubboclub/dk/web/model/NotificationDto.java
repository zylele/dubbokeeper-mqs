package com.dubboclub.dk.web.model;

import java.math.BigInteger;

/**  
* @ClassName: NotificationDto  
* @Description:邮箱地址   
* @author zhangpengfei  
* @date 2019年3月11日   
*/
public class NotificationDto {
	private BigInteger id;
    private String type;
    private String receiver;
    private String address;
    private String chnlCode;
    private String chnlName;
    
    
    public String getChnlCode() {
		return chnlCode;
	}

	public void setChnlCode(String chnlCode) {
		this.chnlCode = chnlCode;
	}

	public String getChnlName() {
		return chnlName;
	}

	public void setChnlName(String chnlName) {
		this.chnlName = chnlName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
