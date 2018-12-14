package com.dubboclub.dk.alarm.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.dubboclub.dk.alarm.AlarmService;
import com.dubboclub.dk.storage.ServiceWarningStorage;
import com.dubboclub.dk.storage.model.ServiceWarningPo;
import com.dubboclub.dk.web.utils.ConstantsUtil;

public class AlarmServiceImpl implements AlarmService {
    @Autowired
    @Qualifier("serviceWarningStorage")
    private ServiceWarningStorage serviceWarningStorage;
	@Override
	public void alarmHandle(URL url) {
		//empty协议的url，category==providers为提供者
		if(Constants.PROVIDERS_CATEGORY.equals(url.getParameter(Constants.CATEGORY_KEY))){
			// TODO 提供者不可用时，处理逻辑
		    ServiceWarningPo serviceWarning = new ServiceWarningPo();
		    serviceWarning.setContent(url.toFullString());
		    serviceWarning.setStartTime(new SimpleDateFormat(ConstantsUtil.DATE_FORMAT).format(new Date()));
		    serviceWarning.setHost(url.getHost());
		    serviceWarning.setServiceInterface(url.getServiceInterface());
		    serviceWarning.setStatus("00");
		    serviceWarningStorage.insertServiceWarning(serviceWarning);
		}
	}
	
}
