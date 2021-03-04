package com.dubboclub.dk.storage.mysql.mapper;

import java.util.List;

import com.dubboclub.dk.storage.model.DayTradingPo;
import com.dubboclub.dk.storage.model.DayTradingQuery;

public interface DayTradingMapper {
	public Integer addDayTrading(DayTradingPo dayTradingPo);
	public Integer updateDayTradingByTxCode(DayTradingPo dayTradingPo);
	public Integer deleteDayTradingByPageByCondition(DayTradingPo dayTradingPo);
	public DayTradingPo selectDayTradingByTxCode(DayTradingPo dayTradingPo);
	public List<DayTradingPo> selectDayTradingByPageByCondition(DayTradingQuery dayTradingQuery);
	public List<DayTradingPo> selectDayTradingByMinute(DayTradingQuery dayTradingQuery);
	public List<DayTradingPo> selectDayTradingByHour(DayTradingQuery dayTradingQuery);
	public List<DayTradingPo> selectDayTradingByDay(DayTradingQuery dayTradingQuery);

}
