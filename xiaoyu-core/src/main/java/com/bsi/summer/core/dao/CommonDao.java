package com.bsi.summer.core.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - Dao实现类基类
 * 
 * @author Administrator
 * 
 * @param <T>
 * @param <PK>
 */
@Repository
public  class CommonDao extends BaseDaoImpl{

	@Resource
	protected void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		
	}


}