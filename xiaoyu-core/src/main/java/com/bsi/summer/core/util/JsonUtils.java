package com.bsi.summer.core.util;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;

/**
 * json格式处理类
 * @author 程亚东
 * Date:2013-3-15
 *
 */
public class JsonUtils {

	/**
	 * 发送json格式,不对date类型处理
	 * @param object
	 */
	public static void sendJson(Object object){
		sendJson(object,new String[]{});
	}
	
	/**
	 * 发送json格式,对date类型处理为 yyyy-MM-dd形式
	 * @param object
	 * @param strings
	 */
	public static void sendJson(Object object,String...strings){
		sendJson(object, "yyyy-MM-dd", strings);
	}
	/**
	 * 发送json格式,对date类型处理为 pattern形式
	 * @param object
	 * @param pattern
	 * @param strings
	 */
	public static void sendJson(Object object,String pattern,String...strings){
		HttpServletResponse response = ServletActionContext.getResponse();
		
		response.setContentType("text/json;charset=utf-8");
		try {
			PrintWriter out = response.getWriter();
			String json = "";
			JsonConfig config = new JsonConfig();  
			config.setExcludes(strings);  
            config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(pattern));
            
			if ( object instanceof Object[] || object  instanceof List )
				json = JSONArray.fromObject(object,config).toString();
			else 
				json = JSONObject.fromObject(object,config).toString();
			out.print(json);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String convertJson(Object o) {
		JsonConfig config = new JsonConfig();
		String json = "";
		if (o instanceof Object[] || o instanceof List)
			json = JSONArray.fromObject(o, config).toString();
		else
			json = JSONObject.fromObject(o, config).toString();
		return json;
	}

	@SuppressWarnings("unchecked")
	public  static <T> T jsonToBean(String json, Class<T> cls) {
		JSONObject obj = JSONObject.fromObject(json);
		T t = (T) JSONObject.toBean(obj, cls);
		return t;
	}
	
}
