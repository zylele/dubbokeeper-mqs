package com.dubboclub.dk.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.zgcbank.uiap.bean.SsoUser;

/**
 * @ClassName: TradeInterceptor
 * @Description:操作人员行为日志打印及登录拦截 
 * @author jinxiaolei
 * @date 2019年2月14日
 */
public class TradeInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger log = LoggerFactory.getLogger(TradeInterceptor.class);
	SsoUser user=null;
		
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		HttpSession session = request.getSession();
		user = (SsoUser) session.getAttribute("user");
		if(session.getAttribute("user")!=null){		
			log.info("*********************************************");
			log.info("操作人员： "+user.getIdmUsrName()+" ID:"+user.getIdmUsrId());
			log.info("调用方法： "+request.getRequestURI());
			log.info("*********************************************");
			
		}else{
			response.sendRedirect("10.4.0.34:8080/uiap/login");
		}
		
		return true;
	}
}
