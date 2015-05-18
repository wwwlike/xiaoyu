package com.bsi.summer.core.service;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.bsi.summer.core.bean.Pager;
import com.bsi.summer.core.dao.BaseDao;
import com.bsi.summer.core.item.Item;

/**
 * Service实现类 - Service实现类基类
 * 
 * @author Administrator
 * 
 * @param <T>
 * @param <PK>
 */

public class BaseServiceImpl<T extends Item<PK>, PK extends Serializable, D extends BaseDao<T, PK>>
		implements BaseService<T, PK> {

	protected D dao;

	public D getDao() {
		return dao;
	}

	public void setDao(D dao) {
		this.dao = dao;
	}

	public T get(PK id) {
		return dao.get(id);
	}

	public T load(PK id) {
		return dao.load(id);
	}

	public List<T> get(PK[] ids) {
		return dao.get(ids);
	}

	public T get(String propertyName, Object value) {
		return dao.get(propertyName, value);
	}

	public List<T> getList(String propertyName, Object value) {
		return dao.getList(propertyName, value);
	}

	public List<T> getAll() {
		return dao.getAll();
	}

	public Long getTotalCount() {
		return dao.getTotalCount();
	}

	public boolean isUnique(String propertyName, Object oldValue,
			Object newValue) {
		return dao.isUnique(propertyName, oldValue, newValue);
	}

	public boolean isExist(String propertyName, Object value) {
		return dao.isExist(propertyName, value);
	}

	public PK save(T entity) {
		return dao.save(entity);
	}

	public void update(T entity) {
		dao.update(entity);
	}

	public void delete(T entity) {
		dao.delete(entity);
	}

	public void delete(PK id) {
		dao.delete(id);
	}

	public void delete(PK[] ids) {
		dao.delete(ids);
	}
	
	public void delete(List<T> beans) {
		for(T t:beans){
			delete(t);
		}
	}

	public void flush() {
		dao.flush();
	}

	public void clear() {
		dao.clear();
	}

	public void evict(Object object) {
		dao.evict(object);
	}

	public Pager findByPager(Pager pager) {
		return dao.findByPager(pager);
	}

	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria) {
		return dao.findByPager(pager, detachedCriteria);
	}

	public Pager findByPager(Pager pager, T bean) {
		return dao.findByPager(pager, bean);
	}

	public Pager findByHql(Pager pager) {
		return dao.findByHql(pager);
	}

	public List<T> findByHql(String hql) {
		return dao.find(hql);
	}

	public Pager findByPager(Pager pager, Class<T> clazz) {
		return dao.findByPager(pager, clazz);

	}

	public Item<PK> get(Class<Item<PK>> clazz, PK id) {
		return dao.get(clazz, id);
	}

	public List<T> findByHql(String hql,Object ...args){
		return dao.find(hql, args);
	}
	
	public Pager findByHql(Pager pager,String hql,Object ...args) {
		return dao.findByQuery(pager,dao.getQuery(hql, args));
	}

	@Override
	public List<T> getAll(Pager pager) {
		// TODO Auto-generated method stub
		return dao.getAll(pager);
	}
	
	public List<T> find(String hql,int max,Object...args){
		return dao.findMax(hql, max, args);
	}

	@Override
	public void  saveItems(List<T> items){
		for(T t:items){
			dao.saveOrupdate(t);
		}
	}
	
	
}
