package com.dubboclub.dk.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dubboclub.dk.notification.WarningStatusHolder;
import com.dubboclub.dk.remote.MsgSystemService;
import com.dubboclub.dk.remote.esb.dto.SendEmailReq;
import com.dubboclub.dk.remote.esb.dto.SendSingleMsgIn;
import com.dubboclub.dk.remote.esb.dto.SingleEmailReq;
import com.dubboclub.dk.storage.BizWarningStorage;
import com.dubboclub.dk.storage.NotificationStorage;
import com.dubboclub.dk.storage.model.BizWarningPo;
import com.dubboclub.dk.storage.model.CurrentPage;
import com.dubboclub.dk.storage.model.NotificationPo;
import com.dubboclub.dk.web.utils.ConstantsUtil;

@Component
public class BizExceptionTaskImpl implements BizExceptionTask {
    private static final Logger logger = LoggerFactory.getLogger(BizExceptionTaskImpl.class);
    @Autowired
    @Qualifier("notificationStorage")
    private NotificationStorage notificationStorage;
    @Autowired
    @Qualifier("bizWarningStorage")
    private BizWarningStorage bizWarningStorage;
//    @Autowired
//    private ApplicationMailer mailer;
    @Autowired
	MsgSystemService msgSystemService;
    @Autowired
	private WarningStatusHolder warningStatusHolder;
    private final static String BIZ_EXCEPTION_URL="/zipkin/api/v2/traces?annotationQuery=error&limit=100&lookback=6000000";
	private String zipkinUrl;
	private String sendMail;
	private String sendPhone;
    
    @PostConstruct
    public void init() {
    	zipkinUrl = ConfigUtils.getProperty("zipkin.url");
    	sendMail = ConfigUtils.getProperty("sendMail.url");
    	sendPhone = ConfigUtils.getProperty("sendPhone.url");
    }
    
    @Scheduled(cron="0/10 * *  * * ? ")   //每10秒执行一次    
    @Override  
	public void getBizExceptionTask() {
		// String zipkinUrl = ConfigUtils.getProperty("zipkin.url");
		RestTemplate restTemplate = new RestTemplate();
		String data = null;
		try {
			data = restTemplate.getForObject(zipkinUrl + BIZ_EXCEPTION_URL, String.class);
		} catch (Exception e) {
			logger.warn("Can't connect Zipkin Server!!!");
			return;
		}
		JSONArray jsonErrors = JSONArray.parseArray(data);
		for (Object jsonError : jsonErrors) {
			String error = "";
			String txCode = "";
			if (jsonError instanceof JSONArray) {
				for (Object span : (JSONArray) jsonError) {
					String serviceName = ((JSONObject) span).getJSONObject("localEndpoint").getString("serviceName");
					if (methodName(serviceName)) {
						if (span instanceof JSONObject) {
							txCode = ((JSONObject) span).getJSONObject("tags").getString("txCode");
							String traceId = ((JSONObject) span).getString("traceId");
							Long timestamp = ((JSONObject) span).getLong("timestamp");
							BizWarningPo bizWarningPo = new BizWarningPo();
							bizWarningPo.setTraceId(traceId);
							CurrentPage currentPage = new CurrentPage();
							currentPage.setCurrentPage(1);
							currentPage.setPageSize(10);
							List<BizWarningPo> result = bizWarningStorage.selectBizWarningByPage(bizWarningPo,
									currentPage);
							if (result == null || result.size() == 0) {
								bizWarningPo.setTraceContent(span.toString());
								bizWarningPo.setTraceDt(new SimpleDateFormat(ConstantsUtil.DATE_FORMAT)
										.format(new Date(timestamp / 1000)));
								error = ((JSONObject) span).getJSONObject("tags").getString("error");
								if (!error.equals("normal")) {
									bizWarningPo.setError(error);
									bizWarningPo.setTxCode(txCode);
									bizWarningStorage.addBizWarning(bizWarningPo);

									SendEmailReq sendEmailReq = new SendEmailReq();
									sendEmailReq.setSceneCode("M001");
									sendEmailReq.setBusType("OutOpenAcc");
									sendEmailReq.setSubject(ConstantsUtil.MAIL_SUBJECT);
									sendEmailReq.setMailTo(queryAddress());
									sendEmailReq.setAttachments(null);
									sendEmailReq.setMsg("新的业务异常，traceId: " + traceId + ",error: " + error);

									logger.debug("新的业务异常，traceId: " + traceId + ",error: " + error);
									sendWarningMailAsyc(sendEmailReq, txCode);
									warningStatusHolder.setBizStatus(true);
								}
							}
							break;
						}

					}
				}
			}
		}
	}

	
	private void sendWarningMailAsyc(SendEmailReq error, String txCode) {
//				ApplicationEmail email = new ApplicationEmail();
//				NotificationPo po = new NotificationPo();
//				po.setType("01");//邮件
//				List<NotificationPo> notificationPoList = notificationStorage.selectNotificationByConditions(po);
//				email.setSubject("业务异常_"+txCode);
//				String addresses = "";
//				for(NotificationPo notificationPo : notificationPoList) {
//					addresses += notificationPo.getAddress() + ",";
//				}
//				email.setAddressee(addresses);
//				email.setContent(error);
//				mailer.sendMailByAsynchronousMode(email);
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
		int i = (int)(Math.random()*900 + 100);
		singleEmailReq.setSeqNo(System.currentTimeMillis() + i +"" );// 渠道流水号
		singleEmailReq.setSystemId("IBS");// 发起方系统编码
		singleEmailReq.setCompany("");// 法人代表
		singleEmailReq.getSysHead().setSrcSysSvrid("0");// 源发起系统服务器Id
		if(sendMail.equals("true"))
			msgSystemService.SendSingleEmail(singleEmailReq);
		//短信
		SendSingleMsgIn sendSingleMsgInput = new SendSingleMsgIn();
		sendSingleMsgInput.setScene_code("0099");
		sendSingleMsgInput.setMobiles(queryPhoneNum());
		sendSingleMsgInput.setContent_data(error.getContent());
		if(sendPhone.equals("true"))
			msgSystemService.sendSingleMsg(sendSingleMsgInput);
		
	}
	
	private List<String> queryAddress(){
		NotificationPo notificationPo = new NotificationPo();
		notificationPo.setType("01");
		List<NotificationPo> notificationPos = notificationStorage.selectNotificationByConditions(notificationPo);
		List<String> mails = new ArrayList<String>();
		for (NotificationPo notificationPo2 : notificationPos) {
			mails.add(notificationPo2.getAddress());
		}
		return mails;
		
	}
	//取到数据库中手机号
	private List<String> queryPhoneNum(){
		NotificationPo notificationPo = new NotificationPo();
		notificationPo.setType("02");
		List<NotificationPo> notificationPos = notificationStorage.selectNotificationByConditions(notificationPo);
		List<String> PhoneNums = new ArrayList<String>();
		for(NotificationPo notificationPo2 : notificationPos){
			PhoneNums.add(notificationPo2.getAddress());
		}
		return PhoneNums;
	}
	private boolean methodName (String servicename){
		int server = servicename.indexOf("server");
		int client = servicename.indexOf("client");
		if(server != -1 || client != -1){
			return true;
		}
		return false;	
	}

}
