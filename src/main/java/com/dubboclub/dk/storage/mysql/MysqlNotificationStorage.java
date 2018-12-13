package com.dubboclub.dk.storage.mysql;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.dubboclub.dk.storage.NotificationStorage;
import com.dubboclub.dk.storage.model.CurrentPage;
import com.dubboclub.dk.storage.model.NotificationPo;
import com.dubboclub.dk.storage.mysql.mapper.NotificationMapper;
import com.github.pagehelper.PageHelper;

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

	@Override
	public List<NotificationPo> selectNotificationByPage(NotificationPo notificationPo, CurrentPage currentPage) {
		// TODO Auto-generated method stub
		PageHelper.startPage(currentPage.getCurrentPage(), currentPage.getPageSize());
        List<NotificationPo> notifications = notificationMapper.selectNotificationByPage(notificationPo);
        return notifications;
	}

	

	


}
