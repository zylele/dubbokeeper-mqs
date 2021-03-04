package com.dubboclub.dk.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import com.dubboclub.dk.admin.model.Application;
import com.dubboclub.dk.admin.model.Node;
import com.dubboclub.dk.admin.service.ApplicationService;
import com.dubboclub.dk.common.SendMessage;
import com.dubboclub.dk.remote.esb.dto.SendEmailReq;
import com.dubboclub.dk.storage.AlarmServiceStorage;
import com.dubboclub.dk.storage.NotificationStorage;
import com.dubboclub.dk.storage.model.AlarmServicePo;
import com.dubboclub.dk.web.utils.ConstantsUtil;

  
/**  
* @ClassName: ServiceWarningTaskImpl  
* @Description: 检测服务节点异常实现  
* @author jinxiaolei
* @date 2019年3月5日   
*/  
@Component
public class ServiceWarningTaskImpl implements ServiceWarningTask {	
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceWarningTaskImpl.class);
    @Autowired
    private SendMessage sendMessage;
	@Autowired
    private ApplicationService applicationService;   
    @Autowired
    private AlarmServiceStorage alarmServiceStorage;
    
	/**
	  * 定时检查zookeeper中应用节点状态 
	 */
    @Scheduled(cron="0/10 * *  * * ? ")   //每10秒执行一次  
  	@Override
	public void checkAppStatus() {
    	// 获取最新节点信息与库内节点信息
    	List<AlarmServicePo> newData = selectAppdata();
    	List<AlarmServicePo> oldData = alarmServiceStorage.selectAlarmServiceAll();
    	
    	// 如增，更新，如减，证明节点可能故障通知相关人员
    	if(newData.size()>oldData.size()){
    		updatetAppData(newData);
    	}
    	if(newData.size()<oldData.size()){
    		List<AlarmServicePo> dieData =  getDiffrent(oldData,newData);
    		updatetAppData(newData);
    		// 发送邮件及短信通知相关人员
    		setMailObj(dieData);    	
    		setMsgObj(dieData);
    	}
    	
	}
    

    /**
             * 获取zookeeper中最新节点信息
     */
	private List<AlarmServicePo> selectAppdata() {
		List<AlarmServicePo> serviceData = new ArrayList<AlarmServicePo>();
		// 获取应用名称集合
		List<Application> appData = applicationService.getApplications();
		// 遍历应用名称集合并获取部署节点集合
		for (Application application : appData) {
			List<Node> appHostData = applicationService.getNodesByApplicationName(application.getApplication());
			// 封装数据并入库
			for (Node node : appHostData) {
				AlarmServicePo alarmServicePo = new AlarmServicePo();
				alarmServicePo.setServiceName(application.getApplication());
				alarmServicePo.setHost(node.getNodeAddress());
				serviceData.add(alarmServicePo);
			}
		}
		
		return serviceData;
	}
	
	
	/**
	  * 更新数据库节点信息
	 */
	private void updatetAppData(List<AlarmServicePo> list) {
		// 删除原有数据并更新最新数据
		alarmServiceStorage.deleteAlarmService();		
		for (AlarmServicePo alarmServicePo : list) {
			alarmServiceStorage.addAlarmService(alarmServicePo);
		}
	}
	
	 
	/**
            * 筛选少掉的的节点信息
	 */
	private List<AlarmServicePo> getDiffrent(List<AlarmServicePo> bigCol, List<AlarmServicePo> smallCol){
		List<AlarmServicePo> dieResult = new ArrayList<>();
		//在添加进map的时候先遍历较大集合，这样子可以减少没必要的判断	,创建 Map<对象,出现次数> (直接指定大小减少空间浪费)
		Map<Object, Integer> map = new HashMap<>(bigCol.size());
		//遍历大集合把元素put进map，初始出现次数为1
		for(AlarmServicePo p : bigCol) {
			map.put(p, 1);
		}
		//遍历小集合，如果map中存在小集合中的元素，把出现次数置为2
		for(AlarmServicePo p : smallCol) {
			if (map.get(p) != null) {
				map.put(p, 2);				
			}
		}
		//把出现次数为1的 Key:Value 捞出，并把Key添加到返回结果
		for(Map.Entry<Object, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 1) {
				dieResult.add((AlarmServicePo) entry.getKey());
			}
		}		
		
		return dieResult;
	}
	
	
	/**
	 * 发送邮件
	 */
	private void setMailObj(List<AlarmServicePo> dieData){
		// 拼接邮件内容
		StringBuilder sb = new StringBuilder();
		for (AlarmServicePo alarmServicePo : dieData) {
			sb.append("服务名称："+alarmServicePo.getServiceName()+"------");
			sb.append("节点地址："+alarmServicePo.getHost()+";"+"<br>");
		}
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String time = sd.format(new Date());
		String str =  "以下应用服务于 "+time+" 出现异常，请及时检查:"+"<br><br>"+sb.toString();	
		// IBS 为全渠道(系统异常发送这个渠道)，
		List<String> mails = sendMessage.queryAddressByChnlCode("IBS");
		SendEmailReq sendEmailReq = new SendEmailReq();
		sendEmailReq.setSceneCode("M001");
		sendEmailReq.setBusType("OutOpenAcc");
		sendEmailReq.setSubject(ConstantsUtil.MAIL_SUBJECT);
		sendEmailReq.setMailTo(mails);
		sendEmailReq.setAttachments(null);
		sendEmailReq.setMsg(str);
		sendMessage.sendWarningMailAsyc(sendEmailReq, "000000");
	}
	

	/**
	 * 发送短信
	 */
    private void setMsgObj(List<AlarmServicePo> dieData) {
    	// 拼接邮件内容
    	StringBuilder sb = new StringBuilder();
		for (AlarmServicePo alarmServicePo : dieData) {
			sb.append("服务名称："+alarmServicePo.getServiceName()+"---");
			sb.append("节点地址："+alarmServicePo.getHost()+";");
		}
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String time = sd.format(new Date());
		String str = "以下应用服务于"+time+"出现异常，请及时检查!     "+sb.toString();
    	// IBS 为全渠道(系统异常发送这个渠道)，
    	List<String> phones = sendMessage.queryPhoneNumsByChnlCode("IBS");
		// 根据场景码将map的key设置为对应字符串
		Map<String, String> msg = new LinkedHashMap<String, String>();
		msg.put("msg", str);
		sendMessage.sendWarningPhoneAsyc(msg, phones);
	}
 
}
