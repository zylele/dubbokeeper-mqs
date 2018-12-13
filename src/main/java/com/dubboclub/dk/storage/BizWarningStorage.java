/**
 * 
 */
package com.dubboclub.dk.storage;

import java.util.List;

import com.dubboclub.dk.storage.model.BizWarningPo;
import com.dubboclub.dk.storage.model.CurrentPage;

/**
 * Copyright: Copyright (c) 2018 东华软件股份公司
 * 
 * @Description: 该类的功能描述
 *
 * @author: 黄祖真
 * @date: 2018年12月7日 上午10:36:30 
 *
 */
public interface BizWarningStorage {
	public BizWarningPo selectBizWarningById(BizWarningPo bizWarning);
	public Integer deleteBizWarningById(BizWarningPo bizWarningPo);
	public Integer addBizWarning(BizWarningPo bizWarningPo);
	public Integer updateBizWarningById(BizWarningPo bizWarningPo);
	public List<BizWarningPo> selectBizWarningByPage(BizWarningPo bizWarningPo, CurrentPage currentPage);

}

