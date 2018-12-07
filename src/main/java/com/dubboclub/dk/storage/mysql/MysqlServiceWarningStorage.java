/**
 * 
 */
package com.dubboclub.dk.storage.mysql;

import org.springframework.beans.factory.InitializingBean;

import com.dubboclub.dk.storage.ServiceWarningStorage;
import com.dubboclub.dk.storage.model.ServiceWarning;
import com.dubboclub.dk.storage.mysql.mapper.BizWarningMapper;
import com.dubboclub.dk.storage.mysql.mapper.ServiceWarningMapper;

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
    public int insertServiceWarning(ServiceWarning serviceWarning) {
        return serviceWarningMapper.insertServiceWarning(serviceWarning);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }

}
