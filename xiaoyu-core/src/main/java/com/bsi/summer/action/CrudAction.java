package com.bsi.summer.action;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.bsi.summer.cfg.entity.OptLog;
import com.bsi.summer.core.annotation.OptTypeEnum;
import com.bsi.summer.core.annotation.UserOperateLog;
import com.bsi.summer.core.bean.EasyUiPager;
import com.bsi.summer.core.bean.Pager;
import com.bsi.summer.core.dao.PropertyFilter;
import com.bsi.summer.core.item.Item;
import com.bsi.summer.core.service.BaseService;

/**
 * @author dlc
 * @date 2012-3-7
 * @category 增删改查 基于泛型的Action基类
 */
@Results(
		value={
				@Result(name="list",location="/${info.stable.modelPath}!list.action", type = "redirect"),
				@Result(name="redirect",location="${redirectPath}", params = {"bean.id", "${bean.id}"}, type = "redirect")
	}
)
public  class CrudAction<T extends Item<PK>, S extends BaseService<T, PK>,PK extends Serializable>
		extends BaseCrudAction<T,S,PK>  {
	private static final long serialVersionUID = 1218548589714255779L;
	public String index(){
		return cfg("index");
	}
	
	List<PropertyFilter> filters=null;
	public String list() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		if(pager==null) pager=new Pager();
		
		filters=pager.getAndFilters();
		pager.setAndFilters(ActionUtils.beanToPropertyFilter(filters,bean));
		listCommon();
		setJsonInfo();
		return jsp(LIST);
		
//		return result(LIST);
	}
	
	
	public String grid() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		list();
		return cfg("grid");
		
		
	}
	/**
	 * 删除一条记录后返回到list
	 * @return
	 */
	public String deleteBackList(){
		if(id!=null)
		service.delete(id);
		else if(bean!=null&&bean.getId()!=null){
			service.delete(bean);
		}
		return LIST;
	}
	
	/**
	 * 复杂表管理list页面 左右布局 左边是父
	 */
	public String relationList(){
		listCommon();
		return jsp("relation_list");
	}
	
	/**
	 * 为jquery.summerGrid.js 服务的异步方法
	 * @return
	 */
	public String ajaxGridList(){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if(pager==null) 
			pager=new Pager();
		filters=new ArrayList<PropertyFilter>();
		pager.setAndFilters(ActionUtils.beanToPropertyFilter(filters,bean));
		pager=service.findByPager(pager);

		jsonMap.put("info", info);//表信息
		jsonMap.put("pager",pager);//分页数据信息
		return ajax(jsonBinder.toJson(jsonMap), "json");
	}
	
	
	/**
	 * 加载表记录为select 或者checkbox 服务，并返回该表的 表信息
	 * @return
	 */
	public String ajaxFormData(){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if(this.info==null){
				return ERROR;
		}
		
		if(pager==null) 
			pager=new Pager();
		filters=new ArrayList<PropertyFilter>();
		pager.setAndFilters(ActionUtils.beanToPropertyFilter(filters,bean));
		service.findByPager(pager);
		jsonMap.put("data", pager.getList());//表信息
		jsonMap.put("fields",this.info.getFields());//分页数据信息
		
		return ajax(jsonBinder.toJson(jsonMap), "json");
	}
	

	public String add() {
		return result(INPUT);
	}

	public String edit() {
		if(this.editEntity())
			return result(INPUT);
		else
			return ERROR;
	}
	
	public String input(){
		setJsonInfo();
		if(bean!=null&&bean.getId()!=null){
			setAttribute("beanData",jsonBinder.toJson(service.get(bean.getId())));//数据信息
		}
		return result(INPUT);
	}

	//@UserOperateLog(optType=OptTypeEnum.SAVE)
	public String save() {
		if (bean == null) {
			return ERROR;
		}
		ActionUtils.delSubByNUll(bean);//去除保存过程中 为空的关联item
		if(bean.getId()!=null)
			service.update(bean);
		else
			service.save(bean);
	
		setJsonInfo();
		setAttribute("beanData",jsonBinder.toJson(bean));//数据信息
		setMessage("保存成功");
		
		return  result(INPUT);
	}
	
	//@UserOperateLog(optType=OptTypeEnum.SAVE)
	public String ajaxSave(){
		save();
		return ajaxJsonSuccessMessage("保存成功！",bean);
	}

	public String update() {
		if (bean == null||bean.getId()==null) {
			return ERROR;
		}
		service.update(bean);
		return result(INPUT);
	}
	
	/**
	 *easyui 异步用到的list
	 * @return
	 */
	public String ajaxList(){
		Pager re=service.findByPager(pager);
		Map<String,Object> map=new HashMap<String,Object>();
		EasyUiPager ui=new EasyUiPager();
		ui.setRows(re.getList());
		ui.setTotal(re.getTotalCount());
		map.put("rows",re.getList());
		map.put("total",re.getTotalCount());
		return ajax(jsonBinder.toJson(ui), "text/html");
	}
	
	public String button(){
		return common("button");
	}

	public String delete() throws Exception {
		if(this.deleteEntity())
		return ajaxJsonSuccessMessage("删除成功！");
		else
		return ajaxJsonSuccessMessage("删除失败！");	
	}

	
	@Override
	public OptLog getOptLog() {
		optLog=new OptLog();
		optLog.setTableName(this.info.getStable().getTableName());
		return optLog;
	}

	@Override
	public T getModel() {
		return bean;
	}
}