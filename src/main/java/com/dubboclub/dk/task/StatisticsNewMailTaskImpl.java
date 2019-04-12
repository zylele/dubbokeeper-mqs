package com.dubboclub.dk.task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.dubboclub.dk.common.ExcelUtil;
import com.dubboclub.dk.common.SendMessage;
import com.dubboclub.dk.remote.MsgSystemService;
import com.dubboclub.dk.storage.NotificationStorage;
import com.dubboclub.dk.storage.TradingStatisticStorage;
import com.dubboclub.dk.storage.model.ChnlDefPo;
import com.dubboclub.dk.storage.model.NotificationPo;
import com.dubboclub.dk.storage.model.TradingStatisticPo;
import com.dubboclub.dk.storage.model.TradingStatisticQuery;
import com.dubboclub.dk.web.utils.ConstantsUtil;

/** 
 * @ClassName: StatisticsNewMailTaskImpl 
 * @Description: 定时发送交易统计信息
 * @author jinxiaolei 
 * @date 2019年4月11日 
 */
@Component
public class StatisticsNewMailTaskImpl implements StatisticsMailTask {
	
	private static final Logger logger = LoggerFactory.getLogger(StatisticsNewMailTaskImpl.class);
    @Autowired
    private SendMessage sendMessage;
    
    @Autowired
	MsgSystemService msgSystemService;
    
    @Autowired
    @Qualifier("tradingStatisticStorage")
    private TradingStatisticStorage tradingStatisticStorage;
    
    @Autowired
    @Qualifier("notificationStorage")
    private NotificationStorage notificationStorage;
    
    String excelPath = ConfigUtils.getProperty("excel.Path");
	
    /**
             *具体实现方法
     */
    @Scheduled(cron="0/30 * *  * * ? ")   //每天凌晨1点执行一次 "0 0 1 1/1 * ?"
	@Override
	public void getStatisticsMailTask() {
    	// 获取指定时间
//    	String nowTime = new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(System.currentTimeMillis()-1000*60*60*24));
    	String nowTime = new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(System.currentTimeMillis()-1000*60*60*24*7));
//    	String rebackSevenTime = new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(System.currentTimeMillis()-1000*60*60*24*7));
    	String rebackSevenTime = new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(System.currentTimeMillis()-1000*60*60*24*9));
    	
    	// 封装Excel表头
    	String [] formHead = {"交易码","交易名称","交易类别","交易笔数","成功笔数","失败笔数","失败率(%)","平均耗时(ms)"};
    	Map<String, Object> index = new LinkedHashMap<>();
    	for (String head : formHead) {
    		index.put(head, head);
		}
    	
		// 获取需要发送邮件的维护人权及渠道信息(Type：1为邮件)
		List<NotificationPo> dataMails = new ArrayList<NotificationPo>();
    	NotificationPo  notificationPo = new NotificationPo();
    	notificationPo.setType("01");
    	dataMails = notificationStorage.selectNotificationByConditions(notificationPo);
    	
    	logger.info("开始统计当日交易信息并添加到excel中");
    	for (NotificationPo dataMail : dataMails) {
    		// 全渠道邮箱信息仅接受系统异常信息故跳出循环
    		if (dataMail.getChnlCode().equals("IBS")) {
    			return ;
    		}
    		
    		// Excel工具类实体
    		ExcelUtil excel = new ExcelUtil();
    		excel.setOverWrite(false);
        	List <Map<String, Object>> listAll = new ArrayList<Map<String, Object>>();
    		// 获取渠道相信信息
    		ChnlDefPo chnlDef = notificationStorage.getChnlDefByChnlcode(dataMail.getChnlCode());
    		String xlsPath = excelPath+nowTime+"-"+chnlDef.getChnlName()+"交易信息统计.xls";
    		
        	TradingStatisticQuery tradingStatisticQuery = new TradingStatisticQuery();
        	tradingStatisticQuery.setTradingStartDate(rebackSevenTime);
        	tradingStatisticQuery.setTradingEndDate(nowTime);
        	tradingStatisticQuery.setChnlCode(dataMail.getChnlCode());
        	
        	// 日交易信息
        	List<TradingStatisticPo> oneDay = tradingStatisticStorage.selectTradingStatisticBydate(tradingStatisticQuery);
        	if(oneDay!=null&&oneDay.size()>0) {
        		listAll.add(index);
        		for (TradingStatisticPo obj : oneDay) {
            		Map<String, Object> line = new LinkedHashMap<>();;
            		line.put("交易码", obj.getTxCode());
            		line.put("交易名称", obj.getTxName());
            		line.put("交易类别", obj.getTxType().equals("1")?"金融类交易":"非金融类交易");
            		line.put("交易笔数", obj.getTotalNum());
            		line.put("成功笔数", obj.getSuccess());
            		line.put("失败笔数", obj.getFail());
            		line.put("失败率(%)", obj.getFailRate());
            		line.put("平均耗时(ms)", obj.getTimeAvg());
            		listAll.add(line);	
    			}
            	try {
            		excel.setSheetName("日交易信息统计");
    				excel.writeExcel(listAll, xlsPath);
    			} catch (IOException e) {
    				logger.info("日交易信息统计出错:"+e.toString());
    			}
            	listAll.clear();
        	}
        	
        	// 周交易信息
        	tradingStatisticQuery.setTradingStartDate(rebackSevenTime);
        	List<TradingStatisticPo> sevenDay = tradingStatisticStorage.selectTradingStatisticBydate(tradingStatisticQuery);
        	if(sevenDay!=null&&sevenDay.size()>0) {
        		listAll.add(index);
            	for (TradingStatisticPo obj : sevenDay) {
            		Map<String, Object> line = new LinkedHashMap<>();;
            		line.put("交易码", obj.getTxCode());
            		line.put("交易名称", obj.getTxName());
            		line.put("交易类别", obj.getTxType().equals("1")?"金融类交易":"非金融类交易");
            		line.put("交易笔数", obj.getTotalNum());
            		line.put("成功笔数", obj.getSuccess());
            		line.put("失败笔数", obj.getFail());
            		line.put("失败率(%)", obj.getFailRate());
            		line.put("平均耗时(ms)", obj.getTimeAvg());
            		listAll.add(line);	
    			}
            	try {
            		excel.setSheetName("周交易信息统计");
    				excel.writeExcel(listAll, xlsPath);
    			} catch (IOException e) {
    				logger.info("周交易信息统计出错:"+e.toString());
    			}
            	listAll.clear();
        	}

        	
		}
		
	}

}
