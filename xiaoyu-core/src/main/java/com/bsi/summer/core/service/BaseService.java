package com.bsi.summer.core.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.bsi.summer.core.annotation.UserOperateLog;
import com.bsi.summer.core.bean.Pager;
import com.bsi.summer.core.item.Item;

/**
 * Service接口 - Service接口基类
 * @author Administrator
 *
 * @param <T>
 * @param <PK>
 */

public interface BaseService<T extends Item<PK>, PK extends Serializable> {

	/**
	 * 根据ID获取实体对象.
	 * 
	 * @param id
	 *            记录ID
	 * @return 实体对象
	 */
	public T get(PK id);

	/**
	 * 根据ID获取实体对象.
	 * 
	 * @param id
	 *            记录ID
	 * @return 实体对象
	 */
	public T load(PK id);
	
	/**
	 * 根据ID数组获取实体对象集合.
	 * 
	 * @param ids
	 *            ID对象数组
	 * 
	 * @return 实体对象集合
	 */
	public List<T> get(PK[] ids);
	
	/**
	 * 根据属性名和属性值获取实体对象.
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 * @return 实体对象
	 */
	public T get(String propertyName, Object value);
	
	
	/**
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
//	public Item get(Class<Item> clazz,PK id);
	public Item<PK> get(Class<Item<PK>> clazz,PK id);

	

	/**
	 * 根据属性名和属性值获取实体对象集合.
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 * @return 实体对象集合
	 */
	public List<T> getList(String propertyName, Object value);

	/**
	 * 获取所有实体对象集合.
	 * 
	 * @return 实体对象集合
	 */
	public List<T> getAll();
	
	
	/**
	 * 获取所有实体对象集合.
	 * 
	 * @return 实体对象集合
	 */
	public List<T> getAll(Pager pager);
	
	/**
	 * 获取所有实体对象总数.
	 * 
	 * @return 实体对象总数
	 */
	public Long getTotalCount();

	/**
	 * 根据属性名、修改前后属性值判断在数据库中是否唯一(若新修改的值与原来值相等则直接返回true).
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param oldValue
	 *            修改前的属性值
	 * @param oldValue
	 *            修改后的属性值
	 * @return boolean
	 */
	public boolean isUnique(String propertyName, Object oldValue, Object newValue);
	
	/**
	 * 根据属性名判断数据是否已存在.
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            值
	 * @return boolean
	 */
	public boolean isExist(String propertyName, Object value);

	/**
	 * 保存实体对象.
	 * 
	 * @param entity
	 *            对象
	 * @return ID
	 */
	public PK save(T entity);

	/**
	 * 更新实体对象.
	 * 
	 * @param entity
	 *            对象
	 */
	public void update(T entity);

	/**
	 * 删除实体对象.
	 * 
	 * @param entity
	 *            对象
	 * @return
	 */
	public void delete(T entity);

	/**
	 * 根据ID删除实体对象.
	 * 
	 * @param id
	 *            记录ID
	 */
	public void delete(PK id);

	/**
	 * 根据ID数组删除实体对象.
	 * 
	 * @param ids
	 *            ID数组
	 */
	public void delete(PK[] ids);
	
	
	/**
	 * 根据List数组删除实体对象.
	 * 
	 * @param beans
	 */
	public void delete(List<T> beans);
	
	/**
	 * 刷新session.
	 * 
	 */
	public void flush();

	/**
	 * 清除Session.
	 * 
	 */
	public void clear();
	
	/**
	 * 清除某一对象.
	 * 
	 * @param object
	 *            需要清除的对象
	 */
	public void evict(Object object);

	/**
	 * 根据Page对象进行查询(提供分页、查找、排序功能).
	 * 
	 * @param page
	 *            Page对象
	 * @return Page对象
	 */
	
	/**
	 * 根据Page对象进行查询(提供分页、查找、排序功能).
	 * 
	 * @param page
	 *            Page对象
	 * @return Page对象
	 */
	public Pager findByPager(Pager pager);
	
	
	/**
	 * 根据Pager和Class<T>对象进行查询(提供分页、查找、排序功能).
	 * 
	 * @param pager
	 *            Pager对象
	 * @return Pager对象
	 */
	public Pager findByPager(Pager pager, Class<T> clazz);
	
	/**
	 * 根据Pager和DetachedCriteria对象进行查询(提供分页、查找、排序功能).
	 * 
	 * @param pager
	 *            	Pager对象
	 * @return Pager对象
	 */
	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria);
	
	
	/**
	 * 根据Pager和Bean实体(提供分页、查找、排序功能).
	 * 
	 * @param pager
	 *            	Pager对象
	 * @return Pager对象
	 */
	public Pager findByPager(Pager pager, T bean) ;
	
	/**
	 * hql分页
	 * @param pager
	 * @return
	 */
	public Pager findByHql(Pager pager);
	
	
	/**
	 * hql查询
	 * @param pager
	 * @return
	 */
	public List<T> findByHql(String hql);
	

	/**
	 * hql查询防注入
	 * @param pager
	 * @return
	 */
	public List<T> findByHql(String hql,Object ...args);
	
	
	public Pager findByHql(Pager pager,String hql,Object ...args);
	
	/**
	 * 根据ID删除实体对象. 根据字段信息 该级联清空的需要级联清空
	 * 
	 * @param id
	 *            记录ID
	public void delete(PK id,Field field); */
	
	public List<T> find(String hql,int max,Object...args);
	
	public void  saveItems(List<T> items);

}
