/**
 * 
 */
package com.dubboclub.dk.storage.mysql;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.dubboclub.dk.storage.BizWarningStorage;
import com.dubboclub.dk.storage.model.BizWarningPo;
import com.dubboclub.dk.storage.model.CurrentPage;
import com.dubboclub.dk.storage.mysql.mapper.BizWarningMapper;
import com.github.pagehelper.PageHelper;

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

    public BizWarningMapper getBizWarningMapper() {
		return bizWarningMapper;
	}
	public void setBizWarningMapper(BizWarningMapper bizWarningMapper) {
		this.bizWarningMapper = bizWarningMapper;
	}
	@Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }
	@Override
	public BizWarningPo selectBizWarningById(BizWarningPo bizWarning) {
		// TODO Auto-generated method stub
		return bizWarningMapper.selectBizWarningById(bizWarning);
	}
	@Override
	public Integer deleteBizWarningById(BizWarningPo bizWarningPo) {
		// TODO Auto-generated method stub
		return bizWarningMapper.deleteBizWarningById(bizWarningPo);
	}
	
	@Override
	public Integer addBizWarning(BizWarningPo bizWarningPo) {
		// TODO Auto-generated method stub
		return bizWarningMapper.addBizWarning(bizWarningPo);
	}
	@Override
	public Integer updateBizWarningById(BizWarningPo bizWarningPo) {
		// TODO Auto-generated method stub
		return bizWarningMapper.updateBizWarningById(bizWarningPo);
	}
	@Override
	public List<BizWarningPo> selectBizWarningByPage(BizWarningPo bizWarningPo, CurrentPage currentPage) {
		// TODO Auto-generated method stub
		PageHelper.startPage(currentPage.getCurrentPage(), currentPage.getPageSize());
        List<BizWarningPo> bizWarnings = bizWarningMapper.selectBizWarningByPage(bizWarningPo);
        return bizWarnings;
	}

	
	
	

	
	
	
	

	
	

	

}
