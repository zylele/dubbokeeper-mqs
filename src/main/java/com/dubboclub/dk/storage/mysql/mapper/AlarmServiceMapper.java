package com.dubboclub.dk.storage.mysql.mapper;

import java.util.List;

import com.dubboclub.dk.storage.model.AlarmServicePo;

public interface AlarmServiceMapper {
	public AlarmServicePo selectAlarmServiceByServiceName(AlarmServicePo alarmServicePo);
	public Integer deleteAlarmService();
	public Integer addAlarmService(AlarmServicePo alarmServicePo);
	public List<AlarmServicePo> selectAlarmServiceAll();
}
