package com.dubboclub.dk.storage.mysql;

import org.springframework.beans.factory.InitializingBean;

import com.dubboclub.dk.storage.NotificationStorage;
import com.dubboclub.dk.storage.model.NotificationPo;
import com.dubboclub.dk.storage.mysql.mapper.NotificationMapper;

public class MysqlNotificationStorage implements NotificationStorage,InitializingBean{
	private NotificationMapper notificationMapper;

	public NotificationMapper getNotificationMapper() {
		return notificationMapper;
	}

	public void setNotificationMapper(NotificationMapper notificationMapper) {
		this.notificationMapper = notificationMapper;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NotificationPo selectNotificationById(NotificationPo notificationPo) {
		// TODO Auto-generated method stub
		return notificationMapper.selectNotificationById(notificationPo);
	}

	@Override
	public Integer deleteNotificationById(NotificationPo notificationPo) {
		// TODO Auto-generated method stub
		return notificationMapper.deleteNotificationById(notificationPo);
	}

	@Override
	public Integer addNotification(NotificationPo notificationPo) {
		// TODO Auto-generated method stub
		return notificationMapper.addNotification(notificationPo);
	}

	@Override
	public Integer updateNotificationById(NotificationPo notificationPo) {
		// TODO Auto-generated method stub
		return notificationMapper.updateNotificationById(notificationPo);
	}

	

	


}
