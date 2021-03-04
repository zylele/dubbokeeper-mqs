package com.dubboclub.dk.storage.mysql;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.dubboclub.dk.storage.TradingStatisticStorage;
import com.dubboclub.dk.storage.model.CurrentPage;
import com.dubboclub.dk.storage.model.TradingStatisticPo;
import com.dubboclub.dk.storage.model.TradingStatisticQuery;
import com.dubboclub.dk.storage.model.TradingStatisticQueryTime;
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

	@Override
	public Integer deleteTradingStatisticByPageByCondition(TradingStatisticPo tradingStatisticPo) {
		// TODO Auto-generated method stub
		return tradingStatisticMapper.deleteTradingStatisticByPageByCondition(tradingStatisticPo);
	}
	@Override
	public List<TradingStatisticPo> selectTradingStatisticByPageByDayFailRate(TradingStatisticQueryTime tradingStatisticQueryTime) {
		// TODO Auto-generated method stub
        List<TradingStatisticPo> tradingStatistics = tradingStatisticMapper.selectTradingStatisticByPageByDayFailRate(tradingStatisticQueryTime);
		return tradingStatistics;
	}

	@Override
	public List<TradingStatisticPo> selectTradingStatisticByPageByTxType(TradingStatisticQueryTime tradingStatisticQueryTime
			) {
		// TODO Auto-generated method stub
        List<TradingStatisticPo> tradingStatistics = tradingStatisticMapper.selectTradingStatisticByPageByTxType(tradingStatisticQueryTime);
		return tradingStatistics;
	}
	@Override
	public List<TradingStatisticPo> selectTradingStatisticByPageByFail(TradingStatisticQueryTime tradingStatisticQueryTime,
			CurrentPage currentPage) {
		// TODO Auto-generated method stub
		PageHelper.startPage(currentPage.getCurrentPage(), currentPage.getPageSize());
        List<TradingStatisticPo> tradingStatistics = tradingStatisticMapper.selectTradingStatisticByPageByFail(tradingStatisticQueryTime);
		return tradingStatistics;
	}
	@Override
	public List<TradingStatisticPo> selectTradingStatisticByType(TradingStatisticQueryTime tradingStatisticQueryTime,
			CurrentPage currentPage) {
		// TODO Auto-generated method stub
		PageHelper.startPage(currentPage.getCurrentPage(), currentPage.getPageSize());
        List<TradingStatisticPo> tradingStatistics = tradingStatisticMapper.selectTradingStatisticByType(tradingStatisticQueryTime);
		return tradingStatistics;
	}

	@Override
	public List<TradingStatisticPo> selectTradingStatisticBydate(TradingStatisticQuery tradingStatisticQuery) {
		List<TradingStatisticPo> tradingStatistics = tradingStatisticMapper.selectTradingStatisticBydate(tradingStatisticQuery);
		return tradingStatistics;
	}

	
    
}
