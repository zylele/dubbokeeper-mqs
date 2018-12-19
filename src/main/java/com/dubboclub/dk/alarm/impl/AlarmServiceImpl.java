package com.dubboclub.dk.alarm.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private static Map<String, Date> serviceMap = new HashMap<String, Date>();

	@Override
	public void alarmHandle(URL url,String application) {
		// empty协议的url，category==providers为提供者
		if (Constants.PROVIDERS_CATEGORY.equals(url.getParameter(Constants.CATEGORY_KEY))) {
			//提供者不可用时，处理逻辑
			ServiceWarningPo serviceWarning = new ServiceWarningPo();
			serviceWarning.setContent(url.toFullString());
			serviceWarning.setStartTime(new SimpleDateFormat(ConstantsUtil.DATE_FORMAT).format(new Date()));
			serviceWarning.setHost(url.getHost());
			serviceWarning.setServiceInterface(url.getServiceInterface());
			serviceWarning.setStatus("00");
			serviceWarning.setApplication(application);
			serviceWarning.setCategory(Constants.PROVIDERS_CATEGORY);
			serviceWarningStorage.insertServiceWarning(serviceWarning);
			if (isAllowedSend(application)) {
				String msg = serviceWarning.getStartTime() + ": "+application+" 服务异常，请马上处理！";
				sendWarningMailAsyc(msg,application);
				warningStatusHolder.setServiceStatus(true);
				logger.debug(msg);
			}
		} else if (Constants.CONSUMERS_CATEGORY.equals(url.getParameter(Constants.CATEGORY_KEY))) {
			ServiceWarningPo serviceWarning = new ServiceWarningPo();
			serviceWarning.setContent(url.toFullString());
			serviceWarning.setStartTime(new SimpleDateFormat(ConstantsUtil.DATE_FORMAT).format(new Date()));
			serviceWarning.setHost(url.getHost());
			serviceWarning.setServiceInterface(url.getServiceInterface());
			serviceWarning.setStatus("00");
			serviceWarning.setApplication(application);
			serviceWarning.setCategory(Constants.CONSUMERS_CATEGORY);
			serviceWarningStorage.insertServiceWarning(serviceWarning);
			if (isAllowedSend(application)) {
				String msg = serviceWarning.getStartTime() + ": "+application+" 服务异常，请马上处理！";
				sendWarningMailAsyc(msg,application);
				warningStatusHolder.setServiceStatus(true);
				logger.debug(msg);
			}
		}
	}

	private void sendWarningMailAsyc(String error,String serviceName) {
		ApplicationEmail email = new ApplicationEmail();
		NotificationPo po = new NotificationPo();
		po.setType("01");// 邮件
		List<NotificationPo> notificationPoList = notificationStorage.selectNotificationByConditions(po);
		email.setSubject("服务异常-"+serviceName);
		String addresses = "";
		for (NotificationPo notificationPo : notificationPoList) {
			addresses += notificationPo.getAddress() + ",";
		}
		email.setAddressee(addresses);
		email.setContent(error);
		mailer.sendMailByAsynchronousMode(email);
	}

	private boolean isAllowedSend(String application) {
		Date lastSendTime = serviceMap.get(application);
		if (lastSendTime == null) {
			lastSendTime = new Date();
			serviceMap.put(application, lastSendTime);
			return true;
		} else if (((new Date()).getTime() - lastSendTime.getTime()) > interval){
			lastSendTime = new Date();
			serviceMap.put(application, lastSendTime);
			return true;
		}
		return false;
	}

}
