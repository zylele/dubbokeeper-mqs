package com.dubboclub.dk.remote.esb.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.Assert;
import com.dubboclub.dk.remote.esb.biz.EsbComRpcBiz;
import com.dubboclub.dk.remote.esb.util.ParameterMappingUtils;

/**
 * Create on 2017/5/9
 *
 * @author chenweipu
 */
@Service("esbComService")
public class EsbComServiceImpl{
  private static final Logger LOGGER = LoggerFactory.getLogger(EsbComServiceImpl.class);

  @Autowired
  private EsbComRpcBiz esbComRpcBiz;

  /**
   * 入参继承EsbBaseBO
   * 返回值继承EsbBaseOutBO
   * 进行远程调用，包含加签和验签
   * @param req 输入实体
   * @param resType 输入出实体class
   * @param <T> 输出实体类型
   * @return 远程调用返回结果
   */
  public <T> T remoteCall(Object req, Class<T> resType) {
    Assert.notNull(req, "req is null");
    Assert.notNull(resType, "resType is null'");
    String requestPlain = ParameterMappingUtils.objToEsbJson(req);
    /*LOGGER.debug("Request plain is : {}", requestPlain);*/
   /* LOGGER.info(requestPlain);*/
    if(requestPlain.length()>4096){
    	LOGGER.info(requestPlain.substring(0, 4096)  + "...["+requestPlain.length()+"]");
	}else{
		LOGGER.info(requestPlain);
	}
    String responsePlain = esbComRpcBiz.sendMessage(requestPlain);
    /*LOGGER.debug("Response plain is : {}", responsePlain);*/
    /*LOGGER.info(responsePlain);*/
    if(responsePlain.length()>4096){
    	LOGGER.info(responsePlain.substring(0, 4096)  + "...["+responsePlain.length()+"]");
	}else{
		LOGGER.info(responsePlain);
	}
    return ParameterMappingUtils.esbJsonToObj(responsePlain, resType);
  }
  /**
   * 入参继承EsbBaseBO
   * 返回值继承EsbBaseOutBO
   * 进行远程调用，包含加签和验签
   * @param req 输入实体
   * @param resType 输入出实体class
   * @param <T> 输出实体类型
   * @return 远程调用返回结果
   */
  @Deprecated
  public <T> T remoteCall(Object req, Class<T> resType,String url) {
    Assert.notNull(req, "req is null");
    Assert.notNull(resType, "resType is null'");
    String requestPlain = ParameterMappingUtils.objToEsbJson(req);
    /*LOGGER.debug("Request plain is : {}", requestPlain);*/
    /*LOGGER.info(requestPlain);*/
    if(requestPlain.length()>4096){
    	LOGGER.info(requestPlain.substring(0, 4096)  + "...["+requestPlain.length()+"]");
	}else{
		LOGGER.info(requestPlain);
	}
    String responsePlain = esbComRpcBiz.sendMessage(requestPlain,url);
    /*LOGGER.debug("Response plain is : {}", responsePlain);*/
    /*LOGGER.info(responsePlain);*/
    if(responsePlain.length()>4096){
    	LOGGER.info(responsePlain.substring(0, 4096)  + "...["+responsePlain.length()+"]");
	}else{
		LOGGER.info(responsePlain);
	}
    return ParameterMappingUtils.esbJsonToObj(responsePlain, resType);
  }
  /**
   * 入参继承EsbBaseBO
   * 返回值继承EsbBaseOutBO
   * 进行远程调用，包含加签和验签 没有日志打印
   * @param req 输入实体
   * @param resType 输入出实体class
   * @param <T> 输出实体类型
   * @return 远程调用返回结果
   */
  public <T> T remoteCallnoLog(Object req, Class<T> resType,String url) {
    Assert.notNull(req, "req is null");
    Assert.notNull(resType, "resType is null'");
    String requestPlain = ParameterMappingUtils.objToEsbJson(req);
    /*LOGGER.debug("Request plain is : {}", requestPlain);*/
    /*LOGGER.info(requestPlain);*/
    String responsePlain = esbComRpcBiz.sendMessage(requestPlain,url);
    /*LOGGER.debug("Response plain is : {}", responsePlain);*/
    /*LOGGER.info(responsePlain);*/
    
    return ParameterMappingUtils.esbJsonToObj(responsePlain, resType);
  }

  /**
   * 入参继承EsbBaseReq
   * 返回值继承EsbBaseRes
   * 进行远程调用，包含加签和验签
   * @param req 输入实体
   * @param resType 输入出实体class
   * @param <T> 输出实体类型
   * @return 远程调用返回结果
   */
  public <T> T remoteCallNew(Object req, Class<T> resType) {
    Assert.notNull(req, "req is null");
    Assert.notNull(resType, "resType is null'");
    String requestPlain = ParameterMappingUtils.objToEsbJsonNew(req);
    LOGGER.debug("Request plain is : {}", requestPlain);
    /*LOGGER.info(requestPlain);*/
    if(requestPlain.length()>4096){
    	LOGGER.info(requestPlain.substring(0, 4096)  + "...["+requestPlain.length()+"]");
	}else{
		LOGGER.info(requestPlain);
	}
    String responsePlain = esbComRpcBiz.sendMessage(requestPlain);
    LOGGER.debug("Response plain is : {}", responsePlain);
    /*LOGGER.info(responsePlain);*/
    
    if(responsePlain.length()>4096){
    	LOGGER.info(responsePlain.substring(0, 4096)  + "...["+responsePlain.length()+"]");
	}else{
		LOGGER.info(responsePlain);
	}
    return ParameterMappingUtils.esbJsonToObjNew(responsePlain, resType);
  }
  
  /**
   * 入参继承EsbBaseReq
   * 返回值继承EsbBaseRes
   * 进行远程调用，包含加签和验签
   * @param req 输入实体
   * @param resType 输入出实体class
   * @param <T> 输出实体类型
   * @return 远程调用返回结果
   */
  public <T> T remoteCallNewnolog(Object req, Class<T> resType) {
    Assert.notNull(req, "req is null");
    Assert.notNull(resType, "resType is null'");
    String requestPlain = ParameterMappingUtils.objToEsbJsonNew(req);
    /*LOGGER.debug("Request plain is : {}", requestPlain);*/
    /*LOGGER.info(requestPlain);*/
    
    String responsePlain = esbComRpcBiz.sendMessage(requestPlain);
    /*LOGGER.debug("Response plain is : {}", responsePlain);*/
    /*LOGGER.info(responsePlain);*/
    
    
    return ParameterMappingUtils.esbJsonToObjNew(responsePlain, resType);
  }

  /**
   * 入参继承EsbBaseReq
   * 返回值继承EsbBaseRes
   * 进行远程调用，包含加签和验签
   * @param req 输入实体
   * @param resType 输入出实体class
   * @param url 远程调用的地址
   * @param <T> 输出实体类型
   * @return 远程调用返回结果
   */
  public <T> T remoteCallNew(Object req, Class<T> resType,String url) {
    Assert.notNull(req, "req is null");
    Assert.notNull(resType, "resType is null'");
    String requestPlain = ParameterMappingUtils.objToEsbJsonNew(req);
    /*LOGGER.debug("Request plain is : {}", requestPlain);*/
    if(requestPlain.length()>4096){
    	LOGGER.info(requestPlain.substring(0, 4096)  + "...["+requestPlain.length()+"]");
	}else{
		LOGGER.info(requestPlain);
	}
//    LOGGER.info(requestPlain);
    String responsePlain = esbComRpcBiz.sendMessage(requestPlain,url);
    /*LOGGER.debug("Response plain is : {}", responsePlain);*/
    /*LOGGER.info(responsePlain);*/
    
    if(responsePlain.length()>4096){
    	LOGGER.info(responsePlain.substring(0, 4096)  + "...["+responsePlain.length()+"]");
	}else{
		LOGGER.info(responsePlain);
	}
    return ParameterMappingUtils.esbJsonToObjNew(responsePlain, resType);
  }
  public <T> T remoteCallSzrUavFeedPiece(Object req,Class<T> resType, String url) {
	  Assert.notNull(req, "req is null");
//	  String requestPlain = JSON.toJSONString(req);
	  String requestPlain = ParameterMappingUtils.objToEsbJsonSjc(req);
	  /*LOGGER.debug("Request plain is : {}", requestPlain);*/
	  if(requestPlain.length()>4096){
		  LOGGER.info(requestPlain.substring(0, 4096)  + "...["+requestPlain.length()+"]");
	  }else{
		  LOGGER.info(requestPlain);
	  }
	  LOGGER.info(requestPlain);
	  String responsePlain = esbComRpcBiz.sendMessage(requestPlain,url);
	  /*LOGGER.debug("Response plain is : {}", responsePlain);*/
	  /*LOGGER.info(responsePlain);*/
	  
	  if(responsePlain.length()>4096){
		  LOGGER.info(responsePlain.substring(0, 4096)  + "...["+responsePlain.length()+"]");
	  }else{
		  LOGGER.info(responsePlain);
	  }
	  return ParameterMappingUtils.esbJsonToObjNew(responsePlain, resType);
  }
}
