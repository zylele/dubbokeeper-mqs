package com.dubboclub.dk.web.model;

public class ServiceWarningResultDto {
	public ServiceWarningResultDto(Integer count) {
		super();
		this.count = count;
	}
	private Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
