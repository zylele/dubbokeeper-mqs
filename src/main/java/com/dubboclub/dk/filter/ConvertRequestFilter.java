package com.dubboclub.dk.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dubboclub.dk.filter.model.ResettableStreamRequestWrapper;
import com.zgcbank.uiap.bean.SsoUser;


/** 
 * @ClassName: ConvertRequestFilter 
 * @Description: request转换及操作行为记录
 * @author jinxiaolei 
 * @date 2019年3月14日 
 */
public class ConvertRequestFilter implements Filter {
	
	private static final Logger log = LoggerFactory.getLogger(ConvertRequestFilter.class);	
	SsoUser user = null;	
    private String excludedUrl;   
    private String[] excludedUrls;
    	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// ignores为web.xml定义的不过滤url
		excludedUrl = filterConfig.getInitParameter("ignores");
		if (excludedUrl != null && excludedUrl.length() > 0) {
			excludedUrls = excludedUrl.split(",");
		}
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		ResettableStreamRequestWrapper requestWrapper = new ResettableStreamRequestWrapper((HttpServletRequest) request);
		
		// 验证用户请求url 是否包含不过滤url
		for (String url : excludedUrls) {
			if (requestWrapper.getRequestURI().contains(url)) {
				chain.doFilter(requestWrapper, response);
				return ;
			}
		}
		
		// 验证用户登录身份
		user = (SsoUser)requestWrapper.getSession().getAttribute("user");
		if (null!=user) {	
			
			// 记录操作人员信息及request请求
			log.info("-----------------------------------------request---------------------------------------------");
			log.info("operator： " + user.getIdmUsrName() + ",ID:" + user.getIdmUsrId());
			
			String headerData = getHeader(requestWrapper);
			if(headerData!=null&&!headerData.equals("")) {
				log.info("request-header:"+headerData);
			}
			String formData = getFormdata(requestWrapper);
			if(formData!=null&&headerData!="") {
				log.info("request-formdate:"+formData);
			}
			String payloadData = getPayload(requestWrapper);
			if(payloadData!=null&&!headerData.equals("")) {
				log.info("request-payload:"+payloadData);
			}
			log.info("---------------------------------------------------------------------------------------------");
			
			// reader流读取后数据重新放回requestWrapper
			requestWrapper.setBodyData(payloadData.getBytes());
			requestWrapper.resetInputStream();
			
			chain.doFilter(requestWrapper, response);
		}else {
			// 身份验证失败，重新登陆
			requestWrapper.getRequestDispatcher("templates/error/404err.html").forward(requestWrapper, response);
		}	
	}
	
	@Override
	public void destroy() {}
	
	
	/**
	 * request-header
	 */
	static String getHeader(ResettableStreamRequestWrapper request) {
		Enumeration headers = request.getHeaderNames();
		StringBuilder sheader = new StringBuilder();
		while (headers.hasMoreElements()) {
			String headerName = (String) headers.nextElement();
			sheader.append("[" + headerName + ":" + request.getHeader(headerName) + "]");
		}
		return sheader.toString();
	}

	
	/**
	 * request-body/form_data
	 */
	static String getFormdata(ResettableStreamRequestWrapper request) {
		Enumeration params = request.getParameterNames();
		StringBuilder sform = new StringBuilder();
		while (params.hasMoreElements()) {
			String paramName = (String) params.nextElement();
			sform.append("[" + paramName + ":" + request.getParameter(paramName) + "]");
		}
		return sform.toString();
	}
	
	
	/**
	 * request-body/request_payload
	 * @throws IOException 
	 */
	static String getPayload(ResettableStreamRequestWrapper request) throws IOException{
        BufferedReader br = request.getReader();
		StringBuilder sload = new StringBuilder();
        String line ;
        try {
            while ((line = br.readLine()) != null) {
            	sload.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        br.close();
        return sload.toString();	
	}
}
