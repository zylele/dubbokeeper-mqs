package com.dubboclub.dk.web.model;

import java.math.BigInteger;

public class NotificationDto {
	private BigInteger id;
    private String type;
    private String receiver;
    
    
    
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

}
