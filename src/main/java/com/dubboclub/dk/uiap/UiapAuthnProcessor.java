package com.dubboclub.dk.uiap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.zgcbank.uiap.ClientSsoVerifyToken;
import com.zgcbank.uiap.bean.SsoUser;

public class UiapAuthnProcessor extends HandlerInterceptorAdapter {	
	
	private static final long serialVersionUID = 1L;
	
	// 获取uiap的url
	private String uiapUrl = ConfigUtils.getProperty("uiap.url");
		
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		// token验证结果
		boolean falg = false;

		// 请求登录用户对象
		SsoUser user = null;
		try {
			// 获取用户信息user
			user = ClientSsoVerifyToken.analysisParameter(request);

			// 验证token
			falg = ClientSsoVerifyToken.verifyToken(request, uiapUrl);

		} catch (Exception e) {
			// 下游自行处理异常
			e.printStackTrace();
		}

		// 判断token验证结果
		if (falg) {
			// 验证成功，进入主页
			request.getRequestDispatcher("views/main.jsp").forward(request, response);
			return true;
		} else {
			// 验证失败，返回登录页面
			response.sendRedirect("views/login.jsp");
			return false;
		}
	}
}
