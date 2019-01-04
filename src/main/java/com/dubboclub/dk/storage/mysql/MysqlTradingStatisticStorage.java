package com.dubboclub.dk.storage.mysql;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.dubboclub.dk.storage.TradingStatisticStorage;
import com.dubboclub.dk.storage.model.CurrentPage;
import com.dubboclub.dk.storage.model.TradingStatisticPo;
import com.dubboclub.dk.storage.model.TradingStatisticQuery;
import com.dubboclub.dk.storage.mysql.mapper.TradingStatisticMapper;
import com.github.pagehelper.PageHelper;

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
	public Integer updateTradingStatisticByTxCode(TradingStatisticPo tradingStatisticPo) {
		return tradingStatisticMapper.updateTradingStatisticByTxCode(tradingStatisticPo);
	}
	@Override
	public TradingStatisticPo selectTradingStatisticByTxCode(TradingStatisticPo tradingStatisticPo) {
		return tradingStatisticMapper.selectTradingStatisticByTxCode(tradingStatisticPo);
	}

	@Override
	public List<TradingStatisticPo> selectTradingStatisticByPageByCondition(TradingStatisticQuery tradingStatisticQuery,
			CurrentPage currentPage) {
		// TODO Auto-generated method stub
		PageHelper.startPage(currentPage.getCurrentPage(), currentPage.getPageSize());
        List<TradingStatisticPo> tradingStatistics = tradingStatisticMapper.selectTradingStatisticByPageByCondition(tradingStatisticQuery);
		return tradingStatistics;
	}

	
    
}
