package com.dubboclub.dk.remote.esb.biz;

import java.net.SocketTimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.dubboclub.dk.remote.common.BizException;
import com.dubboclub.dk.remote.common.ErrCode;

/**
 * Create on 2017/5/9
 *
 * @author chenweipu
 */
@Component("esbComRpcBiz")
public class EsbComRpcBiz {
  private static final Logger LOGGER = LoggerFactory.getLogger(EsbComRpcBiz.class);

  /**
   * 与esb通信，发送http请求
   * @param message json string报文
   * @return 返回报文
   */
  public String sendMessage(String message){
    return sendMessage(message, ConfigUtils.getProperty("esb.core.url"));
  }
  /**
   * 与esb通信，发送http请求
   * @param message json string报文
   * @return 返回报文
   */
  public String sendMessage(String message,String url){
    HttpHeaders headers = new HttpHeaders();
    MediaType type = MediaType.parseMediaType(ConfigUtils.getProperty("esb.core.type"));
    headers.setContentType(type);
    HttpEntity<String> formEntity = new HttpEntity<>(message, headers);
    String jsonStr = null;
    try {
      RestTemplate restTemplate = new RestTemplate();
	jsonStr = restTemplate .postForObject(url, formEntity, String.class);
    }catch (ResourceAccessException e) {
    	Throwable cause = e.getCause();
    	if ((cause instanceof SocketTimeoutException)
    			|| cause.getMessage().contains("timed out")) {
    		//可重试
    		LOGGER.error("请求超时");
    		throw new BizException(ErrCode.TC038.code(),ErrCode.TC038.desc(), e);
    	}else {
    		//不可重试
    		LOGGER.error("指定服务不存在,检查ip、port、服务等要素");
    		throw new BizException(ErrCode.TC037.code(),ErrCode.TC037.desc(), e);
    	}
    }catch (HttpStatusCodeException e){
    	//不可重试
    	LOGGER.error("http 状态码:"+e.getStatusCode());
    	throw new BizException(ErrCode.TC037.code(),ErrCode.TC037.desc(), e);
    }catch (RestClientException e) {
      //不可重试
      LOGGER.error("预料之外的报文返回类型");
      throw new BizException(ErrCode.TC037.code(),ErrCode.TC037.desc(), e);
    }
    if (jsonStr == null) {
      LOGGER.error("通信返回值为空");
      throw new BizException(ErrCode.SS009.code(),ErrCode.SS009.desc());
    }
    return jsonStr;
  }
}
