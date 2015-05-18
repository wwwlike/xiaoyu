package com.bsi.summer.core.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.util.Assert;

import com.bsi.summer.action.ActionUtils;
import com.bsi.summer.core.bean.Pager;
import com.bsi.summer.core.item.Item;

/**
 * Dao实现类 - Dao实现类基类 =?= andFilter or filter 需要从pager里面分离出来。
 * 
 * @author Administrator
 * 
 * @param <T>
 * @param <PK>
 */

public abstract class BaseDaoImpl<T extends Item<PK>, PK extends Serializable>
		implements BaseDao<T, PK> {

	private Class<T> entityClass;
	protected SessionFactory sessionFactory;
	protected CriteriaUtil criteriaUtil;
	
	protected abstract void setSessionFactory(SessionFactory sessionFactory);

	/**
	 * 根据实体bean 转换成filters 后查询指定数量结果集
	 * @param temp
	 * @return
	 */
	public List<T> findListByFilters(List<PropertyFilter> temp,int max) {
		criteriaUtil.clearFilter();
		return criteriaUtil.buildCriterionByPropertyAndFilter(
				getSession().createCriteria(entityClass), temp).setFirstResult(0).setMaxResults(max).list();
	}
	

	/**
	 * 根据实体bean 转换成filters 查询指定条数结果集 有排序
	 * @param temp
	 * @return
	 */
	public List<T> findListByFilters(List<PropertyFilter> temp,int max,String orderBy,String orderType) {
		criteriaUtil.clearFilter();
		return criteriaUtil.addOrder(criteriaUtil.buildCriterionByPropertyAndFilter(
				getSession().createCriteria(entityClass), temp),orderBy,orderType).setFirstResult(0).setMaxResults(max).list();
	}
	
	
	/**
	 * 根据实体bean 转换成filters 查询所有结果集
	 * @param temp
	 * @return
	 */
	public List<T> findListByFilters(List<PropertyFilter> temp) {
		criteriaUtil.clearFilter();
		return criteriaUtil.buildCriterionByPropertyAndFilter(
				getSession().createCriteria(entityClass), temp).list();
	}
	/**
	 * 实体对象转换成查询条件 后 返回 查询结果集
	 * @param t
	 * @return
	 */
	public List<T> findListByItem(T t) {
		return findListByFilters(ActionUtils.beanToPropertyFilter(t));
	}
	
	/**
	 * 实体对象转换成查询条件 后 返回指定数量结果集
	 * @param t
	 * @return
	 */
	public List<T> findListByItem(T t,int max) {
		return findListByFilters(ActionUtils.beanToPropertyFilter(t),max);
	}
	

	@Resource
	public void setCriteriaUtil(CriteriaUtil criteriaUtil) {
		this.criteriaUtil = criteriaUtil;
	}

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		this.entityClass = null;
		Class<? extends BaseDaoImpl> c = getClass();
		Type type = c.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) type)
					.getActualTypeArguments();
			this.entityClass = (Class<T>) parameterizedType[0];
		}
	}

	public Session getSession() {
		Session session;
		session = sessionFactory.getCurrentSession();
		return session;

	}

	@SuppressWarnings("unchecked")
	public T get(PK id) {
		Assert.notNull(id, "id is required");
		T t=(T) getSession().get(entityClass, id);
		return t;
	}

	@SuppressWarnings("unchecked")
	public Item<PK> get(Class<Item<PK>> clazz,PK id) {
		Assert.notNull(id, "id is required");
		return (T) getSession().get(clazz, id);
	}
	
	

	@SuppressWarnings("unchecked")
	public T load(PK id) {
		Assert.notNull(id, "id is required");
		return (T) getSession().load(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> get(PK[] ids) {
		Assert.notEmpty(ids, "ids must not be empty");
		String hql = "from " + entityClass.getName()
				+ " as model where model.id in(:ids)";
		return getSession().createQuery(hql).setParameterList("ids", ids)
				.list();
	}

	@SuppressWarnings("unchecked")
	public T get(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String hql = "from " + entityClass.getName() + " as model where model."
				+ propertyName + " = ?";
		return (T) getSession().createQuery(hql).setParameter(0, value)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public Item get(Class clazz, String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String hql = "from " + clazz.getName() + " as model where model."
				+ propertyName + " = ?";
		return (T) getSession().createQuery(hql).setParameter(0, value)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public T get(Map<String, String> map) {
		Assert.notEmpty(map, "map must not be empty");
		String hql = "from " + entityClass.getName() + " as model where ";
		Query query = getSession().createQuery(hql);
		for (Entry<String, String> str : map.entrySet()) {
			query.setParameter("model." + str.getKey(), str.getValue());
		}
		return (T) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<T> getList(String propertyName, Object value) {

		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String hql = "from " + entityClass.getName() + " as model where model."
				+ propertyName + " = ?";

		return getSession().createQuery(hql).setParameter(0, value).list();
	}

	@SuppressWarnings("unchecked")
	public List<T> getList(String propertyName, Object value,String orderby ,String orderType) {

		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String hql = "from " + entityClass.getName() + " as model where model."
				+ propertyName + " = ? order by "+ orderby+" "+orderType;

		return getSession().createQuery(hql).setParameter(0, value).list();
	}

	
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		String hql = "from " + entityClass.getName();

		return getSession().createQuery(hql).list();
	}

	public List<T> getAll(Pager pager) {

		Map<String, Object> searchMap = pager.getSearchMap();
		String hql = "from " + entityClass.getName() + "where 1=1";
		if (searchMap != null && searchMap.size() > 0) {
			Set<String> key = searchMap.keySet();
			for (Iterator<String> it = key.iterator(); it.hasNext();) {
				String s = it.next();
				Object value = searchMap.get(s);
				hql += " and " + s + "=" + value;
			}
		}
		return getSession().createQuery(hql).list();
	}

	public Long getTotalCount() {
		String hql = "select count(*) from " + entityClass.getName();
		return (Long) getSession().createQuery(hql).uniqueResult();
	}

	public boolean isUnique(String propertyName, Object oldValue,
			Object newValue) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(newValue, "newValue is required");
		if (newValue == oldValue || newValue.equals(oldValue)) {
			return true;
		}
		if (newValue instanceof String) {
			if (oldValue != null
					&& StringUtils.equalsIgnoreCase((String) oldValue,
							(String) newValue)) {
				return true;
			}
		}
		T object = get(propertyName, newValue);
		return (object == null);
	}

	public boolean isExist(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		T object = get(propertyName, value);
		return (object != null);
	}

	@SuppressWarnings("unchecked")
	public PK save(T entity) {
		Assert.notNull(entity, "entity is required");
		return (PK) getSession().save(entity);
	}

	public void update(T entity) {
		Assert.notNull(entity, "entity is required");
		getSession().update(entity);
	}

	public void saveOrupdate(T entity) {
		Assert.notNull(entity, "entity is required");
		getSession().saveOrUpdate(entity);
	}

	public void delete(T entity) {
		Assert.notNull(entity, "entity is required");
		getSession().delete(entity);
	}

	public void delete(PK id) {
		Assert.notNull(id, "id is required");
		T entity = load(id);
		getSession().delete(entity);
	}

	public void delete(PK[] ids) {
		Assert.notEmpty(ids, "ids must not be empty");
		for (PK id : ids) {
			T entity = load(id);
			getSession().delete(entity);
		}
	}

	public void flush() {
		getSession().flush();
	}

	public void clear() {
		getSession().clear();
	}

	public void evict(Object object) {
		Assert.notNull(object, "object is required");
		getSession().evict(object);
	}

	public Pager findByPager(Pager pager) {
		if (pager == null) {
			pager = new Pager();
		}
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(entityClass);
		return findByPager(pager, detachedCriteria);
	}
	

	public Pager findByPager(Pager pager, Class<T> myClass) {
		if (pager == null) {
			pager = new Pager();
		}
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(myClass);
		return findByPager(pager, detachedCriteria);
	}
	
	public Pager findByPager(Pager pager, T bean) {
		if (pager == null) {
			pager = new Pager();
		}
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(bean.getClass());
		return findByPager(pager, detachedCriteria);
	}

	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria) {
		if (pager == null)
			pager = new Pager();
		Integer pageNumber = pager.getPageNumber();
		Integer pageSize = pager.getPageSize();
		String orderBy = pager.getOrderBy();
		String orderType = pager.getOrderType();
		List<PropertyFilter> filters = pager.getAndFilters();
		List<PropertyFilter> Orfilters = pager.getOrFilters();
		Criteria criteria = detachedCriteria
				.getExecutableCriteria(getSession());
		criteria.setCacheable(true);
		// 多项目搜索组装查询条件和
		criteriaUtil.clearFilter();
		if (filters != null && filters.size() > 0)
			criteria = criteriaUtil.buildCriterionByPropertyAndFilter(criteria,
					filters);
		if (Orfilters != null && Orfilters.size() > 0)
			criteria = criteriaUtil.buildCriterionByPropertyOrFilter(criteria,
					Orfilters);

		// 查询总共记录数 查询得到count后 将之前的Projection设置回去
		Integer totalCount = ((Number) criteria.setProjection(
				Projections.rowCount()).uniqueResult()).intValue();
		pager.setTotalCount(totalCount);
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		// 分页设置
		if (!pager.isIs_all()) {
			criteria = criteriaUtil.setPaging(criteria, pageNumber, pageSize);
		}
		// 排序方式
		criteria = criteriaUtil.addOrder(criteria, orderBy, orderType);
		// 结果填充
		pager.setList(criteria.list());
		return pager;
	}

	public Pager findByHql(Pager pager) {
		Query query = getSession().createQuery(pager.getHql());
		Integer totalCount = query.list().size();
		pager.setTotalCount(totalCount);
		if (!pager.isIs_all()) {
			// 查询分页数据 否则查询 全部数据
			query.setFirstResult((pager.getPageNumber() - 1)
					* pager.getPageSize());
			query.setMaxResults(pager.getPageSize());
		}
		List<T> list = query.list();
		pager.setList(list);
		return pager;
	}
	
	
	public Pager findByQuery(Pager pager,Query query) {
		Integer totalCount =query.list().size();
		pager.setTotalCount(totalCount);
		if (!pager.isIs_all()) {
			// 查询分页数据 否则查询 全部数据
			query.setFirstResult((pager.getPageNumber() - 1)
					* pager.getPageSize());
			query.setMaxResults(pager.getPageSize());
		}
		List<T> list = query.list();
		pager.setList(list);
		return pager;
	}
	
	public void executeHql(String hql,Object...args){
		Query query=getQuery(hql,args);
		query.executeUpdate();
	}
	
	public List<T> find(String hql) {
		return getQuery(hql).list();
	}
	
	public List<T> find(String hql,Object...args) {
		return getQuery(hql, args).list();
	}
	
	public List<T> findMax(String hql,int max,Object...args){
		return getQuery(hql,args).setFirstResult(0).setMaxResults(max).list();
	}

	
	public Query getQuery(String hql,Object...args) {
		Query query= null;
		int i=0;
		if(query==null){
			query=getSession().createQuery(hql);
		}
		
		for(Object arg:args){
			query.setParameter(i,arg);
			i++;
		}
		return query;
	}

}