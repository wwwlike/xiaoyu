package com.bsi.summer.action;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.bsi.summer.cfg.bean.ListPage;
import com.bsi.summer.cfg.bean.TableInfo;
import com.bsi.summer.cfg.entity.Field;
import com.bsi.summer.cfg.entity.OptLog;
import com.bsi.summer.cfg.entity.Stable;
import com.bsi.summer.cfg.listener.init.StableInfoInit;
import com.bsi.summer.cfg.util.ActionMessage;
import com.bsi.summer.cfg.util.DeleteEntityUtils;
import com.bsi.summer.core.action.BaseAction;
import com.bsi.summer.core.bean.Pager;
import com.bsi.summer.core.entity.ILogable;
import com.bsi.summer.core.item.Item;
import com.bsi.summer.core.service.BaseService;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * @author dlc
 * @date 2012-3-7
 * @category 增删改 基于泛型的BaseCrudAction基类  不予具体业务，具体页面发生耦合
 */
@SuppressWarnings("serial")
public abstract class BaseCrudAction<T extends Item<PK>, S extends BaseService<T, PK>, PK extends Serializable>
		extends BaseAction implements ModelDriven<T>,Preparable ,ILogable {
	public PK[] ids;
	public T[] beans;
	protected T bean = null;
	protected S service = null;
	protected Stable stable;
	protected List<Field> fields;
	protected Class<T> clazz;
	public TableInfo info = null;
	public ListPage listPage;
	protected OptLog optLog;
	public PK id;//实体类ID 

	/**
	 * 根据泛型类型实例化对象
	 * 
	 * @return 泛型类型的对象
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public T instanceAnnotationObject() throws Exception {
		try {
		Class<?> cl = this.getClass();
		Type[] types = ((ParameterizedType) cl.getGenericSuperclass())
				.getActualTypeArguments();
			return ((Class<T>) types[0]).newInstance();
		} catch (Exception e) {
			return (T) this.getModel().getClass().newInstance();
		}
	}

	public void prepare() throws Exception {
		if(pager==null){
			this.pager=new Pager(); 
		}
		this.bean = this.instanceAnnotationObject(); //泛型无法set进来，在准备的时候实例化 则可以set
		this.clazz = (Class<T>) bean.getClass();
		this.info = StableInfoInit.getTableInfo().get(clazz.getName());// 当前表信息
		listPage=new ListPage();//=?= 这样实例化不好,稍后改为注入
	}

	public TableInfo getInfo() {
		return info;
	}

	public T getBean() {
		return bean;
	}

	protected S getService() {
		return service;
	}

	protected void setBean(T bean) {
		this.bean = bean;
	}

	protected void setService(S service) {
		this.service = service;
	}

	public PK[] getIds() {
		return ids;
	}

	public void setIds(PK[] ids) {
		this.ids = ids;
	}

	public boolean editEntity() {
		bean = service.load(bean.getId());
		return true;
	}

	public boolean saveEntity() {
		if (bean == null) {
			return false;
		}
		service.save(bean);
		return true;
	}

	public boolean updateEntity() {
		if (bean == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * list 首页 页面 公用查询
	 * @param tableid
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws ClassNotFoundException
	 */
	public void listCommon(){
		pager = service.findByPager(pager,getModel());
	}

	public void setJsonInfo(){
		if(info!=null&&info.getStable()!=null){
			setAttribute("fieldsJson", jsonBinder.toJson(info.getFields()));//字段信息
			setAttribute("fkTableJson", jsonBinder.toJson(info.getStableFKs()));//外键表信息
		}
	}
	
	public boolean deleteEntity() throws Exception {
		if (ids == null && beans != null) {
			// 不能创建泛型数组，只能利用反射来创建
			ids = (PK[]) Array.newInstance(beans[0].getId().getClass(),
					beans.length);
			List<PK> pks = new ArrayList<PK>();
			for (T t : beans) {
				pks.add(t.getId());
			}
			pks.toArray(ids);
		}
		ActionMessage message = new DeleteEntityUtils<PK>(service).canDel(info,ids);
		if (ids != null) {
			service.delete(ids);
		} else {
			return false;
		}
		return true;
	}
	
	public String deleteAjax() throws Exception{
		this.deleteEntity();
		return ajaxJsonSuccessMessage("删除成功！");
	}
	
	public String delete() throws Exception {
		if(this.deleteEntity())
		return ajaxJsonSuccessMessage("删除成功！");
		else
		return ajaxJsonSuccessMessage("删除失败！");	
	}
	

	/**
	 * 获取一条数据byId
	 * @return
	 */
	public String ajaxById(){
		return ajax(jsonBinder.toJson(service.get(bean.getId())), "json");
	}
	/**
	 * 获取一条数据byId
	 * @return
	 */
	public String ajaxAll(){
		return ajax(jsonBinder.toJson(service.getAll()), "json");
	}
	public T[] getBeans() {
		return beans;
	}
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	public ListPage getListPage() {
		return listPage;
	}
	public void setBeans(T[] beans) {
		this.beans = beans;
	}
	public PK getId() {
		return id;
	}
	public void setId(PK id) {
		this.id = id;
	}
	
}