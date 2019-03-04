package com.dubboclub.dk.storage.mysql;

import org.springframework.beans.factory.InitializingBean;

import com.dubboclub.dk.storage.AlarmServiceStorage;
import com.dubboclub.dk.storage.model.AlarmServicePo;
import com.dubboclub.dk.storage.mysql.mapper.AlarmServiceMapper;

public class MysqlAlarmServiceStorage implements AlarmServiceStorage,InitializingBean{
	private AlarmServiceMapper alarmServiceMapper;

	public AlarmServiceMapper getAlarmServiceMapper() {
		return alarmServiceMapper;
	}

	public void setAlarmServiceMapper(AlarmServiceMapper alarmServiceMapper) {
		this.alarmServiceMapper = alarmServiceMapper;
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Integer addAlarmService(AlarmServicePo alarmServicePo) {
		return alarmServiceMapper.addAlarmService(alarmServicePo);
	}
	@Override
	public AlarmServicePo selectAlarmServiceByServiceName(AlarmServicePo alarmServicePo) {
		return alarmServiceMapper.selectAlarmServiceByServiceName(alarmServicePo);
	}

	@Override
	public Integer deleteAlarmService(AlarmServicePo alarmServicePo) {
		// TODO Auto-generated method stub
		return alarmServiceMapper.deleteAlarmService(alarmServicePo);
	}
	

	

	

	
}
