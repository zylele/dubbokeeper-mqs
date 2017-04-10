package com.dubboclub.dk.alarm.impl;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.dubboclub.dk.alarm.AlarmService;

public class AlarmServiceImpl implements AlarmService {
	
	@Override
	public void alarmHandle(URL url) {
		//empty协议的url，category==providers为提供者
		if(Constants.PROVIDERS_CATEGORY.equals(url.getParameter(Constants.CATEGORY_KEY))){
			// TODO 提供者不可用时，处理逻辑
		}
	}
	
}
