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
import com.dubboclub.dk.common.SendMessage;
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
	private SendMessage sendMessage;
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
	private final static String BIZ_EXCEPTION_URL = "/zipkin/api/v2/traces?annotationQuery=error&limit=100&lookback=20000";
	private String zipkinUrl;
	private String sendBizException;


	@PostConstruct
	public void init() {
		zipkinUrl = ConfigUtils.getProperty("zipkin.url");
		sendBizException = ConfigUtils.getProperty("sendBizException.url");
	}

	@Scheduled(cron = "0/10 * *  * * ? ") // 每10秒执行一次
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
//		遍历zipkin返回的josn串
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
								if (error != null) {
									bizWarningPo.setError(error);
									bizWarningPo.setTxCode(txCode);
									bizWarningStorage.addBizWarning(bizWarningPo);
									/*
									 * SendEmailReq sendEmailReq = new SendEmailReq();
									 * sendEmailReq.setSceneCode("M001"); sendEmailReq.setBusType("OutOpenAcc");
									 * sendEmailReq.setSubject(ConstantsUtil.MAIL_SUBJECT);
									 * sendEmailReq.setMailTo(sendMessage.queryAddress());
									 * sendEmailReq.setAttachments(null); sendEmailReq.setMsg("新的业务异常，traceId: " +
									 * traceId + ",error: " + error); if(sendBizException.equals("true"))
									 * sendMessage.sendWarningMailAsyc(sendEmailReq, txCode);
									 */
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

	private boolean methodName(String servicename) {
		int server = servicename.indexOf("server");
		int client = servicename.indexOf("client");
		if (server != -1 || client != -1) {
			return true;
		}
		return false;
	}

}
