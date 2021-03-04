package com.dubboclub.dk.storage;

import java.util.List;

import com.dubboclub.dk.storage.model.AlarmServicePo;

public interface AlarmServiceStorage {
	public Integer addAlarmService(AlarmServicePo alarmServicePo);
	public Integer deleteAlarmService();
	public AlarmServicePo selectAlarmServiceByServiceName(AlarmServicePo alarmServicePo);
	public List<AlarmServicePo> selectAlarmServiceAll();

}
