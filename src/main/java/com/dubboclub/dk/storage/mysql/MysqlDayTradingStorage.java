package com.dubboclub.dk.storage.mysql;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.dubboclub.dk.storage.DayTradingStorage;
import com.dubboclub.dk.storage.model.DayTradingPo;
import com.dubboclub.dk.storage.model.DayTradingQuery;
import com.dubboclub.dk.storage.mysql.mapper.DayTradingMapper;

public class MysqlDayTradingStorage implements DayTradingStorage,InitializingBean {
	
	private DayTradingMapper dayTradingMapper;

	public DayTradingMapper getDayTradingMapper() {
		return dayTradingMapper;
	}

	public void setDayTradingMapper(DayTradingMapper dayTradingMapper) {
		this.dayTradingMapper = dayTradingMapper;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Integer addDayTrading(DayTradingPo dayTradingPo) {
		return dayTradingMapper.addDayTrading(dayTradingPo);
	}
	@Override
	public Integer updateDayTradingByTxCode(DayTradingPo dayTradingPo) {
		return dayTradingMapper.updateDayTradingByTxCode(dayTradingPo);
	}
	@Override
	public DayTradingPo selectDayTradingByTxCode(DayTradingPo dayTradingPo) {
		return dayTradingMapper.selectDayTradingByTxCode(dayTradingPo);
	}
	@Override
	public List<DayTradingPo> selectDayTradingByPageByCondition(DayTradingQuery dayTradingQuery) {
		// TODO Auto-generated method stub
        List<DayTradingPo> dayTradings = dayTradingMapper.selectDayTradingByPageByCondition(dayTradingQuery);
		return dayTradings;
	}
	@Override
	public Integer deleteDayTradingByPageByCondition(DayTradingPo dayTradingPo) {
		// TODO Auto-generated method stub
		return dayTradingMapper.deleteDayTradingByPageByCondition(dayTradingPo);
	}
	@Override
	public List<DayTradingPo> selectDayTradingByMinute(DayTradingQuery dayTradingQuery) {
        List<DayTradingPo> dayTradings = dayTradingMapper.selectDayTradingByMinute(dayTradingQuery);
		return dayTradings;
	}
	@Override
	public List<DayTradingPo> selectDayTradingByHour(DayTradingQuery dayTradingQuery) {
        List<DayTradingPo> dayTradings = dayTradingMapper.selectDayTradingByHour(dayTradingQuery);
		return dayTradings;
	}
	@Override
	public List<DayTradingPo> selectDayTradingByDay(DayTradingQuery dayTradingQuery) {
        List<DayTradingPo> dayTradings = dayTradingMapper.selectDayTradingByDay(dayTradingQuery);
		return dayTradings;
	}




}
