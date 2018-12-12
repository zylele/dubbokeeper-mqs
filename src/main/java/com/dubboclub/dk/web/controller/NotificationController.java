package com.dubboclub.dk.web.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dubboclub.dk.storage.NotificationStorage;
import com.dubboclub.dk.storage.model.NotificationPo;
import com.dubboclub.dk.web.model.NotificationDto;
import com.dubboclub.dk.web.model.NotificationResultDto;

@Controller
@RequestMapping("/notification")
public class NotificationController {
	@Autowired
    @Qualifier("notificationStorage")
    private NotificationStorage notificationStorage;
	
	//查询单条语句并返回
    @RequestMapping("/getNotificationById")
    public @ResponseBody NotificationDto getNotificationById(@RequestBody NotificationDto notification){
    	NotificationPo notificationPo = new NotificationPo();
    	notificationPo.setId(notification.getId());
    	NotificationDto notificationDto = new NotificationDto();
    	NotificationPo notificationPoResult = notificationStorage.selectNotificationById(notificationPo);
    	BeanUtils.copyProperties(notificationPoResult, notificationDto);
        return notificationDto;
    }
    
    @RequestMapping("/deleteNotificationById")
    public @ResponseBody NotificationResultDto deleteNotificationById(@RequestBody NotificationDto notification){
    	NotificationPo notificationPo =new NotificationPo();
    	notificationPo.setId(notification.getId());
    	Integer notificationPoResult = notificationStorage.deleteNotificationById(notificationPo);
    	return new NotificationResultDto(notificationPoResult);	
    }
    
    @RequestMapping("/addNotification")
    public @ResponseBody NotificationResultDto addNotification(@RequestBody NotificationDto notification){
    	NotificationPo notificationPo = new NotificationPo();
    	BeanUtils.copyProperties(notification, notificationPo);
    	Integer notificationPoResult = notificationStorage.addNotification(notificationPo);
        return new NotificationResultDto(notificationPoResult);
    }
    
    @RequestMapping("/updateNotificationById")
    public @ResponseBody NotificationResultDto updateNotificationById(@RequestBody NotificationDto notification){
    	NotificationPo notificationPo = new NotificationPo();
		BeanUtils.copyProperties(notification, notificationPo);
    	Integer notificationPoResult = notificationStorage.updateNotificationById(notificationPo);
        return new NotificationResultDto(notificationPoResult);
    }
}
