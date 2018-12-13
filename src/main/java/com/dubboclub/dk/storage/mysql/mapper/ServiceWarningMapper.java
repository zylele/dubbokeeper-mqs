/**
 * 
 */
package com.dubboclub.dk.storage.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dubboclub.dk.storage.model.ServiceWarningPo;

/**
 * Copyright: Copyright (c) 2018 东华软件股份公司
 * 
 * @Description: 该类的功能描述
 *
 * @author: 黄祖真
 * @date: 2018年12月7日 下午3:14:20 
 *
 */
public interface ServiceWarningMapper {
    public int insertServiceWarning(@Param("serviceWarning")ServiceWarningPo serviceWarning);
    public List<ServiceWarningPo> selectServiceWarningByPage(ServiceWarningPo serviceWarning);
    public ServiceWarningPo selectServiceWarningById(ServiceWarningPo serviceWarning);
	public Integer deleteServiceWarningById(ServiceWarningPo serviceWarningPo);
	public Integer updateServiceWarningById(ServiceWarningPo serviceWarningPo);
}
