package com.dubboclub.dk.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dubboclub.dk.storage.TradingStatisticStorage;
import com.dubboclub.dk.storage.model.TradingStatisticPo;
import com.dubboclub.dk.storage.model.TradingStatisticQuery;
import com.dubboclub.dk.web.model.BaseQueryConditions;
import com.dubboclub.dk.web.model.BasicListResponse;
import com.dubboclub.dk.web.model.TradingStatisticDto;
import com.github.pagehelper.PageInfo;

/**  
* @ClassName: TradingStatisticController  
* @Description:主要数据统计的Controller   
* @author zhangpengfei  
* @date 2019年3月11日   
*/
@Controller
@RequestMapping("/tradingStatistic")
public class TradingStatisticController {
	@Autowired
    @Qualifier("tradingStatisticStorage")
	private TradingStatisticStorage tradingStatisticStorage;
	
	//查询单条数据
	@RequestMapping(value = {"/getTradingStatisticByTxCode"},method = {RequestMethod.POST})
    public @ResponseBody TradingStatisticDto getTradingStatisticByTxCode(@RequestBody TradingStatisticDto tradingStatistic){
		TradingStatisticPo tradingStatisticPo = new TradingStatisticPo();
		tradingStatisticPo.setTxCode(tradingStatistic.getTxCode());
		tradingStatisticPo.setNowTime(tradingStatistic.getNowTime());
    	TradingStatisticDto tradingStatisticDto = new TradingStatisticDto();
    	TradingStatisticPo tradingStatisticPoResult = tradingStatisticStorage.selectTradingStatisticByTxCode(tradingStatisticPo);
    	BeanUtils.copyProperties(tradingStatisticPoResult, tradingStatisticDto);
        return tradingStatisticDto;       
    }
	//查询当天多条数据
	@RequestMapping(value = {"/getTradingStatisticByPageByCondition"},method = {RequestMethod.POST})
    public @ResponseBody BasicListResponse<TradingStatisticDto>  getTradingStatisticByPageByCondition(@RequestBody BaseQueryConditions<TradingStatisticQuery>  conditions) {
		TradingStatisticQuery tradingStatisticQuery = new TradingStatisticQuery();
        BeanUtils.copyProperties(conditions.getConditions(), tradingStatisticQuery);
        List<TradingStatisticPo> listPo = tradingStatisticStorage.selectTradingStatisticByPageByCondition(tradingStatisticQuery, conditions.getCurrentPage());
        PageInfo<TradingStatisticPo> pageInfo = new PageInfo<>(listPo);
        BasicListResponse<TradingStatisticDto> responseList = new BasicListResponse<TradingStatisticDto>();
        responseList.setTotalCount(pageInfo.getTotal());
        List listDto = new ArrayList<TradingStatisticDto>();
        responseList.setList(listDto);
        for(TradingStatisticPo po: listPo) {
        	if(po.getTxCode() != null){
		    	TradingStatisticDto tradingStatisticDto = new TradingStatisticDto();
		        BeanUtils.copyProperties(po, tradingStatisticDto);
		        listDto.add(tradingStatisticDto);
        	}
        	
        }
        return responseList;
    }

}
