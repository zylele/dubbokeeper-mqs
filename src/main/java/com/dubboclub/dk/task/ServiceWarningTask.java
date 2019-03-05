package com.dubboclub.dk.task;

import java.util.List;

import com.dubboclub.dk.storage.model.AlarmServicePo;

/**  
	* @ClassName: ServiceWarningTask
	* @Description:检测服务异常接口类   
	* @author zhangpengfei  
	* @date 2019年3月4日   
	*/
public interface ServiceWarningTask {
	
	// 定时检查zookeeper中应用节点状态
	public void checkAppStatus();
	
}
