/**
 * 
 */
package com.dubboclub.dk.storage;

import java.util.List;

import com.dubboclub.dk.storage.model.CurrentPage;
import com.dubboclub.dk.storage.model.ServiceWarningPo;

/**
 * Copyright: Copyright (c) 2018 东华软件股份公司
 * 
 * @Description: 该类的功能描述
 *
 * @author: 黄祖真
 * @date: 2018年12月7日 下午3:11:53 
 *
 */
public interface ServiceWarningStorage {
    public int insertServiceWarning(ServiceWarningPo serviceWarning);
    public List<ServiceWarningPo> selectServiceWarningByPage(ServiceWarningPo serviceWarning, CurrentPage currentPage);
}
