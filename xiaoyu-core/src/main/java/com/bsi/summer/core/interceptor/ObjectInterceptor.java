package com.bsi.summer.core.interceptor;

import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.bsi.summer.core.action.BaseAction;
import com.bsi.summer.core.item.ItemId;
import com.bsi.summer.core.util.ReflectionUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * CrudAction  Id 拦截器
 * 自动根据参数 转换成泛型类型
 */
public class ObjectInterceptor extends MethodFilterInterceptor {

	private static final Log LOG = LogFactory
			.getLog(ObjectInterceptor.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String doIntercept(ActionInvocation invocation) throws Exception {
		if (LOG.isInfoEnabled()) {
			LOG.info("invoke ObjectArrayInterceptor ... ");
		}
		Object action = invocation.getProxy().getAction();
		HttpServletRequest request = ServletActionContext.getRequest();
		ActionContext ac = invocation.getInvocationContext();
//		if(action.getClass().getSuperclass().equals(BaseAction.class))
//		{
		final Field field = action.getClass().getField("id");
		if (field!=null && field.getType().getSuperclass()==ItemId.class) {
				String name = field.getName();
				Class<?> Clazz = ReflectionUtils.getSuperClassGenricType(action.getClass(),2);
				Map<String, String> paramAndValues = new HashMap<String, String>();
				int size=setObjectParam(request, name, paramAndValues);
				if(size!=-1)
				{
				Object object = createObject(paramAndValues,Clazz);
				if (LOG.isInfoEnabled()) {
					LOG.info("the " + name + "'s value is" + object
							+ " in action [" + action + "]");
				}
				ValueStack stack = ac.getValueStack();
				stack.setValue(name, object);
				}
			}
//		}
		return invocation.invoke();
	}

	/**
	 * 设置参数
	 * @param request
	 * @param name
	 * @param paramAndValues
	 */
	private int setObjectParam(HttpServletRequest request, String name,
			Map<String, String> paramAndValues) {
		int size = -1;
		for (Enumeration<String> parameterNames = request.getParameterNames(); parameterNames
				.hasMoreElements();) {
			String parameterName = parameterNames.nextElement();
			if (parameterName.startsWith(name + ".")) {
				size++;
				String values = request.getParameter(parameterName);
				if (values != null ) {
					paramAndValues.put(parameterName
							.substring(name.length() + 1), values);
				}
			}
			else if(parameterName.equals(name)) 
			{
				size++;
				String values = request.getParameter(parameterName);
				if (values != null ) {
					paramAndValues.put(parameterName, values);
				}
			}
		}
		return size;
	}

	private Object createObject(final Map<String, String> paramAndValues,
			 Class<?> clazz) throws InstantiationException,
			IllegalAccessException {
		
		Object result =clazz.newInstance();
		for (Iterator<Entry<String, String>> it = paramAndValues.entrySet()
				.iterator(); it.hasNext();) {
			Entry<String, String> entry = it.next();
			String name = (String) entry.getKey();
			String values = entry.getValue();
			
				
				setValue(result, name, values);
			}
		return result;
	}

	private void setValue(Object object, String name, String value) {
		try {
			Field field = object.getClass().getDeclaredField(name);
			boolean isFoolback = false;
			if (field.isAccessible() == false) {
				isFoolback = true;
				field.setAccessible(true);
			}
			if (field.getType() == int.class) {
				field.setInt(object, Integer.parseInt(value));
			} else if (field.getType() == long.class) {
				field.setLong(object, Long.parseLong(value));
			} else if (field.getType() == float.class) {
				field.setFloat(object, Float.parseFloat(value));
			} else if (field.getType() == double.class) {
				field.setDouble(object, Double.parseDouble(value));
			} else if (field.getType() == short.class) {
				field.setShort(object, Short.parseShort(value));
			} else if (field.getType() == byte.class) {
				field.setByte(object, Byte.parseByte(value));
			} else if (field.getType() == boolean.class) {
				field.setBoolean(object, Boolean.parseBoolean(value));
			} else {
				field.set(object, value);// String
			}
			if (isFoolback) {
				field.setAccessible(false);
			}
		} catch (Exception e) {
			object=value;
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		
		
//		 field.getType().getSuperclass()==ItemId.class
		
	}
}