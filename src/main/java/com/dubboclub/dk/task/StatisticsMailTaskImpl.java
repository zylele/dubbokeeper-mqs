package com.dubboclub.dk.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.dubboclub.dk.common.SendMessage;
import com.dubboclub.dk.remote.MsgSystemService;
import com.dubboclub.dk.remote.esb.dto.SendEmailReq;
import com.dubboclub.dk.storage.NotificationStorage;
import com.dubboclub.dk.storage.TradingStatisticStorage;
import com.dubboclub.dk.storage.model.ChnlDefPo;
import com.dubboclub.dk.storage.model.CurrentPage;
import com.dubboclub.dk.storage.model.NotificationPo;
import com.dubboclub.dk.storage.model.TradingStatisticPo;
import com.dubboclub.dk.storage.model.TradingStatisticQuery;
import com.dubboclub.dk.storage.model.TradingStatisticQueryTime;
import com.dubboclub.dk.web.utils.ConstantsUtil;


/**  
* Title: StatisticsMailTaskImpl  
* Description:定时发送邮（昨日数据） 
* @author zhangpengfei  
* @date 2019年2月19日  
*/  
@Component
public class StatisticsMailTaskImpl implements StatisticsMailTask {
	private static final Logger logger = LoggerFactory.getLogger(StatisticsMailTaskImpl.class);
    @Autowired
    private SendMessage sendMessage;
    
    @Autowired
    @Qualifier("tradingStatisticStorage")
    private TradingStatisticStorage tradingStatisticStorage;
    
    @Autowired
    @Qualifier("notificationStorage")
    private NotificationStorage notificationStorage;
    
    @Autowired
	MsgSystemService msgSystemService;
	private String sendMailStatistic;
    
    @PostConstruct
    public void init() {
    	sendMailStatistic = ConfigUtils.getProperty("sendMailStatistic.url");
    }
//  统计信息的邮件功能
    @Scheduled(cron="0/30 * *  * * ? ")   //每天凌晨1点执行一次     0 0 1 * * ?
    @Override 
	public void getStatisticsMailTask(){
    	List<NotificationPo> dataMails = new ArrayList<NotificationPo>();
    	NotificationPo  notificationPo = new NotificationPo();
    	notificationPo.setType("01");
    	dataMails = notificationStorage.selectNotificationByConditions(notificationPo);
    	for(NotificationPo dataMail : dataMails){
    		// 全渠道邮箱信息仅接受系统异常信息故跳出循环
    		if (dataMail.getChnlCode().equals("IBS")) {
    			return ;
    		}
    		// 获取渠道相信信息
    		ChnlDefPo chnlDef = notificationStorage.getChnlDefByChnlcode(dataMail.getChnlCode());
    		
    		RestTemplate restTemplate = new RestTemplate();  
        	TradingStatisticQuery tradingStatisticQuery = new TradingStatisticQuery();
        	tradingStatisticQuery.setTradingStartDate(new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(System.currentTimeMillis()-1000*60*60*24)));
        	tradingStatisticQuery.setTradingEndDate(new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(System.currentTimeMillis()-1000*60*60*24)));
        	tradingStatisticQuery.setChnlCode(dataMail.getChnlCode());
        	List<TradingStatisticPo> data = new ArrayList<TradingStatisticPo>();
        	String msgx = "";
        	StringBuilder msgTxCode = new StringBuilder();
        	StringBuilder msgTimeAvg = new StringBuilder();
        	StringBuilder msgSuccess = new StringBuilder();
        	StringBuilder msgFail = new StringBuilder();
        	
//        	向接口传type值，根据type值去排序查询数据
        	String[] x = {"01","02","03","04"};
        	for(String i : x){
        		tradingStatisticQuery.setType(i);
    	    	CurrentPage currentPage = new CurrentPage();
    			currentPage.setCurrentPage(1);
    			currentPage.setPageSize(10);
    			data = tradingStatisticStorage.selectTradingStatisticByPageByCondition(tradingStatisticQuery,currentPage);
    			
//    			遍历返回的数据
    			for(TradingStatisticPo po : data){
    				if(i == "01"){
    					msgTxCode.append(msgx = "<tr><td>" + "交易码:" + po.getTxCode() + "    " + "——" + po.getTxName() + "    " +"</td><td>"+ "交易量:" + po.getTotalNum()  +"</td></tr>");				}else if(i == "02"){
    					msgTimeAvg.append(msgx = "<tr><td>" + "交易码:" + po.getTxCode() + "    " + "——" +  po.getTxName() + "    " + "</td><td>"+ "平均耗时(ms):" + po.getTimeAvg() +"</td></tr>");				}else if(i == "03"){
    					msgSuccess.append(msgx = "<tr><td>" + "交易码:" + po.getTxCode() + "    " + "——" +  po.getTxName() + "    " + "</td><td>"+ "成功次数:" + po.getSuccess() +"</td></tr>");				}else if(i == "04"){
    					msgFail.append(msgx = "<tr><td>" + "交易码:" + po.getTxCode() + "    " + "——" +  po.getTxName() + "    " + "</td><td>"+ "失败次数:" + po.getFail() +"</td></tr>");				}
    				
    			}
        	}
//        	最终的邮件内容字符串
//        	String msg = "昨日交易情况统计:"  + DayAllStatistics(dataMail.getChnlCode()) + "<br>"
//        	String chnlType = "<div class='text' style='text-align:center';>"+chnlDef.getChnlName()+"("+chnlDef.getChnlCode()+")业务信息统计"+"</div><br><br>" ;
        	String nbsp = "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"+chnlDef.getChnlName()+"("+chnlDef.getChnlCode()+")业务信息统计<br><br>";
        	String msg = 	nbsp
        					+"昨日交易情况统计:"+ DayAllStatistics(dataMail.getChnlCode()) + "<br>"
        					+"<caption>"+ "昨日交易量TOP10:" + "</caption><table>" + msgTxCode.toString() + "</table>"
        					+ "<caption>"+ "昨日平均耗时TOP10:" + "</caption><table>" +  msgTimeAvg.toString() + "</table>" 
        					+ "<caption>"+ "昨日交易成功率TOP10:" + "</caption><table>" +  msgSuccess.toString() + "</table>"
        					+ "<caption>"+ "昨日交易失败率TOP10:" + "</caption><table>" + msgFail.toString() + "</table>"
        					+ DayStatistics(dataMail.getChnlCode()) + ""
        					+ WeekStatistic(dataMail.getChnlCode())  + ""
        					+  weekStatistics(dataMail.getChnlCode());
        	
        	SendEmailReq sendEmailReq = new SendEmailReq();
    		sendEmailReq.setSceneCode("M001");
    		sendEmailReq.setBusType("OutOpenAcc");
    		sendEmailReq.setSubject(ConstantsUtil.MAIL_SUBJECT);
    		sendEmailReq.setMailTo(sendMessage.queryAddressByChnlCode(tradingStatisticQuery.getChnlCode()));
    		sendEmailReq.setAttachments(null);
    		sendEmailReq.setMsg(msg);
    		if(sendMailStatistic.equals("true")){
    			logger.info("统计类邮件内容-->" + "渠道号"+ dataMail.getChnlCode() + msg);
    			sendMessage.sendWarningMailAsyc(sendEmailReq, dataMail.getChnlCode()+"昨日数据统计");
    		}
    		
    	}		
	}

//	部分邮件内容获取
	private String DayAllStatistics(String chnlCode){
		TradingStatisticQueryTime tradingStatisticQueryTime = new TradingStatisticQueryTime();
		tradingStatisticQueryTime.setTradingStartDate(new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(System.currentTimeMillis()-1000*60*60*24)));
		tradingStatisticQueryTime.setTradingEndDate(new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(System.currentTimeMillis()-1000*60*60*24)));
		tradingStatisticQueryTime.setChnlCode(chnlCode);   	
		StringBuilder msg = new StringBuilder();
		String msgx = "";
		List<TradingStatisticPo> data = new ArrayList<TradingStatisticPo>();
    	data = tradingStatisticStorage.selectTradingStatisticByPageByDayFailRate(tradingStatisticQueryTime);
    	try {
			for(TradingStatisticPo po : data){
				if(null!=po) {
					msg.append(msgx = "交易总笔数:" + po.getTotalNum() + "    " + "成功总笔数:" + po.getSuccess() + "    " + "失败总笔数:" + po.getFail() + "" +  "    " + "失败率:" + po.getFailRate() + "<br>");			}
				}			
		} catch (Exception e) {
			logger.info("统计数据库中数据为空: " + e.getMessage());
		}
    	List<TradingStatisticPo> data1 = new ArrayList<TradingStatisticPo>();
    	data1 = tradingStatisticStorage.selectTradingStatisticByPageByTxType(tradingStatisticQueryTime);
    	try {
			for(TradingStatisticPo po : data1){
				if(null!=po) {
					msg.append(msgx = "金融类交易笔数:" + po.getTotalNum() + "    " + "金融类交易失败笔数:" + po.getFail() + "<br>");			}
				}				
		} catch (Exception e) {
			logger.info("统计数据库中数据为空: " + e.getMessage());
		}
    	
		return msg.toString();
	}
//	部分邮件内容获取
	private String DayStatistics(String chnlCode){
		TradingStatisticQueryTime tradingStatisticQueryTime = new TradingStatisticQueryTime();
		tradingStatisticQueryTime.setTradingStartDate(new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(System.currentTimeMillis()-1000*60*60*24)));
		tradingStatisticQueryTime.setTradingEndDate(new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(System.currentTimeMillis()-1000*60*60*24)));
		tradingStatisticQueryTime.setChnlCode(chnlCode);
    	CurrentPage currentPage = new CurrentPage();
		currentPage.setCurrentPage(1);
		currentPage.setPageSize(10);
		StringBuilder msg = new StringBuilder();
		StringBuilder msg1 = new StringBuilder();
		String msgx = "";
		List<TradingStatisticPo> data = new ArrayList<TradingStatisticPo>();
    	data = tradingStatisticStorage.selectTradingStatisticByType(tradingStatisticQueryTime,currentPage);
    	try {
			for(TradingStatisticPo po : data){
				if(null!=po) {
					msg1.append(msgx = "<tr><td>"+"交易码:" + po.getTxCode() + "    " + "——" + po.getTxName() + "</td><td>" + "交易量:" + po.getTotalNum() + "</td><tr>");			}
				}
		} catch (Exception e) {
			logger.info("统计数据库中数据为空: " + e.getMessage());
		}
    	List<TradingStatisticPo> data1 = new ArrayList<TradingStatisticPo>();
    	data1 = tradingStatisticStorage.selectTradingStatisticByPageByFail(tradingStatisticQueryTime,currentPage);
    	try {
			for(TradingStatisticPo po : data1){
				if(null!=po) {
					msg1.append(msgx = "<tr><td>"+"交易码:" + po.getTxCode() + "    " + "——" + po.getTxName() + "</td><td>" + "交易量:" + po.getTotalNum() + "</td><tr>");			}
				}
		} catch (Exception e) {
			logger.info("统计数据库中数据为空: " + e.getMessage());
		}
    	
		return "<caption>"+ "昨日金融类TOP10:" + "</caption><table>" + msg.toString() + "</table>"
				+ "<caption>" + "昨日交易失败率TOP10:" + "</caption><table>" + msg1.toString() + "</table>";	
	}
//	部分邮件内容获取
	private String WeekStatistic(String chnlCode){
		TradingStatisticQuery tradingStatisticQuery = new TradingStatisticQuery();
		tradingStatisticQuery.setTradingStartDate(new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(System.currentTimeMillis()-1000*60*60*24*7)));
		tradingStatisticQuery.setTradingEndDate(new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(System.currentTimeMillis())));
		tradingStatisticQuery.setChnlCode(chnlCode);
		List<TradingStatisticPo> weekData = new ArrayList<TradingStatisticPo>();
		String msgx = "";
		tradingStatisticQuery.setType("01");
		StringBuilder msgWeek = new StringBuilder();
		CurrentPage currentPage = new CurrentPage();
		currentPage.setCurrentPage(1);
		currentPage.setPageSize(10);
		weekData = tradingStatisticStorage.selectTradingStatisticByPageByCondition(tradingStatisticQuery,currentPage);
		try {
			for(TradingStatisticPo po : weekData){
				if(null!=po) {
					msgWeek.append(msgx ="<tr><td>"+"交易码:" + po.getTxCode() + "    " + "——" + po.getTxName() + "</td><td>" + "交易量:" + po.getTotalNum() + "</td><tr>");			}
				}
		} catch (Exception e) {
			logger.info("统计数据库中数据为空: " + e.getMessage());
		}

		return "<caption>"+ "近七天交易量TOP10:" + "</caption><table>" + msgWeek.toString()+ "</table>";
	}
//	部分邮件内容获取
	private String weekStatistics(String chnlCode){
		TradingStatisticQueryTime tradingStatisticQueryTime = new TradingStatisticQueryTime();
		tradingStatisticQueryTime.setTradingStartDate(new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(System.currentTimeMillis()-1000*60*60*24*7)));
		tradingStatisticQueryTime.setTradingEndDate(new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(System.currentTimeMillis())));
		tradingStatisticQueryTime.setChnlCode(chnlCode);
		List<TradingStatisticPo> weekData = new ArrayList<TradingStatisticPo>();
		List<TradingStatisticPo> weekData2 = new ArrayList<TradingStatisticPo>();
		String msgx = "";
		StringBuilder msgWeek = new StringBuilder();
		StringBuilder msgWeek1 = new StringBuilder();
		CurrentPage currentPage = new CurrentPage();
		currentPage.setCurrentPage(1);
		currentPage.setPageSize(10);
		weekData = tradingStatisticStorage.selectTradingStatisticByType(tradingStatisticQueryTime,currentPage);
		try {
			for(TradingStatisticPo po : weekData){
				if(null!=po) {
					msgWeek.append(msgx = "<tr><td>"+"交易码:" + po.getTxCode() + "    " + "——" + po.getTxName() + "</td><td>" + "交易量:" + po.getTotalNum() + "</td><tr>");			}
				}
		} catch (Exception e) {
			logger.info("统计数据库中数据为空: " + e.getMessage());
		}
		weekData2 = tradingStatisticStorage.selectTradingStatisticByPageByFail(tradingStatisticQueryTime,currentPage);
		try {
			for(TradingStatisticPo po : weekData2){
				if(null!=po) {
					msgWeek1.append(msgx = "<tr><td>"+"交易码:" + po.getTxCode() + "    " + "——" + po.getTxName() + "</td><td>" + "交易量:" + po.getTotalNum() + "</td><tr>");			}
				}
		} catch (Exception e) {
			logger.info("统计数据库中数据为空: " +  e.getMessage());
		}

		return  "<caption>"+ "近七天金融TOP10:" + "</caption><table>" + msgWeek.toString() + "</table>" 
				+"<caption>"+ "近七天交易失败率TOP10:" + "</caption><table>" + msgWeek1.toString() + "</table>" ;
	}
	
}
