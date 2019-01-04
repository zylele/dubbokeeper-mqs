package com.dubboclub.dk.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
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
import com.dubboclub.dk.storage.TradingStatisticStorage;
import com.dubboclub.dk.storage.model.StatisticObject;
import com.dubboclub.dk.storage.model.TradingStatisticPo;
import com.dubboclub.dk.web.utils.ConstantsUtil;

@Component
public class TradingStatisticTaskImpl implements TradingStatisticTask {
	private static final Logger logger = LoggerFactory.getLogger(TradingStatisticTaskImpl.class);
	@Autowired
	@Qualifier("tradingStatisticStorage")
	private TradingStatisticStorage tradingStatisticStorage;
	private final static String BIZ_EXCEPTION_URL="/zipkin/api/v2/traces?lookback=10000";
	long lastEndTime = 0;
	private String zipkinUrl;
	@PostConstruct
    public void init() {
    	zipkinUrl = ConfigUtils.getProperty("zipkin.url");
    }
	@Scheduled(cron="0/10 * *  * * ? ")   //每10秒执行一次    
	@Override 
    public void getTradingStatisticTask(){
		Long startT;
		Long endT;
		if(lastEndTime == 0){
			lastEndTime = new Date().getTime() -10000;
			startT = lastEndTime - 10000;
			endT = lastEndTime;
		} else {
			startT = lastEndTime + 1;
			endT = lastEndTime + 10000;
			lastEndTime = endT;
		}
		RestTemplate restTemplate = new RestTemplate();         
		String data=null;
		try {
			data = restTemplate.getForObject(zipkinUrl + BIZ_EXCEPTION_URL + "&startTs=" + startT + "&endTs=" + endT  , String.class);
		} catch (Exception e) {
			logger.warn(zipkinUrl + BIZ_EXCEPTION_URL + "&startTs=" + startT + "&endTs=" + endT);
			return;
		}	
		JSONArray jsonTrads = JSONArray.parseArray(data);
		Map<String, StatisticObject> statisticMap = new HashMap<String, StatisticObject>();
		
		for(Object jsonTrad : jsonTrads ){
			String txCode = "";
			long duration = 0;
			boolean success = false;
			String nowTime = "";
			if(jsonTrad instanceof JSONArray){
					for(Object text : (JSONArray)jsonTrad){
						txCode = ((JSONObject) text).getJSONObject("tags").getString("txCode");
						String kind = ((JSONObject)text).getString("kind");
						String error = ((JSONObject) text).getJSONObject("tags").getString("error");
						if(txCode != null || txCode !="" && text instanceof JSONObject ){
							Long timestamp = ((JSONObject)text).getLong("timestamp");
							nowTime = new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(timestamp/1000));
							if(kind.equalsIgnoreCase("SERVER") && error == null){
								success = true;
								
							}else if(kind.equalsIgnoreCase("CLIENT") ){
								duration = ((JSONObject)text).getLong("duration");
	
							};
							
						};
					};
					
				}
			StatisticObject	object = statisticMap.get(txCode);
			if(object == null){
				object = new StatisticObject();
				object.setTotalNum(1);
				object.setTotalTimePerTime(duration);
				object.setNowTime(nowTime);
				if(success)
					object.setSuccess(1);
				else
					object.setFail(1);
				statisticMap.put(txCode, object);
				}					
			else {
				object.setTotalNum(object.getTotalNum()+1); 
				object.setTotalTimePerTime(object.getTotalTimePerTime()+duration);
				if(success)
					object.setSuccess(object.getSuccess()+1);
				else
					object.setFail(object.getFail()+1);
				statisticMap.put(txCode, object);
				
			}
			
			}
		
		for(String key : statisticMap.keySet())
        {
			StatisticObject value = statisticMap.get(key);
			TradingStatisticPo tradingStatisticPo = new TradingStatisticPo();
			tradingStatisticPo.setTxCode(key);
			tradingStatisticPo.setNowTime(value.getNowTime());
			TradingStatisticPo dataPo = tradingStatisticStorage.selectTradingStatisticByTxCode(tradingStatisticPo);

			if(dataPo == null){
				BeanUtils.copyProperties(value, tradingStatisticPo);
				tradingStatisticPo.setTimeAvg(value.getTotalTimePerTime()/value.getTotalNum());
				tradingStatisticPo.setTxCode(key);
				tradingStatisticStorage.addTradingStatistic(tradingStatisticPo);
			}else{
				tradingStatisticPo.setTotalNum(dataPo.getTotalNum()+value.getTotalNum());
				tradingStatisticPo.setFail(dataPo.getFail()+value.getFail());
				tradingStatisticPo.setSuccess(dataPo.getSuccess()+value.getSuccess());				
				tradingStatisticPo.setTimeAvg(TimeAvg(key,value.getTotalNum(),value.getTotalTimePerTime(),dataPo.getTotalNum(),dataPo.getTimeAvg()));
				tradingStatisticStorage.updateTradingStatisticByTxCode(tradingStatisticPo);
				
			}
        }
	

	}
	
	private double TimeAvg(String txCode,int totalNum,long totalTimePerTime,int oldTotal,double oleTimeAvg){
		double totalNum1 = oldTotal+totalNum;
		double duration1 = oleTimeAvg*oldTotal+totalTimePerTime;
		double avg = (duration1/totalNum1);
		return avg;
	}
}

