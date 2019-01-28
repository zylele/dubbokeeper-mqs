package com.dubboclub.dk.uiap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.zgcbank.uiap.ClientSsoVerifyToken;
import com.zgcbank.uiap.bean.SsoUser;

/**
 * @author jinxiaolei
 */
public class UiapAuthnProcessor extends HandlerInterceptorAdapter {	
	
	private static final long serialVersionUID = 1L;
	private String uiapUrl = ConfigUtils.getProperty("uiap.url");

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		// 后续拦截登录请求，暂不使用
		return true;
	}
}
