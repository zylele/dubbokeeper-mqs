package com.dubboclub.dk.notification;

import org.springframework.stereotype.Service;

@Service("warningStatusHolder")
public class WarningStatusHolder {
	private boolean serviceStatus;
	private boolean bizStatus;

	public boolean isServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(boolean serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public boolean isBizStatus() {
		return bizStatus;
	}

	public void setBizStatus(boolean bizStatus) {
		this.bizStatus = bizStatus;
	}

}
