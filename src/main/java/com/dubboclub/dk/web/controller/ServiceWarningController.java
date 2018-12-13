/**
 * 
 */
package com.dubboclub.dk.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dubboclub.dk.storage.ServiceWarningStorage;
import com.dubboclub.dk.storage.model.ServiceWarningPo;
import com.dubboclub.dk.web.model.BaseQueryConditions;
import com.dubboclub.dk.web.model.BasicListResponse;
import com.dubboclub.dk.web.model.ServiceWarningDto;
import com.dubboclub.dk.web.model.ServiceWarningResultDto;
import com.github.pagehelper.PageInfo;

/**
 * Copyright: Copyright (c) 2018 东华软件股份公司
 * 
 * @Description: 该类的功能描述
 *
 * @author: 黄祖真
 * @date: 2018年12月11日 上午11:34:54 
 *
 */
@Controller
@RequestMapping("/servicewarning")
public class ServiceWarningController {
    @Autowired
    @Qualifier("serviceWarningStorage")
    private ServiceWarningStorage serviceWarningStorage;
  //查询单条语句并返回
    @RequestMapping("/getServiceWarningById")
    public @ResponseBody ServiceWarningDto getServiceWarningById(@RequestBody ServiceWarningDto serviceWarning){
    	ServiceWarningPo serviceWarningPo = new ServiceWarningPo();
    	serviceWarningPo.setId(serviceWarning.getId());
    	ServiceWarningDto serviceWarningDto = new ServiceWarningDto();
    	ServiceWarningPo serviceWarningPoResult = serviceWarningStorage.selectServiceWarningById(serviceWarningPo);
    	BeanUtils.copyProperties(serviceWarningPoResult, serviceWarningDto);
        return serviceWarningDto;
    }
    
  //删除单条语句
  	@RequestMapping("/deleteServiceWarningById")
      public @ResponseBody ServiceWarningResultDto deleteServiceWarningById(@RequestBody ServiceWarningDto serviceWarning){
      	ServiceWarningPo serviceWarningPo = new ServiceWarningPo();
      	serviceWarningPo.setId(serviceWarning.getId());
      	ServiceWarningDto serviceWarningDto = new ServiceWarningDto();
      	serviceWarningDto.setId(serviceWarning.getId());
      	Integer serviceWarningPoResult = serviceWarningStorage.deleteServiceWarningById(serviceWarningPo);
      	
          return new ServiceWarningResultDto(serviceWarningPoResult);
      }
      
  	//修改单条语句
  	@RequestMapping("/updateServiceWarningById")
      public @ResponseBody ServiceWarningResultDto updateServiceWarningById(@RequestBody ServiceWarningDto serviceWarning){
  		ServiceWarningPo serviceWarningPo = new ServiceWarningPo();
  		BeanUtils.copyProperties(serviceWarning, serviceWarningPo);
      	Integer serviceWarningPoResult = serviceWarningStorage.updateServiceWarningById(serviceWarningPo);
          return new ServiceWarningResultDto(serviceWarningPoResult);
      }
    //查询多条语句
    @RequestMapping("/getServiceWarningByPage")
    public @ResponseBody BasicListResponse<ServiceWarningDto>  getServiceWarningByPage(@RequestBody BaseQueryConditions<ServiceWarningDto>  conditions) {
        ServiceWarningPo serviceWarningPo = new ServiceWarningPo();
        BeanUtils.copyProperties(conditions.getConditions(), serviceWarningPo);
        List<ServiceWarningPo> listPo = serviceWarningStorage.selectServiceWarningByPage(serviceWarningPo, conditions.getCurrentPage());
        PageInfo<ServiceWarningPo> pageInfo = new PageInfo<>(listPo);
        BasicListResponse<ServiceWarningDto> responseList = new BasicListResponse<ServiceWarningDto>();
        responseList.setTotalCount(pageInfo.getSize());
        List listDto = new ArrayList<ServiceWarningDto>();
        responseList.setList(listDto);
        for(ServiceWarningPo po: listPo) {
            ServiceWarningDto ServiceWarningDto = new ServiceWarningDto();
            BeanUtils.copyProperties(po, ServiceWarningDto);
            listDto.add(ServiceWarningDto);
        }
        return responseList;
    }
   
    
}
