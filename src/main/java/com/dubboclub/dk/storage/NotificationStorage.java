package com.dubboclub.dk.storage;

import java.util.List;

import com.dubboclub.dk.storage.model.ChnlDefPo;
import com.dubboclub.dk.storage.model.CurrentPage;
import com.dubboclub.dk.storage.model.NotificationPo;

public interface NotificationStorage {
	public NotificationPo selectNotificationById(NotificationPo notificationPo);
	public Integer deleteNotificationById(NotificationPo notificationPo);
	public Integer addNotification(NotificationPo notificationPo);
	public Integer updateNotificationById(NotificationPo notificationPo);
	public List<NotificationPo> selectNotificationByPage(NotificationPo notificationPo, CurrentPage currentPage);
	public List<NotificationPo> selectNotificationByConditions(NotificationPo notificationPo);
	public List<ChnlDefPo> getChnlDef(ChnlDefPo chnlDefPo);
	public List<String> getMailByChnlcode(String chnlCode);

}
