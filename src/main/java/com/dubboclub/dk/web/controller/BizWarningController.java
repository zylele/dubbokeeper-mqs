/**
 * 
 */
package com.dubboclub.dk.web.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dubboclub.dk.storage.BizWarningStorage;

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
    
    @RequestMapping("/provider/{serviceKey}/list.htm")
    public @ResponseBody String  getMailList(@PathVariable("serviceKey")String serviceKey) throws UnsupportedEncodingException {
        //bizWarningStorage.queryWarningList();
        //ResponsModel responsModel = new ResponsModel();
        //BeanUtils.copyProperties(bizWarningStorage.queryWarningList(), responsModel);
        return "a";
    }

}
