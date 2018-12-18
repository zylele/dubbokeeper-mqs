package com.dubboclub.dk.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dubboclub.dk.admin.sync.RegistryServerSync;
import com.dubboclub.dk.storage.BizWarningStorage;
import com.dubboclub.dk.storage.model.BizWarningPo;
import com.dubboclub.dk.storage.model.CurrentPage;
import com.dubboclub.dk.web.utils.ConstantsUtil;

@Component
public class BizExceptionTaskImpl implements BizExceptionTask {
    private static final Logger logger = LoggerFactory.getLogger(BizExceptionTaskImpl.class);
    @Autowired
    @Qualifier("bizWarningStorage")
    private BizWarningStorage bizWarningStorage;
    private final static String BIZ_EXCEPTION_URL="/zipkin/api/v2/traces?annotationQuery=error&limit=100&lookback=6000000";
	@Scheduled(cron="0/10 * *  * * ? ")   //每10秒执行一次    
    @Override  
	public void getBizExceptionTask() {
		String zipkinUrl = ConfigUtils.getProperty("zipkin.url");     
		RestTemplate restTemplate = new RestTemplate();
		String data = restTemplate.getForObject(zipkinUrl + BIZ_EXCEPTION_URL, String.class);
		JSONArray jsonErrors = JSONArray.parseArray(data);
		for(Object jsonError : jsonErrors) {
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
							logger.debug("新的业务异常，traceId="+traceId);
						}
						break;
					}
				}
			}
		}
	}

}
