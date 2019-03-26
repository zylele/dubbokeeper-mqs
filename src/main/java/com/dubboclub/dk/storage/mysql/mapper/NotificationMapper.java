/**
 * 
 */
package com.dubboclub.dk.storage.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dubboclub.dk.storage.model.ChnlDefPo;
import com.dubboclub.dk.storage.model.NotificationPo;

/**
 * Copyright: Copyright (c) 2018 东华软件股份公司
 * 
 * @Description: 该类的功能描述
 *
 * @author: 黄祖真
 * @date: 2018年12月7日 上午10:28:18 
 *
 */
public interface NotificationMapper {
	public NotificationPo selectNotificationById(@Param("notification")NotificationPo notification);
	public Integer deleteNotificationById(@Param("notification")NotificationPo notificationPo);
	public Integer addNotification(@Param("notification")NotificationPo notificationPo);
	public Integer updateNotificationById(NotificationPo notificationPo);
	public List<NotificationPo> selectNotificationByPage(NotificationPo notificationPo);
	public List<NotificationPo> selectNotificationByConditions(NotificationPo notificationPo);
	public List<ChnlDefPo> getChnlDef(ChnlDefPo chnlDefPo);
}
