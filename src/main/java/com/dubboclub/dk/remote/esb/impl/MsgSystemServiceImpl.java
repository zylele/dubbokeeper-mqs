package com.dubboclub.dk.remote.esb.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.dubboclub.dk.remote.MsgSystemService;
import com.dubboclub.dk.remote.esb.base.EsbBaseOutBO;
import com.dubboclub.dk.remote.esb.base.EsbBaseRes;
import com.dubboclub.dk.remote.esb.dto.BatchSendSmsIn;
import com.dubboclub.dk.remote.esb.dto.SendSingleMsgIn;
import com.dubboclub.dk.remote.esb.dto.SingleEmailReq;
import com.dubboclub.dk.remote.esb.dto.SingleEmailRes;

/**
 * Create on 2017/5/29 消息平台外接接口
 * 
 * @author luobs
 */
@Service("msgSystemService")
public class MsgSystemServiceImpl implements MsgSystemService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MsgSystemServiceImpl.class);
	@Autowired
	EsbComServiceImpl esbComService;

	
	/**
	 * 短信发送
	 */
	@Override
	public EsbBaseOutBO sendSingleMsg(SendSingleMsgIn sendSingleMsgInput) {
		LOGGER.info("远程调用开始");
		sendSingleMsgInput.setSystemId("IBS");
		sendSingleMsgInput.setTxCode("100001");
		sendSingleMsgInput.setSceneId("01");
		sendSingleMsgInput.setUserLang("en");
		sendSingleMsgInput.setServiceId("120020013");
		sendSingleMsgInput.setSrcSysSvrid("0");
		sendSingleMsgInput.setTranMode("234");
		return esbComService.remoteCallnoLog(sendSingleMsgInput, EsbBaseOutBO.class,ConfigUtils.getProperty("esb.core.url.NTCP"));
	}
	
	/**
	 * 发送邮件
	 * 
	 */
	@Override
	public SingleEmailRes SendSingleEmail(SingleEmailReq singleEmailReq) {
		return esbComService.remoteCallNew(singleEmailReq, SingleEmailRes.class, ConfigUtils.getProperty("esb.core.url.NTCP"));
		 
	}

	/**
	 * 批量短信发送
	 * @param batchSendSmsIn
	 * @return
	 */
	@Override
	public EsbBaseRes batchSendSms(BatchSendSmsIn batchSendSmsIn) {
		return esbComService.remoteCallNew(batchSendSmsIn, EsbBaseRes.class, ConfigUtils.getProperty("esb.core.url.NTCP"));
	}
}
