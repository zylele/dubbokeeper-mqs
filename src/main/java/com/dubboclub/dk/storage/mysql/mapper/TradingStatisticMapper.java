package com.dubboclub.dk.storage.mysql.mapper;

import java.util.List;

import com.dubboclub.dk.storage.model.TradingStatisticPo;
import com.dubboclub.dk.storage.model.TradingStatisticQuery;
import com.dubboclub.dk.storage.model.TradingStatisticQueryTime;

public interface TradingStatisticMapper {
	public Integer addTradingStatistic(TradingStatisticPo tradingStatisticPo);
	public Integer updateTradingStatisticByTxCode(TradingStatisticPo tradingStatisticPo);
	public Integer deleteTradingStatisticByPageByCondition(TradingStatisticPo tradingStatisticPo);
	public TradingStatisticPo selectTradingStatisticByTxCode(TradingStatisticPo tradingStatisticPo);
	public List<TradingStatisticPo> selectTradingStatisticByPageByCondition(
			TradingStatisticQuery tradingStatisticQuery);
	public List<TradingStatisticPo> selectTradingStatisticByPageByDayFailRate(
			TradingStatisticQueryTime tradingStatisticQueryTime);
	public List<TradingStatisticPo> selectTradingStatisticByPageByTxType(
			TradingStatisticQueryTime tradingStatisticQueryTime);
	public List<TradingStatisticPo> selectTradingStatisticByPageByFail(
			TradingStatisticQueryTime tradingStatisticQueryTime);
	public List<TradingStatisticPo> selectTradingStatisticByType(
			TradingStatisticQueryTime tradingStatisticQueryTime);
	public List<TradingStatisticPo> selectTradingStatisticBydate(
			TradingStatisticQuery tradingStatisticQuery);
}
