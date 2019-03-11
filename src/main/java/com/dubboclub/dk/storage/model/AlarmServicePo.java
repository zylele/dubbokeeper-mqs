package com.dubboclub.dk.storage.model;

    /**  
    * @ClassName: AlarmServicePo  
    * @Description: alarm_service表实体
    * @author jinxiaolei 
    * @date 2019年3月5日   
    */  
public class AlarmServicePo {
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
	
	// 因ServiceWarningTaskImpl中以对象元素作为key值做判断重写方法
	public boolean equals(Object o){
		if (o == null) {
			return false;
		}
		if (this == o) {
			return true;
		}
		if (o instanceof AlarmServicePo) {
			AlarmServicePo p = (AlarmServicePo) o;
			if (p.getHost().equals(this.getHost()) && p.getServiceName().equals(this.getServiceName())) {
				return true;
			}else {
				return false;
			}
		}
		return false;
	}
	
	// 因ServiceWarningTaskImpl中以对象元素作为key值做判断重写方法
	public int hashCode(){
		int result = 17;
		result = result * 37 + host.hashCode();
		result = result * 37 + serviceName.hashCode();
		return result;
	}

}
