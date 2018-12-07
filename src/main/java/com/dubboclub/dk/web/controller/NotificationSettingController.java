/**
 * 
 */
package com.dubboclub.dk.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dubboclub.dk.admin.model.Override;
import com.dubboclub.dk.admin.service.ProviderService;
import com.dubboclub.dk.warning.service.NotificationService;
import com.dubboclub.dk.web.model.OverrideInfo;

/**
 * Copyright: Copyright (c) 2018 东华软件股份公司
 * 
 * @Description: 
 *
 * @author: 黄祖真
 * @date: 2018年12月7日 上午9:21:12 
 *
 */
@Controller
@RequestMapping("/notification")
public class NotificationSettingController {

}
