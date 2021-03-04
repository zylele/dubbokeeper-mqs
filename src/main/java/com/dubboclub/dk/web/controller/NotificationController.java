package com.dubboclub.dk.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dubboclub.dk.notification.WarningStatusHolder;
import com.dubboclub.dk.storage.NotificationStorage;
import com.dubboclub.dk.storage.model.ChnlDefPo;
import com.dubboclub.dk.storage.model.NotificationPo;
import com.dubboclub.dk.storage.model.TxCodePo;
import com.dubboclub.dk.web.model.BaseQueryConditions;
import com.dubboclub.dk.web.model.BasicListResponse;
import com.dubboclub.dk.web.model.ChnlDefDto;
import com.dubboclub.dk.web.model.NotificationDto;
import com.dubboclub.dk.web.model.NotificationResultDto;
import com.dubboclub.dk.web.model.TxCodeDto;
import com.dubboclub.dk.web.model.WarningStatusDto;
import com.github.pagehelper.PageInfo;

/**  
* @ClassName: NotificationController  
* @Description:增、删、改、查邮箱地址的Controller   
* @author zhangpengfei  
* @date 2019年3月11日   
*/
@Controller
@RequestMapping("/notification")
public class NotificationController {
	@Autowired
    @Qualifier("notificationStorage")
    private NotificationStorage notificationStorage;
	@Autowired
	private WarningStatusHolder warningStatusHolder;
	//查询单个邮箱地址
    @RequestMapping(value = {"/getNotificationById"},method = {RequestMethod.POST})
    public @ResponseBody NotificationDto getNotificationById(@RequestBody NotificationDto notification){
    	NotificationPo notificationPo = new NotificationPo();
    	notificationPo.setId(notification.getId());
    	NotificationDto notificationDto = new NotificationDto();
    	NotificationPo notificationPoResult = notificationStorage.selectNotificationById(notificationPo);
    	BeanUtils.copyProperties(notificationPoResult, notificationDto);
        return notificationDto;
    }
    //删除单条邮箱地址
    @RequestMapping(value = {"/deleteNotificationById"},method = {RequestMethod.POST})
    public @ResponseBody NotificationResultDto deleteNotificationById(@RequestBody NotificationDto notification){
    	NotificationPo notificationPo =new NotificationPo();
    	notificationPo.setId(notification.getId());
    	Integer notificationPoResult = notificationStorage.deleteNotificationById(notificationPo);
    	return new NotificationResultDto(notificationPoResult);	
    }
    //添加单个邮箱地址
    @RequestMapping(value = {"/addNotification"},method = {RequestMethod.POST})
    public @ResponseBody NotificationResultDto addNotification(@RequestBody NotificationDto notification){
    	NotificationPo notificationPo = new NotificationPo();
    	BeanUtils.copyProperties(notification, notificationPo);
    	Integer notificationPoResult = notificationStorage.addNotification(notificationPo);
        return new NotificationResultDto(notificationPoResult);
    }
    //更新单个邮箱地址
    @RequestMapping(value = {"/updateNotificationById"},method = {RequestMethod.POST})
    public @ResponseBody NotificationResultDto updateNotificationById(@RequestBody NotificationDto notification){
    	NotificationPo notificationPo = new NotificationPo();
		BeanUtils.copyProperties(notification, notificationPo);
    	Integer notificationPoResult = notificationStorage.updateNotificationById(notificationPo);
        return new NotificationResultDto(notificationPoResult);
    }
    
    //查询多个邮箱地址
    @RequestMapping(value = {"/getNotificationByPage"},method = {RequestMethod.POST})
    public @ResponseBody BasicListResponse<NotificationDto>  getNotificationByPage(@RequestBody BaseQueryConditions<NotificationDto>  conditions) {
    	NotificationPo notificationPo = new NotificationPo();
        BeanUtils.copyProperties(conditions.getConditions(), notificationPo);
        List<NotificationPo> listPo = notificationStorage.selectNotificationByPage(notificationPo, conditions.getCurrentPage());
        PageInfo<NotificationPo> pageInfo = new PageInfo<>(listPo);
        BasicListResponse<NotificationDto> responseList = new BasicListResponse<NotificationDto>();
        responseList.setTotalCount(pageInfo.getTotal());
        List listDto = new ArrayList<NotificationDto>();
        responseList.setList(listDto);
        for(NotificationPo po: listPo) {
        	NotificationDto NotificationDto = new NotificationDto();
            BeanUtils.copyProperties(po, NotificationDto);
            listDto.add(NotificationDto);
        }
        return responseList;
    }
    @RequestMapping(value = {"/getChnlDef"},method = {RequestMethod.POST})
    public @ResponseBody BasicListResponse<ChnlDefDto>  getChnlDef() {
    	ChnlDefPo chnlDefPo = new ChnlDefPo();
        List<ChnlDefPo> listPo = notificationStorage.getChnlDef(chnlDefPo);
        BasicListResponse<ChnlDefDto> responseList = new BasicListResponse<ChnlDefDto>();
        List listDto = new ArrayList<NotificationDto>();
        responseList.setList(listDto);
        for(ChnlDefPo po: listPo) {
        	ChnlDefDto chnlDefDto = new ChnlDefDto();
            BeanUtils.copyProperties(po, chnlDefDto);
            listDto.add(chnlDefDto);
        }
        return responseList;
    }
    @RequestMapping(value = {"/getTxCode"},method = {RequestMethod.POST})
    public @ResponseBody BasicListResponse<TxCodeDto>  getTxcode() {
    	TxCodePo txCodePo = new TxCodePo();
        List<TxCodePo> listPo = notificationStorage.getTxcode(txCodePo);
        BasicListResponse<TxCodeDto> responseList = new BasicListResponse<TxCodeDto>();
        List listDto = new ArrayList<NotificationDto>();
        responseList.setList(listDto);
        for(TxCodePo po: listPo) {
        	TxCodeDto txCodeDto = new TxCodeDto();
            BeanUtils.copyProperties(po, txCodeDto);
            listDto.add(txCodeDto);
        }
        return responseList;
    }
    
    @RequestMapping(value = {"/getWarningStatus"},method = {RequestMethod.POST})
    public @ResponseBody WarningStatusDto getWarningStatus(){
    	WarningStatusDto warningStatusDto = new WarningStatusDto();
		BeanUtils.copyProperties(warningStatusHolder, warningStatusDto);
		warningStatusHolder.setBizStatus(false);
		warningStatusHolder.setServiceStatus(false);
        return warningStatusDto;
    }
    
}
