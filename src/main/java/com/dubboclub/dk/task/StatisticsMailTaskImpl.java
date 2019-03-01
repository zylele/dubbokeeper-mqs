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
    @Scheduled(cron="0 0 1 * * ?")   //每天凌晨1点执行一次     
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
    	String[] x = {"01","02","03","04"};
    	for(String i : x){
    		tradingStatisticQuery.setType(i);
	    	CurrentPage currentPage = new CurrentPage();
			currentPage.setCurrentPage(1);
			currentPage.setPageSize(10);
			data = tradingStatisticStorage.selectTradingStatisticByPageByCondition(tradingStatisticQuery,currentPage);
			SendEmailReq sendEmailReq = new SendEmailReq();
			sendEmailReq.setSceneCode("");
			sendEmailReq.setBusType("OutOpenAcc");
			sendEmailReq.setSubject(ConstantsUtil.MAIL_SUBJECT);
			sendEmailReq.setMailTo(queryAddress());
			sendEmailReq.setAttachments(null);
			sendEmailReq.setMsg("昨天数据统计");
			
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
    	String msg = "昨日交易量TOP10:" + "\n" + msgTxCode.toString() + "\n" + "昨日平均耗时TOP10:" + "\n" +  msgTimeAvg.toString() + "\n" + "昨日交易成功率TOP10:" + "\n" +  msgSuccess.toString() + "\n" + "昨日交易失败率TOP10:" + "\n" + msgFail.toString();
    	SingleEmailReq singleEmailReq = new SingleEmailReq();
    	singleEmailReq.setContentData(msg);
		if(sendMailStatistic.equals("true"))
			msgSystemService.SendSingleEmail(singleEmailReq);
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
	};

}
