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

import com.dubboclub.dk.storage.DayTradingStorage;
import com.dubboclub.dk.storage.model.DayTradingPo;
import com.dubboclub.dk.storage.model.DayTradingQuery;
import com.dubboclub.dk.web.model.BasicListResponse;
import com.dubboclub.dk.web.model.DayTradingDto;
import com.dubboclub.dk.web.model.TradingStatisticDto;

/**  
* @ClassName: DayTradingController  
* @Description:每日峰值统计Controller   
* @author zhangpengfei  
* @date 2019年3月11日   
*/
@Controller
@RequestMapping("/dayTrading")
public class DayTradingController {
	@Autowired
    @Qualifier("dayTradingStorage")
	private DayTradingStorage dayTradingStorage;
	
	// 交易趋势查询
	@RequestMapping(value = {"/getDayTradingByPageByCondition"},method = {RequestMethod.POST})
    public @ResponseBody BasicListResponse<DayTradingDto>  getDayTradingByPageByCondition(@RequestBody DayTradingQuery conditions) {
		DayTradingQuery dayTradingQuery = new DayTradingQuery();
		BeanUtils.copyProperties(conditions, dayTradingQuery);
       
		// 以每五分钟一条数据返回
        if(dayTradingQuery.getFindType().equals("minute")) {
        	   	List<DayTradingPo> listPo = dayTradingStorage.selectDayTradingByMinute(dayTradingQuery);
	   	        BasicListResponse<DayTradingDto> responseList = new BasicListResponse<DayTradingDto>();
		        List listDto = new ArrayList<TradingStatisticDto>();
		        responseList.setList(listDto);
		        for(DayTradingPo po: listPo) {
		        	DayTradingDto dayTradingDto = new DayTradingDto();
		            BeanUtils.copyProperties(po, dayTradingDto);
		            listDto.add(dayTradingDto);
		        }
		        return responseList;
        }
        // 以每小时一天数据返回
        else if(dayTradingQuery.getFindType().equals("hour")){
        		List<DayTradingPo> listPo = dayTradingStorage.selectDayTradingByMinute(dayTradingQuery);
	   	        BasicListResponse<DayTradingDto> responseList = new BasicListResponse<DayTradingDto>();
		        List listDto = new ArrayList<TradingStatisticDto>();
		        responseList.setList(listDto);
		        for(DayTradingPo po: listPo) {
		        	DayTradingDto dayTradingDto = new DayTradingDto();
		            BeanUtils.copyProperties(po, dayTradingDto);
		            listDto.add(dayTradingDto);
		        }
		        return responseList;
        }
        //以每天一条数据返回
        else if(dayTradingQuery.getFindType().equals("day")){
        		List<DayTradingPo> listPo = dayTradingStorage.selectDayTradingByDay(dayTradingQuery);
	   	        BasicListResponse<DayTradingDto> responseList = new BasicListResponse<DayTradingDto>();
		        List listDto = new ArrayList<TradingStatisticDto>();
		        responseList.setList(listDto);
		        for(DayTradingPo po: listPo) {
		        	DayTradingDto dayTradingDto = new DayTradingDto();
		            BeanUtils.copyProperties(po, dayTradingDto);
		            listDto.add(dayTradingDto);
		        }
		        return responseList;
        }
        
        return null;
    }

}
