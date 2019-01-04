package com.dubboclub.dk.storage;

import java.util.List;

import com.dubboclub.dk.storage.model.CurrentPage;
import com.dubboclub.dk.storage.model.TradingStatisticPo;
import com.dubboclub.dk.storage.model.TradingStatisticQuery;

public interface TradingStatisticStorage {
	public Integer addTradingStatistic(TradingStatisticPo tradingStatisticPo);
	public Integer updateTradingStatisticByTxCode(TradingStatisticPo tradingStatisticPo);
	public TradingStatisticPo selectTradingStatisticByTxCode(TradingStatisticPo tradingStatisticPo);
	public List<TradingStatisticPo> selectTradingStatisticByPageByCondition(TradingStatisticQuery tradingStatisticQuery,
			CurrentPage currentPage);
	
	
}
