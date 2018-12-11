/**
 * 
 */
package com.dubboclub.dk.web.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dubboclub.dk.storage.BizWarningStorage;
import com.dubboclub.dk.storage.model.BizWarningPo;
import com.dubboclub.dk.web.model.BizWarningDto;
import com.dubboclub.dk.web.model.BizWarningResultDto;

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
    @RequestMapping("/getBizWarningById")
    public @ResponseBody BizWarningDto getBizWarningById(@RequestBody BizWarningDto bizWarning){
    	BizWarningPo bizWarningPo = new BizWarningPo();
    	bizWarningPo.setId(bizWarning.getId());
    	BizWarningDto bizWarningDto = new BizWarningDto();
    	BizWarningPo bizWarningPoResult = bizWarningStorage.selectBizWarningById(bizWarningPo);
    	BeanUtils.copyProperties(bizWarningPoResult, bizWarningDto);
        return bizWarningDto;
        
    }
    
    //删除单条语句
	@RequestMapping("/delectBizWarningById")
    public @ResponseBody BizWarningResultDto delectBizWarningById(@RequestBody BizWarningDto bizWarning){
    	BizWarningPo bizWarningPo = new BizWarningPo();
    	bizWarningPo.setId(bizWarning.getId());
    	BizWarningDto bizWarningDto = new BizWarningDto();
    	bizWarningDto.setId(bizWarning.getId());
    	Integer bizWarningPoResult = bizWarningStorage.deleteBizWarningById(bizWarningPo);
    	
        return new BizWarningResultDto(bizWarningPoResult);
    }
    
    //插入单条语句

    

}
