/**
 * 
 */
package com.dubboclub.dk.storage.mysql;

import org.springframework.beans.factory.InitializingBean;

import com.dubboclub.dk.storage.BizWarningStorage;
import com.dubboclub.dk.storage.mysql.mapper.BizWarningMapper;

/**
 * Copyright: Copyright (c) 2018 东华软件股份公司
 * 
 * @Description: 该类的功能描述
 *
 * @author: 黄祖真
 * @date: 2018年12月7日 上午10:29:35 
 *
 */
public class MysqlBizWarningStorage implements BizWarningStorage,InitializingBean {

    private BizWarningMapper bizWarningMapper;
    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }

}