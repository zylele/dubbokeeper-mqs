package com.dubboclub.dk.web.model;

public class BizWarningResultDto {
	public BizWarningResultDto(Integer count) {
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
