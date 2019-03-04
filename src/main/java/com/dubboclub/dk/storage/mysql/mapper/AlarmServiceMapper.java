package com.dubboclub.dk.storage.mysql.mapper;

import com.dubboclub.dk.storage.model.AlarmServicePo;

public interface AlarmServiceMapper {
	public AlarmServicePo selectAlarmServiceByServiceName(AlarmServicePo alarmServicePo);
	public Integer deleteAlarmService(AlarmServicePo alarmServicePo);
	public Integer addAlarmService(AlarmServicePo alarmServicePo);
}
