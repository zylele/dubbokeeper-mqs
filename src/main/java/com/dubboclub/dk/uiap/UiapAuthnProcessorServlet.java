package com.dubboclub.dk.uiap;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.zgcbank.uiap.ClientSsoVerifyToken;
import com.zgcbank.uiap.bean.SsoUser;

/**
 * @ClassName: UiapAuthnProcessorServlet
 * @Description: uiap单点登录请求servlet
 * @author jinxiaolei
 * @date 2019年1月28日
 */
public class UiapAuthnProcessorServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private String uiapUrl = ConfigUtils.getProperty("uiap.url");

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean falg = false;
		SsoUser user=null;
		
		try {
			user=ClientSsoVerifyToken.analysisParameter(request);		// 获取用户信息						
			falg=ClientSsoVerifyToken.verifyToken(request,uiapUrl);		// 验证token
		} catch (Exception e) {
			e.printStackTrace();
		}			
		if(falg){	// 验证成功，进入主页
			request.getRequestDispatcher("index.htm").forward(request, response);
		}else{		// 验证失败，返登录页				
			response.sendRedirect("forward.jsp");
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
