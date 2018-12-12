package com.dubboclub.dk.storage;

import com.dubboclub.dk.storage.model.NotificationPo;

public interface NotificationStorage {
	public NotificationPo selectNotificationById(NotificationPo notificationPo);
	public Integer deleteNotificationById(NotificationPo notificationPo);
	public Integer addNotification(NotificationPo notificationPo);
	public Integer updateNotificationById(NotificationPo notificationPo);
}
