package com.dubboclub.dk.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.dubboclub.dk.admin.service.ApplicationService;
import com.dubboclub.dk.remote.MsgSystemService;
import com.dubboclub.dk.remote.esb.dto.SendEmailReq;
import com.dubboclub.dk.remote.esb.dto.SendSingleMsgIn;
import com.dubboclub.dk.remote.esb.dto.SingleEmailReq;
import com.dubboclub.dk.storage.AlarmServiceStorage;
import com.dubboclub.dk.storage.NotificationStorage;
import com.dubboclub.dk.storage.model.NotificationPo;
import com.dubboclub.dk.task.ServiceWarningTaskImpl;
import com.dubboclub.dk.web.utils.ConstantsUtil;


    /**  
    * @ClassName: SendMessage  
    * @Description: 业务信息发邮件的公共类
    * @author jinxiaolei  
    * @date 2019年3月5日   
    */  
public class SendMessage {
	
	private static final Logger logger = LoggerFactory.getLogger(SendMessage.class);
	
    @Autowired
	MsgSystemService msgSystemService;
    @Autowired
    private NotificationStorage notificationStorage;   
	private String sendMail;
	private String sendPhone;
    
    @PostConstruct
    public void init() {
    	sendMail = ConfigUtils.getProperty("sendMail.url");
    	sendPhone = ConfigUtils.getProperty("sendPhone.url");
    }
	

    /**
     * 发送邮件 
     */
	public void sendWarningMailAsyc(SendEmailReq error, String txCode) {
		SingleEmailReq singleEmailReq = new SingleEmailReq();
		singleEmailReq.setSceneCode("M001");
		singleEmailReq.setContentData(error.getContent());
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
		int i = (int) (Math.random() * 900 + 100);
		singleEmailReq.setSeqNo(System.currentTimeMillis() + i + "");// 渠道流水号
		singleEmailReq.setSystemId("IBS");// 发起方系统编码
		singleEmailReq.setCompany("");// 法人代表
		singleEmailReq.getSysHead().setSrcSysSvrid("0");// 源发起系统服务器Id
		if (sendMail.equals("true")){
			logger.info("sendMail ==>  true,发送邮件！");
			msgSystemService.SendSingleEmail(singleEmailReq);
		}
			
	}
	

	/**
	 * 手机发送
	 */
	public void sendWarningPhoneAsyc(SendEmailReq error, String txCode) {
		// 短信
		SendSingleMsgIn sendSingleMsgInput = new SendSingleMsgIn();
		sendSingleMsgInput.setScene_code("M001");
		sendSingleMsgInput.setMobiles(queryPhoneNum());
		sendSingleMsgInput.setContent_data(error.getContent());
		if (sendPhone.equals("true")){
			logger.info("sendPhone ==>  true,发送短信！");
			msgSystemService.sendSingleMsg(sendSingleMsgInput);
		}

	}
	
	
	/**
	 * 查询邮件地址 
	 */
	public List<String> queryAddress(){
		NotificationPo notificationPo = new NotificationPo();
		notificationPo.setType("01");
		List<NotificationPo> notificationPos = notificationStorage.selectNotificationByConditions(notificationPo);
		List<String> mails = new ArrayList<String>();
		for (NotificationPo notificationPo2 : notificationPos) {
			mails.add(notificationPo2.getAddress());
		}
		return mails;		
	}
	
	
	/**
	 * 查询手机号码
	 */
	public List<String> queryPhoneNum(){
		NotificationPo notificationPo = new NotificationPo();
		notificationPo.setType("02");
		List<NotificationPo> notificationPos = notificationStorage.selectNotificationByConditions(notificationPo);
		List<String> PhoneNums = new ArrayList<String>();
		for(NotificationPo notificationPo2 : notificationPos){
			PhoneNums.add(notificationPo2.getAddress());
		}
		return PhoneNums;
	}
}
