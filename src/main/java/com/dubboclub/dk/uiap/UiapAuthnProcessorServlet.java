package com.dubboclub.dk.uiap;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static final Logger log = LoggerFactory.getLogger(UiapAuthnProcessorServlet.class);
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
		if(falg){	
			// 将用户信息存储到session中
			request.getSession().setAttribute("user",user);
			//打印用户信息
			log.info("==========================================================================================");
			log.info("login-operator:"+user.getIdmUsrName()+",ID:"+user.getIdmUsrId());
			log.info("==========================================================================================");
			request.getRequestDispatcher("index.htm").forward(request, response);
		}else{						
			// 验证失败，返登录页
			response.sendRedirect("http://10.4.0.34:8080/uiap/login");
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
