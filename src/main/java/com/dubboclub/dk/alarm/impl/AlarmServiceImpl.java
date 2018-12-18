package com.dubboclub.dk.alarm.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.dubboclub.dk.alarm.AlarmService;
import com.dubboclub.dk.notification.ApplicationEmail;
import com.dubboclub.dk.notification.ApplicationMailer;
import com.dubboclub.dk.notification.WarningStatusHolder;
import com.dubboclub.dk.storage.NotificationStorage;
import com.dubboclub.dk.storage.ServiceWarningStorage;
import com.dubboclub.dk.storage.model.NotificationPo;
import com.dubboclub.dk.storage.model.ServiceWarningPo;
import com.dubboclub.dk.web.utils.ConstantsUtil;

public class AlarmServiceImpl implements AlarmService {
    private static final Logger logger = LoggerFactory.getLogger(AlarmServiceImpl.class);
	@Autowired
	@Qualifier("notificationStorage")
	private NotificationStorage notificationStorage;
	@Autowired
	@Qualifier("serviceWarningStorage")
	private ServiceWarningStorage serviceWarningStorage;
	@Autowired
	private ApplicationMailer mailer;
	@Autowired
	private WarningStatusHolder warningStatusHolder;
	private static long interval = 300000;//一定时间间隔内不再发送告警
	private static Date lastSendTime;

	@Override
	public void alarmHandle(URL url) {
		// empty协议的url，category==providers为提供者
		if (Constants.PROVIDERS_CATEGORY.equals(url.getParameter(Constants.CATEGORY_KEY))) {
			// TODO 提供者不可用时，处理逻辑
			ServiceWarningPo serviceWarning = new ServiceWarningPo();
			serviceWarning.setContent(url.toFullString());
			serviceWarning.setStartTime(new SimpleDateFormat(ConstantsUtil.DATE_FORMAT).format(new Date()));
			serviceWarning.setHost(url.getHost());
			serviceWarning.setServiceInterface(url.getServiceInterface());
			serviceWarning.setStatus("00");
			serviceWarningStorage.insertServiceWarning(serviceWarning);
			if (isAllowedSend()) {
				sendWarningMailAsyc(serviceWarning.getStartTime() + ": 核心服务异常，请马上处理！");
				warningStatusHolder.setServiceStatus(true);
				logger.debug("服务异常，服务异常，服务异常！！！");
			}
		}
	}

	private void sendWarningMailAsyc(String error) {
		ApplicationEmail email = new ApplicationEmail();
		NotificationPo po = new NotificationPo();
		po.setType("01");// 邮件
		List<NotificationPo> notificationPoList = notificationStorage.selectNotificationByConditions(po);
		email.setSubject("服务异常");
		String addresses = "";
		for (NotificationPo notificationPo : notificationPoList) {
			addresses += notificationPo.getAddress() + ",";
		}
		email.setAddressee(addresses);
		email.setContent(error);
		mailer.sendMailByAsynchronousMode(email);
	}

	private boolean isAllowedSend() {
		if (lastSendTime == null) {
			lastSendTime = new Date();
			return true;
		} else if (((new Date()).getTime() - lastSendTime.getTime()) > interval){
			lastSendTime = new Date();
			return true;
		}
		return false;
	}

}
