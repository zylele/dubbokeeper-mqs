/**
 * 
 */
package com.dubboclub.dk.web.controller;

import com.dubboclub.dk.storage.model.CurrentPage;

/**
 * Copyright: Copyright (c) 2018 东华软件股份公司
 * 
 * @Description: 该类的功能描述
 *
 * @author: 黄祖真
 * @date: 2018年12月11日 上午11:41:00 
 *
 */
public class BaseQueryConditions<T> {
    private CurrentPage currentPage;
    public CurrentPage getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(CurrentPage currentPage) {
        this.currentPage = currentPage;
    }
    public T getConditions() {
        return conditions;
    }
    public void setConditions(T conditions) {
        this.conditions = conditions;
    }
    private T conditions;
    
}
