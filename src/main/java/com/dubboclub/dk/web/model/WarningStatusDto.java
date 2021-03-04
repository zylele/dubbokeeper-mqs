package com.dubboclub.dk.web.model;

public class WarningStatusDto {
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
