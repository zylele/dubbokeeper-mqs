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
import com.dubboclub.dk.remote.MsgSystemService;
import com.dubboclub.dk.remote.esb.dto.SingleEmailReq;
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
//	@Autowired
//	private ApplicationMailer mailer;
	@Autowired
	MsgSystemService msgSystemService;
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
		//		ApplicationEmail email = new ApplicationEmail();
		NotificationPo po = new NotificationPo();
		po.setType("01");// 邮件
		List<NotificationPo> notificationPoList = notificationStorage.selectNotificationByConditions(po);
		//		email.setSubject("服务异常_"+serviceName);
		//		String addresses = "";
		//		for (NotificationPo notificationPo : notificationPoList) {
		//			addresses += notificationPo.getAddress() + ",";
		//		}
		//		email.setAddressee(addresses);
		//		email.setContent(error);
		//		mailer.sendMailByAsynchronousMode(email);
		SingleEmailReq singleEmailReq = new SingleEmailReq();
		singleEmailReq.setSceneCode("M001");
		singleEmailReq.setContentData(error);
		singleEmailReq.setServiceId("120020013");
		singleEmailReq.setSceneId("01");// 场景码
		// singleEmailReq.setTranMode("ONLINE");//交易模式
		singleEmailReq.setTranMode("234");// 交易模式
		singleEmailReq.setSourceType("DK-MQS");// 渠道编号
		singleEmailReq.setBranchId("90001");// 机构号
		singleEmailReq.setUserId("CB-IBSM");// 柜员号:核心-内管虚拟柜员
		singleEmailReq.setTranDate(new SimpleDateFormat(ConstantsUtil.DATE_FORMATA).format(new Date()));// 交易日期
		singleEmailReq.setTranTimestamp(new SimpleDateFormat(ConstantsUtil.DATE_FORMATB).format(new Date()));// 交易时间
		// singleEmailReq.setUserLang("CHINESE");//操作员语言
		singleEmailReq.setUserLang("en");// 操作员语言
		int i = (int)(Math.random()*900 + 100);
		singleEmailReq.setSeqNo(System.currentTimeMillis() + i +"" );// 渠道流水号
		singleEmailReq.setSystemId("IBS");// 发起方系统编码
		singleEmailReq.setCompany("");// 法人代表
		singleEmailReq.getSysHead().setSrcSysSvrid("0");// 源发起系统服务器Id
		msgSystemService.SendSingleEmail(singleEmailReq);
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
