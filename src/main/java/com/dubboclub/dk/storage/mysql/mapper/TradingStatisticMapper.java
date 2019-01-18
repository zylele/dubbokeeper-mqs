package com.dubboclub.dk.storage.mysql.mapper;

import java.util.List;

import com.dubboclub.dk.storage.model.TradingStatisticPo;
import com.dubboclub.dk.storage.model.TradingStatisticQuery;

public interface TradingStatisticMapper {
	public Integer addTradingStatistic(TradingStatisticPo tradingStatisticPo);
	public Integer updateTradingStatisticByTxCode(TradingStatisticPo tradingStatisticPo);
	public Integer deleteTradingStatisticByPageByCondition(TradingStatisticPo tradingStatisticPo);
	public TradingStatisticPo selectTradingStatisticByTxCode(TradingStatisticPo tradingStatisticPo);
	public List<TradingStatisticPo> selectTradingStatisticByPageByCondition(
			TradingStatisticQuery tradingStatisticQuery);
	
}
