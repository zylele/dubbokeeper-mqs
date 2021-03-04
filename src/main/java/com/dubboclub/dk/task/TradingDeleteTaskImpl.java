package com.dubboclub.dk.task;
 
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.dubboclub.dk.storage.DayTradingStorage;
import com.dubboclub.dk.storage.TradingStatisticStorage;
import com.dubboclub.dk.storage.model.DayTradingPo;
import com.dubboclub.dk.storage.model.TradingStatisticPo;
import com.dubboclub.dk.web.utils.ConstantsUtil;

/**  
* @ClassName: TradingDeleteTaskImpl  
* @Description:定是删除统计数据任务，删除天数可以在pom.xml中配置   
* @author zhangpengfei  
* @date 2019年3月11日   
*/
@Component
public class TradingDeleteTaskImpl implements TradingDeleteTask {
	
	@Autowired
	@Qualifier("tradingStatisticStorage")
	private TradingStatisticStorage tradingStatisticStorage;
	@Autowired
	@Qualifier("dayTradingStorage")
	private DayTradingStorage dayTradingStorage;
	private String reservedDayTop10;
	private String reservedDay;
	@PostConstruct
    public void init() {
		reservedDay = ConfigUtils.getProperty("reservedDay.url");
		reservedDayTop10 = ConfigUtils.getProperty("reservedDayTop10.url");
    }
	/**
	 * 根据POM中传来的值定期删除每日峰值数据和每月统计数据
	 */
	@Scheduled(cron="0 0 0/1 * * ? ")   //每小时执行一次  
	@Override 
    public void deleteTradingDayTask(){
		
		DayTradingPo dayTradingPo = new DayTradingPo();
		int reservedDays = Integer.parseInt(reservedDay);
		long oldDayTimestamp = ((new Date().getTime())/1000) - (reservedDays * 86400);
		dayTradingPo.setTimestamp(oldDayTimestamp);
		dayTradingStorage.deleteDayTradingByPageByCondition(dayTradingPo);
		TradingStatisticPo tradingStatisticPo = new TradingStatisticPo();
		int reservedDayTop10s = Integer.parseInt(reservedDayTop10);
		long oldMonthTimesTamp = ((new Date().getTime()/1000) - (reservedDayTop10s * 86400));
		String oldMonthDate = new SimpleDateFormat(ConstantsUtil.DATE_FORMATE).format(new Date(oldMonthTimesTamp*1000));
		tradingStatisticPo.setNowTime(oldMonthDate);
		tradingStatisticStorage.deleteTradingStatisticByPageByCondition(tradingStatisticPo);
		
	}
}
