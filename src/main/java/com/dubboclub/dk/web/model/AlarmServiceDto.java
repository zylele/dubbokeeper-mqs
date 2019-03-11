package com.dubboclub.dk.web.model;

/**  
* @ClassName: AlarmServiceDto  
* @Description:   
* @author zhangpengfei  
* @date 2019年3月11日   
*/
public class AlarmServiceDto {

	private String host;
	private String serviceName;
	private String serviceType;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}



}
