package com.dubboclub.dk.remote;

import com.dubboclub.dk.remote.esb.base.EsbBaseOutBO;
import com.dubboclub.dk.remote.esb.base.EsbBaseRes;
import com.dubboclub.dk.remote.esb.dto.BatchSendSmsIn;
import com.dubboclub.dk.remote.esb.dto.SendSingleMsgIn;
import com.dubboclub.dk.remote.esb.dto.SingleEmailReq;
import com.dubboclub.dk.remote.esb.dto.SingleEmailRes;

/**
 * Create on 2017/5/10
 * 消息平台外接接口
 *
 * @author luobs
 */
public interface MsgSystemService {
	/**
	 * 短信发送
	 * @param sendSingleMsg
	 * @return
	 */
	EsbBaseOutBO sendSingleMsg(SendSingleMsgIn sendSingleMsgInput);	
	
	/**
	 * 发送邮件
	 * @param singleEmailReq
	 * @return
	 */
	SingleEmailRes SendSingleEmail(SingleEmailReq singleEmailReq);

	/**
	 * 批量短信发送
	 * @param batchSendSmsIn
	 * @return
	 */
	EsbBaseRes batchSendSms(BatchSendSmsIn batchSendSmsIn);
}
