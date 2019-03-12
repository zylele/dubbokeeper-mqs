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
import com.dubboclub.dk.remote.MsgSystemService;
import com.dubboclub.dk.remote.esb.dto.SendEmailReq;
import com.dubboclub.dk.remote.esb.dto.SingleEmailReq;
import com.dubboclub.dk.storage.NotificationStorage;
import com.dubboclub.dk.storage.TradingStatisticStorage;
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
    @Scheduled(cron=" 0 0 1 * * ?")   //每天凌晨1点执行一次     0 0 1 * * ?
    @Override 
	public void getStatisticsMailTask(){
    	RestTemplate restTemplate = new RestTemplate();  
    	TradingStatisticQuery tradingStatisticQuery = new TradingStatisticQuery();
    	tradingStatisticQuery.setTradingStartDate(new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(System.currentTimeMillis()-1000*60*60*24)));
    	tradingStatisticQuery.setTradingEndDate(new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(System.currentTimeMillis()-1000*60*60*24)));
    	List<TradingStatisticPo> data = new ArrayList<TradingStatisticPo>();
    	String msgx = "";
    	StringBuilder msgTxCode = new StringBuilder();
    	StringBuilder msgTimeAvg = new StringBuilder();
    	StringBuilder msgSuccess = new StringBuilder();
    	StringBuilder msgFail = new StringBuilder();
//    	向接口传type值，根据type值去排序查询数据
    	String[] x = {"01","02","03","04"};
    	for(String i : x){
    		tradingStatisticQuery.setType(i);
	    	CurrentPage currentPage = new CurrentPage();
			currentPage.setCurrentPage(1);
			currentPage.setPageSize(10);
			data = tradingStatisticStorage.selectTradingStatisticByPageByCondition(tradingStatisticQuery,currentPage);
			
//			遍历返回的数据
			for(TradingStatisticPo po : data){
				if(i == "01"){
					msgTxCode.append(msgx = "交易码:" + po.getTxCode() + "\t\t" + "交易名称:" + po.getTxName() + "\t\t" + "交易量:" + po.getTotalNum() + "" + "\n");
				}else if(i == "02"){
					msgTimeAvg.append(msgx = "交易码:" + po.getTxCode() + "\t\t" + "交易名称:" +  po.getTxName() + "\t\t" + "平均耗时(ms):" + po.getTimeAvg() + "" + "\n");
				}else if(i == "03"){
					msgSuccess.append(msgx = "交易码:" + po.getTxCode() + "\t\t" + "交易名称:" +  po.getTxName() + "\t\t" + "成功次数:" + po.getSuccess() + "" + "\n");
				}else if(i == "04"){
					msgFail.append(msgx = "交易码:" + po.getTxCode() + "\t\t" + "交易名称:" +  po.getTxName() + "\t\t" + "失败次数:" + po.getFail() + "" + "\n");
				}
				
			}
    	}
//    	最终的邮件内容字符串
    	String msg = "昨日交易情况统计" + "\n" + DayAllStatistics() + "昨日交易量TOP10:" + "\n" + msgTxCode.toString() + "\n" + "昨日平均耗时TOP10:" + "\n" +  msgTimeAvg.toString() + "\n" + "昨日交易成功率TOP10:" + "\n" +  msgSuccess.toString() + "\n" + "昨日交易失败率TOP10:" + "\n" + msgFail.toString()
    	 				+ "\n" + DayStatistics() + "\n" + WeekStatistic() +  weekStatistics();
    	logger.debug("统计类邮件内容-->" + msg);
    	System.out.println("统计类邮件内容-->" + msg);
    	SendEmailReq sendEmailReq = new SendEmailReq();
		sendEmailReq.setSceneCode("M001");
		sendEmailReq.setBusType("OutOpenAcc");
		sendEmailReq.setSubject(ConstantsUtil.MAIL_SUBJECT);
		sendEmailReq.setMailTo(queryAddress());
		sendEmailReq.setAttachments(null);
		sendEmailReq.setMsg("昨天数据统计");
    	SingleEmailReq singleEmailReq = new SingleEmailReq();
    	singleEmailReq.setContentData(msg);
		if(sendMailStatistic.equals("true"))
			msgSystemService.SendSingleEmail(singleEmailReq);
	}
//    获取邮箱地址
    private List<String> queryAddress(){
		NotificationPo notificationPo = new NotificationPo();
		notificationPo.setType("01");
		List<NotificationPo> notificationPos = notificationStorage.selectNotificationByConditions(notificationPo);
		List<String> mails = new ArrayList<String>();
		for (NotificationPo notificationPo2 : notificationPos) {
			mails.add(notificationPo2.getAddress());
		}
		return mails;
	};
//	部分邮件内容获取
	private String DayAllStatistics(){
		TradingStatisticQueryTime tradingStatisticQueryTime = new TradingStatisticQueryTime();
		tradingStatisticQueryTime.setTradingStartDate(new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(System.currentTimeMillis()-1000*60*60*24)));
		tradingStatisticQueryTime.setTradingEndDate(new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(System.currentTimeMillis()-1000*60*60*24)));
    	CurrentPage currentPage = new CurrentPage();
		currentPage.setCurrentPage(1);
		currentPage.setPageSize(1);
		StringBuilder msg = new StringBuilder();
		String msgx = "";
		List<TradingStatisticPo> data = new ArrayList<TradingStatisticPo>();
    	data = tradingStatisticStorage.selectTradingStatisticByPageByDayFailRate(tradingStatisticQueryTime,currentPage);
    	try {
			for(TradingStatisticPo po : data){
				msg.append(msgx = "交易总笔数:" + po.getTotalNum() + "\t\t" + "成功总笔数:" + po.getSuccess() + "\t\t" + "失败总笔数:" + po.getFail() + "" +  "\t\t" + "失败率:" + po.getFailRate() + "\n");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 System.out.println("统计数据库中数据为空: " + e);
		}
    	List<TradingStatisticPo> data1 = new ArrayList<TradingStatisticPo>();
    	data1 = tradingStatisticStorage.selectTradingStatisticByPageByTxType(tradingStatisticQueryTime,currentPage);
    	try {
			for(TradingStatisticPo po : data1){
				msg.append(msgx = "金融类交易笔数:" + po.getTotalNum() + "\t\t" + "金融类交易失败笔数:" + po.getFail() + "\n");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("统计数据库中数据为空: " + e);
		}
    	
		return msg.toString();
	}
//	部分邮件内容获取
	private String DayStatistics(){
		TradingStatisticQueryTime tradingStatisticQueryTime = new TradingStatisticQueryTime();
		tradingStatisticQueryTime.setTradingStartDate(new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(System.currentTimeMillis()-1000*60*60*24)));
		tradingStatisticQueryTime.setTradingEndDate(new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(System.currentTimeMillis()-1000*60*60*24)));
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
				msg.append(msgx =  "交易码:" + po.getTxCode() + "\t\t" + "交易名称:" + po.getTxName() + "\t\t" + "交易量:" + po.getTotalNum() + "" + "\n");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("统计数据库中数据为空: " + e);
		}
    	List<TradingStatisticPo> data1 = new ArrayList<TradingStatisticPo>();
    	data1 = tradingStatisticStorage.selectTradingStatisticByPageByFail(tradingStatisticQueryTime,currentPage);
    	try {
			for(TradingStatisticPo po : data1){
				msg1.append(msgx = "交易码:" + po.getTxCode() + "\t\t" + "交易名称:" + po.getTxName() + "\t\t" + "交易量:" + po.getTotalNum() + "" + "\n");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("统计数据库中数据为空: " + e);
		}
    	
		return "昨日金融类TOP10:" + "\n" + msg.toString()+ "昨日交易失败率TOP10:" + "\n" + msg1.toString();
	}
//	部分邮件内容获取
	private String WeekStatistic(){
		TradingStatisticQuery tradingStatisticQuery = new TradingStatisticQuery();
		tradingStatisticQuery.setTradingStartDate(new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(System.currentTimeMillis()-1000*60*60*24*7)));
		tradingStatisticQuery.setTradingEndDate(new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(System.currentTimeMillis())));
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
				msgWeek.append(msgx ="交易码:" + po.getTxCode() + "\t\t" + "交易名称:" + po.getTxName() + "\t\t" + "交易量:" + po.getTotalNum() + "" + "\n");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("统计数据库中数据为空: " + e);
		}
		return "近七天交易量TOP10:" + "\n" + msgWeek.toString();

	}
//	部分邮件内容获取
	private String weekStatistics(){
		TradingStatisticQueryTime tradingStatisticQueryTime = new TradingStatisticQueryTime();
		tradingStatisticQueryTime.setTradingStartDate(new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(System.currentTimeMillis()-1000*60*60*24*7)));
		tradingStatisticQueryTime.setTradingEndDate(new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(System.currentTimeMillis())));
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
				msgWeek.append(msgx = "交易码:" + po.getTxCode() + "\t\t" + "交易名称:" + po.getTxName() + "\t\t" + "交易量:" + po.getTotalNum() + "" + "\n");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("统计数据库中数据为空: " + e);
		}
		weekData2 = tradingStatisticStorage.selectTradingStatisticByPageByFail(tradingStatisticQueryTime,currentPage);
		try {
			for(TradingStatisticPo po : weekData2){
				msgWeek1.append(msgx = "交易码:" + po.getTxCode() + "\t\t" + "交易名称:" + po.getTxName() + "\t\t" + "交易量:" + po.getTotalNum() + "" + "\n");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("统计数据库中数据为空: " + e);
		}
		return "近七天金融TOP10:" + "\n" + msgWeek.toString() + "近七天交易失败率TOP10:" + "\n" + msgWeek1.toString();

	}

	
	
}
