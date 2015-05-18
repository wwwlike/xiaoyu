package com.bsi.summer.core.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;

import com.bsi.summer.action.ActionUtils;
import com.bsi.summer.core.bean.Pager;
import com.bsi.summer.core.item.Item;

/**
 * Dao接口 - Dao基接口
 * @author Administrator
 *
 * @param <T>
 * @param <PK>
 */

public interface BaseDao<T extends Item<PK>, PK extends Serializable> {
	
	/**
	 * 根据实体bean 转换成filters 后查询指定数量结果集
	 * @param temp
	 * @return
	 */
	public List<T> findListByFilters(List<PropertyFilter> temp,int max);
	
	/**
	 * 根据实体bean 转换成filters 查询所有结果集
	 * @param temp
	 * @return
	 */
	public List<T> findListByFilters(List<PropertyFilter> temp);
	/**
	 * 实体对象转换成查询条件 后 返回 查询结果集
	 * @param t
	 * @return
	 */
	public List<T> findListByItem(T t) ;
	
	/**
	 * 实体对象转换成查询条件 后 返回指定数量结果集
	 * @param t
	 * @return
	 */
	public List<T> findListByItem(T t,int max) ;
	

	/**
	 * 根据ID获取实体对象.
	 * 
	 * @param id
	 *            记录ID
	 * @return 实体对象
	 */
	public T get(PK id);
	
	
	/**
	 * 根据ID获取指定实体对象.
	 * 
	 * @param id
	 *            记录ID
	 * @return 实体对象
	 */
	public Item<PK> get(Class<Item<PK>> clazz,PK id) ;

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
	 * 根据属性名和属性值获取指定实体对象.
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 * @return 实体对象
	 */
	public Item get(Class clazz,String propertyName, Object value);
	
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
	 * 待排序
	 * @param propertyName
	 * @param value
	 * @param orderby
	 * @param orderType
	 * @return
	 */
	public List<T> getList(String propertyName, Object value,String orderby ,String orderType);

	/**
	 * 获取所有实体对象集合.
	 * 
	 * @return 实体对象集合
	 */
	public List<T> getAll();
	
	
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
	 * 保存或者更新
	 * @param entity
	 */
	public void saveOrupdate(T entity) ;

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
	 * 根据Pager对象进行查询(提供分页、查找、排序功能).
	 * 
	 * @param pager
	 *            Pager对象
	 * @return Pager对象
	 */
	public Pager findByPager(Pager pager);
	
	/**
	 * 根据bean数据分页查询
	 * @param pager
	 * @param bean
	 * @return
	 */
	public Pager findByPager(Pager pager, T bean);
	
	/**
	 * 根据Pager对象,class对象查找指定bean进行查询(提供分页、查找、排序功能).
	 * 
	 * @param pager
	 *            Pager对象
	 * @return Pager对象
	 */
	public Pager findByPager(Pager pager,Class<T> clazz);
	
	/**
	 * 根据Pager和DetachedCriteria对象进行查询(提供分页、查找、排序功能).
	 * 
	 * @param pager
	 *            Pager对象
	 * @return Pager对象
	 */
	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria);
	
	/**
	 * hql分页
	 * @param pager
	 * @param hql
	 * @return
	 */
	public Pager findByHql(Pager pager);
	/**
	 * hql查询
	 * @param hql
	 * @return
	 */
	public  List<T> find(String hql) ;
	
	/**
	 * 防注入 hql 查询
	 * @param hql
	 * @param args
	 * @return
	 */
	public List<T> find(String hql,Object...args);
	
	public Pager findByQuery(Pager pager,Query query);
	
	public Query getQuery(String hql,Object...args);

	/**
	 * @param hql
	 * @param max
	 * @param args
	 * @return
	 */
	public List<T> findMax(String hql,int max,Object...args);
	/**
	 * 执行hql 增删改语句
	 * @param hql
	 * @param args
	 */
	public void executeHql(String hql,Object...args);
	
	/**
	 * 
	 * @param filters 过滤条件list
	 * @param max 查询指定数量
	 * @param orderBy 排序字段  可以是多字段中间 逗号分隔
	 * @param orderType 排序方式
	 * @return
	 */
	public List<T> findListByFilters(List<PropertyFilter> filters,int max,String orderBy,String orderType);


}