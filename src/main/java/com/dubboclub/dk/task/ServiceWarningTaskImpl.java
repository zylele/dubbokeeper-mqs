package com.dubboclub.dk.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dubboclub.dk.admin.model.Application;
import com.dubboclub.dk.admin.model.Node;
import com.dubboclub.dk.admin.service.ApplicationService;
import com.dubboclub.dk.storage.AlarmServiceStorage;
import com.dubboclub.dk.storage.model.AlarmServicePo;

/**  
* @ClassName: ServiceWarningTaskImpl
* @Description:发送服务异常邮件实现类   
* @author jinxiaolei  
* @date 2019年3月4日   
*/
@Component
public class ServiceWarningTaskImpl implements ServiceWarningTask {

    @Autowired
    private ApplicationService applicationService;   
    @Autowired
    @Qualifier("alarmServiceStorage")
    private AlarmServiceStorage alarmServiceStorage;
	
    @Scheduled(cron="0/10 * *  * * ? ")   //每10秒执行一次  
	@Override
	public void getServiceWarningTask() {		
		// 获取应用名称信息
		List<Application> appData = applicationService.getApplications();
		String appName = null;
		AlarmServicePo alarmServicePo = new AlarmServicePo();
		//遍历应用名称集合
		for (Application application : appData) {
			appName = application.getApplication();
			List<Node> appHostData= applicationService.getNodesByApplicationName(appName);
			//遍历应用的节点部署信息
			for (Node node : appHostData) {
				alarmServicePo.setServiceName(appName);
				alarmServicePo.setHost(node.getNodeAddress());
				alarmServiceStorage.addAlarmService(alarmServicePo);
			}
		}		
	}

}
