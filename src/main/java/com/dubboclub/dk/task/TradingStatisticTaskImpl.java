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
import com.dubboclub.dk.storage.DayTradingStorage;
import com.dubboclub.dk.storage.TradingStatisticStorage;
import com.dubboclub.dk.storage.model.DayTradingPo;
import com.dubboclub.dk.storage.model.StatisticObject;
import com.dubboclub.dk.storage.model.TradingStatisticPo;
import com.dubboclub.dk.web.utils.ConstantsUtil;

/**  
* @ClassName: TradingStatisticTaskImpl  
* @Description:定时从zipkin获取数据并统计   
* @author zhangpengfei  
* @date 2019年3月11日   
*/
@Component
public class TradingStatisticTaskImpl implements TradingStatisticTask {
	private static final Logger logger = LoggerFactory.getLogger(TradingStatisticTaskImpl.class);
	@Autowired
	@Qualifier("tradingStatisticStorage")
	private TradingStatisticStorage tradingStatisticStorage;
	@Autowired
	@Qualifier("dayTradingStorage")
	private DayTradingStorage dayTradingStorage;
	private final static String BIZ_EXCEPTION_URL="/zipkin/api/v2/traces?";
	long lastEndTime = 0;
	private String zipkinUrl;
	private String txCodeKey;
	@PostConstruct
    public void init() {
    	zipkinUrl = ConfigUtils.getProperty("zipkin.url");
    	txCodeKey = ConfigUtils.getProperty("txCode.Key");
    }
	@Scheduled(cron="0 0/5 * * * ?  ")   //每5分钟执行一次    
	@Override 
	//解决时间重叠问题 
    public void getTradingStatisticTask(){
		
		//统一定义时间
		long timestamp = System.currentTimeMillis()/1000;
		String nowTime = new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date());
		String startTime = new SimpleDateFormat(ConstantsUtil.DATE_FORMAT).format(new Date().getTime());
		
		Long startT;
		Long endT;
		if(lastEndTime == 0){
			lastEndTime = new Date().getTime() -10000;
			startT = lastEndTime - 10000;
			endT = lastEndTime ;
		} else {
			startT = lastEndTime + 1;
			endT = lastEndTime + 10000;
			lastEndTime = endT;
		}
		RestTemplate restTemplate = new RestTemplate();         
		String data=null;
		try {
			// 指定zipkin回溯时间 1h=3600000
			data = restTemplate.getForObject(zipkinUrl + BIZ_EXCEPTION_URL + "&endTs=" + endT + "&lookback=300000"  , String.class);
			
		} catch (Exception e) {
			logger.warn(zipkinUrl + BIZ_EXCEPTION_URL + "&startTs=" + startT + "&endTs=" + endT);
			return;
		}
		JSONArray jsonTrads = JSONArray.parseArray(data);
//		如果没有交易定时向每日峰值表中插入数据
		if(jsonTrads == null || jsonTrads.size() == 0){
			DayTradingPo dayTradingPo = new DayTradingPo();
			dayTradingPo.setTxCode("0000");
			dayTradingPo.setChnlCode("000000");
			dayTradingPo.setTotalTimeNum(0);
			dayTradingPo.setNowTime(nowTime);
			dayTradingPo.setStartTime(startTime);
			dayTradingPo.setTimestamp(timestamp);
			dayTradingStorage.addDayTrading(dayTradingPo);
		}
		Map<String, StatisticObject> statisticMap = new HashMap<String, StatisticObject>();  //交易量，平均耗时，成功或失败次数Map
		
		//遍历返回的数据并put到statisticMap中，以serviceName+txCode+sourceType+为Key
		for (Object jsonTrad : jsonTrads) {
		String txCode = "";
		long duration = 0;
		boolean success = false;
		String kind = "";
		String serviceName = "";
		String sourceType = "";
		if (jsonTrad instanceof JSONArray) {
			for (Object text : (JSONArray) jsonTrad) {
				serviceName = ((JSONObject) text).getJSONObject("localEndpoint").getString("serviceName");
				if (methodName(serviceName)) {
					txCode = ((JSONObject) text).getJSONObject("tags").getString("txCode");
					if (txCode != null && !txCode.equals(" ") && text instanceof JSONObject) {
						if(txCodeKey.indexOf(txCode)==-1){
						kind = ((JSONObject) text).getString("kind");
						String error = ((JSONObject) text).getJSONObject("tags").getString("error");
						sourceType = ((JSONObject) text).getJSONObject("tags").getString("chnlType");
						duration = ((JSONObject) text).getLong("duration");
						if (error==null ) 
							success = true;
					
						// 将交易数据做加工并放在map中
							try{
								String key = serviceName+txCode+sourceType;
								StatisticObject object = statisticMap.get(key);
								if (object == null) {
								object = new StatisticObject();
								object.setTotalNum(1);
								object.setTotalTimePerTime(duration/1000);
								object.setNowTime(nowTime);
								object.setTimestamp(timestamp);
								object.setStartTime(startTime);
								object.setTxCode(txCode);
								object.setServiceName(serviceName);
								object.setSourceType(sourceType==null?sourceType="0000":sourceType);
								object.setTotalDayTimePer(object.getTotalDayTimePer() + 1);
								if (success)
									object.setSuccess(1);
								else
									object.setFail(1);
								statisticMap.put(key, object);
								} else {
									object.setTotalNum(object.getTotalNum() + 1);
									object.setTotalTimePerTime(object.getTotalTimePerTime() + (duration/1000));
									if (success)
										object.setSuccess(object.getSuccess() + 1);
									else
										object.setFail(object.getFail() + 1);
									object.setTotalDayTimePer(object.getTotalDayTimePer() + 1);
									statisticMap.put(key, object);
							}
							}catch(Exception e){
								logger.info("处理zipkin数据出现异常"+e.getMessage());
								return;
							}
						}
					};
				}
			};
		};
	}	
		//此次回溯并处理后的数据map
		for(String key : statisticMap.keySet())
        {
			StatisticObject value = statisticMap.get(key);
			TradingStatisticPo tradingStatisticPo = new TradingStatisticPo();
			tradingStatisticPo.setTxCode(value.getTxCode());
			tradingStatisticPo.setNowTime(value.getNowTime());
			tradingStatisticPo.setServiceName(value.getServiceName());
			tradingStatisticPo.setSourceType(value.getSourceType());
			TradingStatisticPo dataPo = tradingStatisticStorage.selectTradingStatisticByTxCode(tradingStatisticPo);
			
			if(dataPo == null){
				BeanUtils.copyProperties(value, tradingStatisticPo);
				tradingStatisticPo.setTimeAvg(value.getTotalTimePerTime()/value.getTotalNum());
				tradingStatisticStorage.addTradingStatistic(tradingStatisticPo);
				
			}else{
				tradingStatisticPo.setTotalNum(dataPo.getTotalNum()+value.getTotalNum());
				tradingStatisticPo.setFail(dataPo.getFail()+value.getFail());
				tradingStatisticPo.setSuccess(dataPo.getSuccess()+value.getSuccess());	
				tradingStatisticPo.setTimeAvg(TimeAvg(value.getTotalNum(),value.getTotalTimePerTime(),dataPo.getTotalNum(),dataPo.getTimeAvg()));
				tradingStatisticStorage.updateTradingStatisticByTxCode(tradingStatisticPo);
			}
			
			// 更新staticday表
			DayTradingPo dayTradingPo = new DayTradingPo();
			dayTradingPo.setTotalTimeNum(value.getTotalNum());
			dayTradingPo.setSuccess(value.getSuccess());
			dayTradingPo.setFail(value.getFail());
			dayTradingPo.setStartTime(value.getStartTime());
			dayTradingPo.setNowTime(value.getNowTime());
			dayTradingPo.setTimestamp(value.getTimestamp());
			dayTradingPo.setTxCode(value.getTxCode());
			dayTradingPo.setChnlCode(value.getSourceType());
			dayTradingStorage.addDayTrading(dayTradingPo);
        }
		
	}
                                                                         
	// 求平均耗时
	private double TimeAvg(int totalNum,long totalTimePerTime,int oldTotal,double oleTimeAvg){
		double totalNum1 = oldTotal+totalNum;
		double duration1 = oleTimeAvg*oldTotal+totalTimePerTime;
		double avg = (duration1/totalNum1);
		return avg;
	}
	
	// 过滤多余数据
	private boolean methodName (String servicename){
		int client = servicename.indexOf("client");
		if(client != -1){
			return true;
		}
		return false;	
	}
	
}

