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
    
    @RequestMapping("alarm/list.htm")

    public @ResponseBody BizWarningDto getBizWarningById(@RequestBody BizWarningPo bizWarning){
    	BizWarningDto bizWarningDto = new BizWarningDto();
    	BizWarningPo bizWarningPo = bizWarningStorage.selectBizWarningById(bizWarning);
    	BeanUtils.copyProperties(bizWarningPo, bizWarningDto);
    	
        return bizWarningDto;
    }
    

}
