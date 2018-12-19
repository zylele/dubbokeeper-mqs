package com.dubboclub.dk.task;

import java.text.SimpleDateFormat;
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
import com.dubboclub.dk.notification.ApplicationEmail;
import com.dubboclub.dk.notification.ApplicationMailer;
import com.dubboclub.dk.notification.WarningStatusHolder;
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
    @Autowired
    private ApplicationMailer mailer;
    @Autowired
	private WarningStatusHolder warningStatusHolder;
    private final static String BIZ_EXCEPTION_URL="/zipkin/api/v2/traces?annotationQuery=error&limit=100&lookback=6000000";
	private String zipkinUrl;
    
    @PostConstruct
    public void init() {
    	zipkinUrl = ConfigUtils.getProperty("zipkin.url");
    }
    
    @Scheduled(cron="0/10 * *  * * ? ")   //每10秒执行一次    
    @Override  
	public void getBizExceptionTask() {
		//String zipkinUrl = ConfigUtils.getProperty("zipkin.url");
		RestTemplate restTemplate = new RestTemplate();
		String data = restTemplate.getForObject(zipkinUrl + BIZ_EXCEPTION_URL, String.class);
		JSONArray jsonErrors = JSONArray.parseArray(data);
		for(Object jsonError : jsonErrors) {
			String error = "";
			String txCode = "";
			if(jsonError instanceof JSONArray) {
				for(Object span : (JSONArray)jsonError) {
					if(span instanceof JSONObject) {
						String traceId = ((JSONObject)span).getString("traceId");
						Long timestamp = ((JSONObject)span).getLong("timestamp");
						BizWarningPo bizWarningPo = new BizWarningPo();
						bizWarningPo.setTraceId(traceId);
						CurrentPage currentPage = new CurrentPage();
						currentPage.setCurrentPage(1);
						currentPage.setPageSize(10);
						List<BizWarningPo> result = bizWarningStorage.selectBizWarningByPage(bizWarningPo, currentPage);
						if(result == null || result.size() == 0) {
							bizWarningPo.setTraceContent(jsonError.toString());
							bizWarningPo.setTraceDt(new SimpleDateFormat(ConstantsUtil.DATE_FORMAT).format(new Date(timestamp/1000)));
							bizWarningStorage.addBizWarning(bizWarningPo);
							error = ((JSONObject) span).getJSONObject("tags").getString("error");
							txCode = ((JSONObject) span).getJSONObject("tags").getString("txCode");
							logger.debug("新的业务异常，traceId: "+traceId+",error: "+error);
							sendWarningMailAsyc("time="+bizWarningPo.getTraceDt()+","+error, txCode);
							warningStatusHolder.setBizStatus(true);
						}
						break;
					}
				}
			}
		}

	}
	
	private void sendWarningMailAsyc(String error, String txCode) {
		ApplicationEmail email = new ApplicationEmail();
		NotificationPo po = new NotificationPo();
		po.setType("01");//邮件
		List<NotificationPo> notificationPoList = notificationStorage.selectNotificationByConditions(po);
		email.setSubject("业务异常_"+txCode);
		String addresses = "";
		for(NotificationPo notificationPo : notificationPoList) {
			addresses += notificationPo.getAddress() + ",";
		}
		email.setAddressee(addresses);
		email.setContent(error);
		mailer.sendMailByAsynchronousMode(email);
	}

}
