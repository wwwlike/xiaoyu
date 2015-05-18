package com.bsi.summer.core.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.bsi.summer.core.bean.Pager;
import com.bsi.summer.core.dao.PropertyFilter;
import com.bsi.summer.core.item.Item;
import com.bsi.summer.core.util.ServletUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ParamsFilterHandler extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		HttpServletRequest req = ServletActionContext.getRequest();
		// 获取action后附带参数
		Map<String, Object> parameters = invocation.getInvocationContext()
				.getParameters();
		Map<String, Object> param = ServletUtils.getParametersStartingWith(req,
				"filter_", false);
		Map<String, Object> paramOr = ServletUtils.getParametersStartingWith(
				req, "or_", false);
		Pager pager = (Pager) parameters.get("pager");
		Item bean =(Item) parameters.get("bean");
		if ((param != null && param.size() > 0)
				|| (paramOr != null && paramOr.size() > 0)) {
			pager = initParam(pager, req);
			
		}
//		if (req.getParameter("page")!=null&&req.getParameter("rows")!=null)
//		{
//			pager=setPagerParam(pager,req.getParameter("page"),req.getParameter("rows"));
//		}
		if(pager!=null){
		invocation.getInvocationContext().getParameters().put("pager",
				pager);
		}

			return invocation.invoke();
	}

	/**
	 * 查询参数当顶到pager里面去
	 * @param pager
	 * @param req
	 * @return
	 */
	public Pager initParam(Pager pager, HttpServletRequest req) {
		if (pager == null)
			pager = new Pager();

		// 设置and过滤
		pager.setAndFilters(PropertyFilter.buildFromHttpRequest(req));
		// 设置or过滤
		pager.setOrFilters(PropertyFilter.buildFromHttpRequest(req, "or"));
		
		return pager;
	}

	/**
	 * easyui 的参数转到pager对应的pagesize pageNumber去
	 * @param pager
	 * @param num
	 * @param size
	 * @return
	 */
	public Pager setPagerParam(Pager pager, String num, String size) {
		if (pager == null)
			pager = new Pager();
		pager.setPageNumber(Integer.parseInt(num));
		pager.setPageSize(Integer.parseInt(size));
		return pager;
	}

}
