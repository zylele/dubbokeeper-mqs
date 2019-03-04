package com.dubboclub.dk.storage;

import com.dubboclub.dk.storage.model.AlarmServicePo;

public interface AlarmServiceStorage {
	public Integer addAlarmService(AlarmServicePo alarmServicePo);
	public Integer deleteAlarmService(AlarmServicePo alarmServicePo);
	public AlarmServicePo selectAlarmServiceByServiceName(AlarmServicePo alarmServicePo);

}
