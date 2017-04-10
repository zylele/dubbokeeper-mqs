package com.dubboclub.dk.alarm;

import com.alibaba.dubbo.common.URL;

public interface AlarmService {
	/**
	 * 服务不可用时预警方法
	 * @param url
	 */
	void alarmHandle(URL url);
}
