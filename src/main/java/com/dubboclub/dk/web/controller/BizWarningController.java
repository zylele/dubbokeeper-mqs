/**
 * 
 */
package com.dubboclub.dk.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dubboclub.dk.storage.BizWarningStorage;
import com.dubboclub.dk.storage.model.BizWarningPo;
import com.dubboclub.dk.storage.model.BizWarningQuery;
import com.dubboclub.dk.web.model.BaseQueryConditions;
import com.dubboclub.dk.web.model.BasicListResponse;
import com.dubboclub.dk.web.model.BizWarningDto;
import com.dubboclub.dk.web.model.BizWarningResultDto;
import com.dubboclub.dk.web.utils.ConstantsUtil;
import com.github.pagehelper.PageInfo;

/**
 * Copyright: Copyright (c) 2018 东华软件股份公司
 * 
 * @Description: 该类的功能描述
 *
 * @author: 黄祖真
 * @date: 2018年12月7日 上午9:40:38 
 *
 */
@Controller
@RequestMapping("/bizwarning")
public class BizWarningController {
    @Autowired
    @Qualifier("bizWarningStorage")
    private BizWarningStorage bizWarningStorage;
    
    //查询单条语句并返回
    @RequestMapping(value = {"/getBizWarningById"},method = {RequestMethod.POST})
    public @ResponseBody BizWarningDto getBizWarningById(@RequestBody BizWarningDto bizWarning){
    	BizWarningPo bizWarningPo = new BizWarningPo();
    	bizWarningPo.setId(bizWarning.getId());
    	BizWarningDto bizWarningDto = new BizWarningDto();
    	BizWarningPo bizWarningPoResult = bizWarningStorage.selectBizWarningById(bizWarningPo);
    	BeanUtils.copyProperties(bizWarningPoResult, bizWarningDto);
        return bizWarningDto;
        
    }
    
    //删除单条语句
	@RequestMapping(value = {"/deleteBizWarningById"},method = {RequestMethod.POST})
    public @ResponseBody BizWarningResultDto deleteBizWarningById(@RequestBody BizWarningDto bizWarning){
    	BizWarningPo bizWarningPo = new BizWarningPo();
    	bizWarningPo.setId(bizWarning.getId());
    	BizWarningDto bizWarningDto = new BizWarningDto();
    	bizWarningDto.setId(bizWarning.getId());
    	Integer bizWarningPoResult = bizWarningStorage.deleteBizWarningById(bizWarningPo);
    	
        return new BizWarningResultDto(bizWarningPoResult);
    }
    
    //插入单条语句
	@RequestMapping(value = {"/addBizWarning"},method = {RequestMethod.POST})
    public @ResponseBody BizWarningResultDto addBizWarning(@RequestBody BizWarningDto bizWarning){
    	BizWarningPo bizWarningPo = new BizWarningPo();
    	BeanUtils.copyProperties(bizWarning, bizWarningPo);
    	bizWarningPo.setTraceDt(new SimpleDateFormat(ConstantsUtil.DATE_FORMAT).format(new Date()));
    	Integer bizWarningPoResult = bizWarningStorage.addBizWarning(bizWarningPo);
        return new BizWarningResultDto(bizWarningPoResult);
    }
	//修改单条语句
	@RequestMapping(value = {"/updateBizWarningById"},method = {RequestMethod.POST})
    public @ResponseBody BizWarningResultDto updateBizWarningById(@RequestBody BizWarningDto bizWarning){
		BizWarningPo bizWarningPo = new BizWarningPo();
		BeanUtils.copyProperties(bizWarning, bizWarningPo);
    	Integer bizWarningPoResult = bizWarningStorage.updateBizWarningById(bizWarningPo);
        return new BizWarningResultDto(bizWarningPoResult);
    }
	//查询多条语句
	@RequestMapping(value = {"/getBizWarningByPage"},method = {RequestMethod.POST})
    public @ResponseBody BasicListResponse<BizWarningDto>  getBizWarningByPage(@RequestBody BaseQueryConditions<BizWarningDto>  conditions) {
        BizWarningPo bizWarningPo = new BizWarningPo();
        BeanUtils.copyProperties(conditions.getConditions(), bizWarningPo);
        List<BizWarningPo> listPo = bizWarningStorage.selectBizWarningByPage(bizWarningPo, conditions.getCurrentPage());
        PageInfo<BizWarningPo> pageInfo = new PageInfo<>(listPo);
        BasicListResponse<BizWarningDto> responseList = new BasicListResponse<BizWarningDto>();
        responseList.setTotalCount(pageInfo.getTotal());
        List listDto = new ArrayList<BizWarningDto>();
        responseList.setList(listDto);
        for(BizWarningPo po: listPo) {
            BizWarningDto bizWarningDto = new BizWarningDto();
            BeanUtils.copyProperties(po, bizWarningDto);
            listDto.add(bizWarningDto);
        }
        return responseList;
    }
	//按时间段查询数据
	@RequestMapping(value = {"/getBizWarningByPageByCondition"},method = {RequestMethod.POST})
    public @ResponseBody BasicListResponse<BizWarningDto>  getBizWarningByPageByCondition(@RequestBody BaseQueryConditions<BizWarningQuery>  conditions) {
		BizWarningQuery bizWarningQuery = new BizWarningQuery();
		BeanUtils.copyProperties(conditions.getConditions(), bizWarningQuery);
		if(bizWarningQuery.getTxCode()!=null) {
			if(bizWarningQuery.getTxCode().equals("undefined")) {
				bizWarningQuery.setTxCode(null);
			}		
		}
		if(bizWarningQuery.getChnlType()!=null) {
			if(bizWarningQuery.getChnlType().equals("undefined")) {
				bizWarningQuery.setChnlType(null);
			}else {
				String chnltype = bizWarningQuery.getChnlType();
				bizWarningQuery.setChnlType("\"chnlType\":\""+chnltype+"\"");
			}
		}
		List<BizWarningPo> listPo = bizWarningStorage.selectBizWarningByPageByCondition(bizWarningQuery, conditions.getCurrentPage());
		PageInfo<BizWarningPo> pageInfo = new PageInfo<>(listPo);
        BasicListResponse<BizWarningDto> responseList = new BasicListResponse<BizWarningDto>();
        responseList.setTotalCount(pageInfo.getTotal());
        List listDto = new ArrayList<BizWarningDto>();
        responseList.setList(listDto);
        for(BizWarningPo po: listPo) {
            BizWarningDto bizWarningDto = new BizWarningDto();
            BeanUtils.copyProperties(po, bizWarningDto);
            listDto.add(bizWarningDto);
        }
        return responseList;
    }
    

}
