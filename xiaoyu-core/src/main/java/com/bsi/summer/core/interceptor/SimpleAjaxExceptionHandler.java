package com.bsi.summer.core.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;

import com.bsi.summer.core.exception.BaseException;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 
 * @author : Bless
 * @version : 1.00
 * Create Time : 2011-3-23-上午10:25:23 
 * Description : 达运 异常拦截器 (针对Json数据的Ajax验证)
 * History     ： Editor  version Time Operation Description*
 * 
 */
public class SimpleAjaxExceptionHandler extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;

	public String intercept(ActionInvocation actioninvocation) {

		String result = null; // Action的返回值
		try {
			// 运行被拦截的Action,期间如果发生异常会被catch住
			result = actioninvocation.invoke();
			return result;
		} catch (Exception e) {
			/**
			 * 处理异常
			 */
			String errorMsg = "未知错误！";

			if (e instanceof BaseException) {
				// OSPM异常
				BaseException be = (BaseException) e;
				be.printStackTrace();
				if(be.getMessage()!=null||"".equals(be.getMessage().trim())){
					errorMsg = be.getMessage().trim();
				}
			} else if(e instanceof RuntimeException){
				//未知的运行时异常
				RuntimeException re = (RuntimeException)e;
				re.printStackTrace();
			} else if(e instanceof IOException){
				//IO输入输出流异常
				IOException ie = (IOException)e;
				ie.printStackTrace();
			}else{
				//未知的严重异常
				e.printStackTrace();
			}
			
			/**
			 * 读取文件，获取对应错误消息
			 */
			HttpServletResponse response = (HttpServletResponse)actioninvocation.getInvocationContext().get(StrutsStatics.HTTP_RESPONSE);
			response.setCharacterEncoding("UTF-8");
			
			
			/**
			 * 发送错误消息到页面
			 */
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(errorMsg);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
			
			/**
			 * log4j记录日志
			 */
			Log log = LogFactory
					.getLog(actioninvocation.getAction().getClass());
			if (e.getCause() != null){
				log.error(errorMsg, e);
			}else{
				log.error(errorMsg, e);
			}

			return null;
		}// ...end of catch
	}
}
