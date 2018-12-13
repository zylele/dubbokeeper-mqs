/**
 * 
 */
package com.dubboclub.dk.storage.mysql;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.dubboclub.dk.storage.ServiceWarningStorage;
import com.dubboclub.dk.storage.model.CurrentPage;
import com.dubboclub.dk.storage.model.ServiceWarningPo;
import com.dubboclub.dk.storage.mysql.mapper.ServiceWarningMapper;
import com.github.pagehelper.PageHelper;

/**
 * Copyright: Copyright (c) 2018 东华软件股份公司
 * 
 * @Description: 该类的功能描述
 *
 * @author: 黄祖真
 * @date: 2018年12月7日 下午3:12:59 
 *
 */
public class MysqlServiceWarningStorage implements ServiceWarningStorage,InitializingBean {

    private ServiceWarningMapper serviceWarningMapper;

    public void setServiceWarningMapper(ServiceWarningMapper serviceWarningMapper) {
        this.serviceWarningMapper = serviceWarningMapper;
    }


    @Override
    public int insertServiceWarning(ServiceWarningPo serviceWarning) {
        return serviceWarningMapper.insertServiceWarning(serviceWarning);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }


	@Override
	public ServiceWarningPo selectServiceWarningById(ServiceWarningPo serviceWarningPo) {
		// TODO Auto-generated method stub
		return serviceWarningMapper.selectServiceWarningById(serviceWarningPo);
	}


	@Override
	public Integer deleteServiceWarningById(ServiceWarningPo serviceWarningPo) {
		// TODO Auto-generated method stub
		return serviceWarningMapper.deleteServiceWarningById(serviceWarningPo);
	}


	@Override
	public Integer updateServiceWarningById(ServiceWarningPo serviceWarningPo) {
		// TODO Auto-generated method stub
		return serviceWarningMapper.updateServiceWarningById(serviceWarningPo);
	}

    @Override
    public List<ServiceWarningPo> selectServiceWarningByPage(ServiceWarningPo serviceWarning, CurrentPage currentPage) {
        PageHelper.startPage(currentPage.getCurrentPage(), currentPage.getPageSize());
        List<ServiceWarningPo> serviceWarnings = serviceWarningMapper.selectServiceWarningByPage(serviceWarning);
        return serviceWarnings;
    }





}
