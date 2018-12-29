package com.dubboclub.dk.storage.mysql.mapper;

import com.dubboclub.dk.storage.model.TradingStatisticPo;

public interface TradingStatisticMapper {
	public Integer addTradingStatistic(TradingStatisticPo tradingStatisticPo);
	public Integer updateTradingStatisticById(TradingStatisticPo tradingStatisticPo);
	public TradingStatisticPo selectTradingStatisticById(TradingStatisticPo tradingStatisticPo);
}
