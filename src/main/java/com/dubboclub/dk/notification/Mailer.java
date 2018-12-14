/**
 * 
 */
package com.dubboclub.dk.notification;

/**
 * Copyright: Copyright (c) 2018 东华软件股份公司
 * 
 * @Description: 该类的功能描述
 *
 * @author: 黄祖真
 * @date: 2018年12月12日 上午8:57:42 
 *
 */
public interface Mailer {
    public void sendMailByAsynchronousMode(final ApplicationEmail email);
}
