package com.bsi.summer.core.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;

import com.bsi.summer.core.bean.Message;
import com.bsi.summer.core.exception.BaseException;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * @author : donglc
 * @version : 1.00 
 * Create Time : 2011-3-23-上午10:25:23 
 * Description : 异常拦截器 (针对Json数据的Ajax验证)
 * History     ： Editor  version Time Operation Description*
 */
public class JsonAjaxExceptionHandler extends MethodFilterInterceptor {
	private static final long serialVersionUID = 1L;

	
	// 输出JSON错误消息，返回null
	public void ajaxJsonErrorMessage(String message,String status) {
		JSONObject jsonObject = JSONObject.fromObject(new Message(message,status));
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html" + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(jsonObject.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String doIntercept(ActionInvocation actioninvocation) throws Exception {

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
			} else{
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
				ajaxJsonErrorMessage(errorMsg,"error");
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
