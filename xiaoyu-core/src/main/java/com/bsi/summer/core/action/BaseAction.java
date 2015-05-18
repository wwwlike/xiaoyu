package com.bsi.summer.core.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import com.bsi.summer.cfg.service.SelectDetailService;
import com.bsi.summer.core.bean.Pager;
import com.bsi.summer.core.common.Const;
import com.bsi.summer.core.container.SessionContainer;
import com.bsi.summer.core.container.UserInfo;
import com.bsi.summer.core.util.AnnotationUtils;
import com.bsi.summer.core.util.JsonBinder;
import com.bsi.summer.core.util.ServletUtils;
import com.bsi.summer.core.util.StringUtils;
import com.bsi.summer.core.util.Struts2Utils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author dlc 所有action基类 提供相关方法
 */
public class BaseAction extends ActionSupport {
	private static final long serialVersionUID = 6718838811223344556L;
	protected static final String VIEW = "view";
	protected static final String LIST = "list";
	protected static final String STATUS = "status";
	protected static final String WARN = "warn";
	protected static final String FRAME = "frame";
	protected static final String SUCCESS = "success";
	protected static final String ERROR = "error";
	protected static final String INPUT = "input";
	protected static final String MESSAGE = "message";
	protected static final String CONTENT = "content";
	protected static final String REDITECT = "redirect";
	protected static final String INDEX="index";
	public String base =  getRequest().getContextPath();
	protected String message;
	//代包路径
	public String actionPath= getRequest().getRequestURI().substring(0, getRequest().getRequestURI().indexOf("!"));// action访问路径
	public String redirectPath;
	
	
	protected Pager pager;
	
	public UserInfo getCurrUser(){
		SessionContainer container=(SessionContainer)getSession().get(Const.SessionContainer);
		return container.getUser();
	}
	
	public void setUserInfo(UserInfo userInfo){
		SessionContainer container=(SessionContainer)getSession().get(Const.SessionContainer);
		container.setUser(userInfo);
	}
	
	@Autowired
	protected Struts2Utils struts2Utils;
	@Autowired
	protected ServletUtils servletUtils;
	@Autowired
	protected JsonBinder jsonBinder;
	@Autowired
	protected SelectDetailService selectDetailService;

	// 获取Attribute
	public Object getAttribute(String name) {
		return ServletActionContext.getRequest().getAttribute(name);
	}

	// 设置Attribute
	public void setAttribute(String name, Object value) {
		ServletActionContext.getRequest().setAttribute(name, value);
	}

	// 获取Parameter
	public String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	// 获取Parameter数组
	public String[] getParameterValues(String name) {
		return getRequest().getParameterValues(name);
	}

	// 获取Session
	public Object getSession(String name) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		return session.get(name);
	}

	// 获取Session
	public Map<String, Object> getSession() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		return session;
	}

	// 设置Session
	public void setSession(String name, Object value) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		session.put(name, value);
	}

	// 获取Request
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	// 获取Response
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	// 获取Application
	public ServletContext getApplication() {
		return ServletActionContext.getServletContext();
	}

	// AJAX输出，返回null
	public String ajax(String content, String type) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// AJAX输出文本，返回null
	public String ajaxText(String text) {
		return ajax(text, "text/plain");
	}
	
	// 输出JSON成功消息，返回null
	public String ajaxJsonSuccessMessage(String message,Object bean) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MESSAGE, message);
		jsonMap.put("bean", jsonBinder.toJson(bean));
		return ajax(jsonBinder.toJson(jsonMap), "text/html");
	}

	// AJAX输出HTML，返回null
	public String ajaxHtml(String html) {
		return ajax(html, "text/html");
	}

	// AJAX输出XML，返回null
	public String ajaxXml(String xml) {
		return ajax(xml, "text/xml");
	}

	// 根据字符串输出JSON，返回null
	public String ajaxJson(String jsonString) {
		return ajax(jsonString, "text/html");
	}

	// 根据Map输出JSON，返回null
	public String ajaxJson(Map<String, String> jsonMap) {

		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	// 输出JSON警告消息，返回null
	public String ajaxJsonWarnMessage(String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, WARN);
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	// 输出JSON成功消息，返回null
	public String ajaxJsonSuccessMessage(String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}
	

	// 输出JSON错误消息，返回null
	public String ajaxJsonErrorMessage(String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, ERROR);
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	// 设置页面不缓存
	public void setResponseNoCache() {
		getResponse().setHeader("progma", "no-cache");
		getResponse().setHeader("Cache-Control", "no-cache");
		getResponse().setHeader("Cache-Control", "no-store");
		getResponse().setDateHeader("Expires", 0);
	}

	/**
	 * 跳转到框架页面 菜单点击后跳转通常掉用此方法
	 */
	public String frame() {
		return result(FRAME);
	}

	/**
	 * 返回到当前action所对应的路径下 文件夹 首字母小写
	 * 
	 * @return
	 */
	public String result(String str) {
		String prefix = StringUtils.toLowerCaseFirstWord(getLastName(
				this.getClass().getName()).replace("Action", ""));
		prefix = prefix + "/" + prefix + "_" + str;
		return prefix;
	}

	/**
	 * 所属模块jsp
	 * @param str
	 * @return
	 */
	public String packageResultJsp(String str){
		String prefix = StringUtils.toLowerCaseFirstWord(getLastName(
				this.getClass().getName()).replace("Action", ""));
		String packageName=AnnotationUtils.getValue(AnnotationUtils.findAnnotation(this.getClass(),ParentPackage.class)).toString();
		
		prefix =packageName+"/"+ prefix + "/"+ prefix + "_" + str+".jsp";
		return prefix;
	}
	/*
	 * 获得bean Class Name
	 */
	public String getLastName(String str) {
		String[] array = str.split("\\.");
		int size = array.length;
		String prefix = array[size - 1].replace("Action", "");
		return prefix;

	}

	public String getBase() {
		return base;
	}

	/**
	 * result 到jsp跟页面
	 * 
	 * @param str
	 * @return
	 */
	public String jsp(String str) {
		return "../" + str;
	}
	
	/**
	 * result 到jsp跟页面
	 * 
	 * @param str
	 * @return
	 */
	public String common(String str) {
		return "/common/" + str;
	}

	public String cfg(String str) {
		return "../cfg/" + str;
	}
	
	public String einfo(String str) {
		return "../einfo/" + str;
	}
	
	public String jxw(String str) {
		return "../jxw/" + str;
	}

	public String sys(String str) {
		return "../sys/" + str;
	}

	public String exit() {
		return jsp("exit");
	}

	/**
	 * ----------------get and set------------------------
	 */
	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	//设置当前访问action 路径
	
	public String getActionPath() {
		return actionPath;
	}

	public void setActionPath(String actionPath) {
		this.actionPath = actionPath;
	}

	public String getRedirectPath() {
		return redirectPath;
	}

	public void setRedirectPath(String redirectPath) {
		this.redirectPath = redirectPath;
	}

	
	
	
}
