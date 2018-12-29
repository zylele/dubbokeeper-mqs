package com.dubboclub.dk.storage.mysql;

import org.springframework.beans.factory.InitializingBean;

import com.dubboclub.dk.storage.TradingStatisticStorage;
import com.dubboclub.dk.storage.model.TradingStatisticPo;
import com.dubboclub.dk.storage.mysql.mapper.TradingStatisticMapper;

public class MysqlTradingStatisticStorage implements TradingStatisticStorage,InitializingBean {
	
	private TradingStatisticMapper tradingStatisticMapper;

	public TradingStatisticMapper getTradingStatisticMapper() {
		return tradingStatisticMapper;
	}
	
	public void setTradingStatisticMapper(TradingStatisticMapper tradingStatisticMapper) {
		this.tradingStatisticMapper = tradingStatisticMapper;
	}
	@Override
	public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }
	@Override
	public Integer addTradingStatistic(TradingStatisticPo tradingStatisticPo) {
		return tradingStatisticMapper.addTradingStatistic(tradingStatisticPo);
	}
	@Override
	public Integer updateTradingStatisticById(TradingStatisticPo tradingStatisticPo) {
		return tradingStatisticMapper.updateTradingStatisticById(tradingStatisticPo);
	}
	@Override
	public TradingStatisticPo selectTradingStatisticById(TradingStatisticPo tradingStatisticPo) {
		return tradingStatisticMapper.selectTradingStatisticById(tradingStatisticPo);
	}
    
}
