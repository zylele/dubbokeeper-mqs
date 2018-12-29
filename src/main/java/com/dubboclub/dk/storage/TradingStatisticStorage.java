package com.dubboclub.dk.storage;

import com.dubboclub.dk.storage.model.TradingStatisticPo;

public interface TradingStatisticStorage {
	public Integer addTradingStatistic(TradingStatisticPo tradingStatisticPo);
	public Integer updateTradingStatisticById(TradingStatisticPo tradingStatisticPo);
	public TradingStatisticPo selectTradingStatisticById(TradingStatisticPo tradingStatisticPo);
}
