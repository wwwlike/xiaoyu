package com.bsi.summer.core.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bsi.summer.core.dao.PropertyFilter;

/**
 * Bean类 - 分页
 */

@SuppressWarnings("unchecked")
public class Pager {
	
//	// 排序方式
//	public enum OrderType{
//		asc, desc
//	}
	
	public Pager() {
		super();
	}

	public Pager(Integer pageSize, String orderBy) {
		super();
		this.pageSize = pageSize;
		this.orderBy = orderBy;
	}

	public static final Integer MAX_PAGE_SIZE = 500;// 每页最大记录数限制
	private boolean is_all=false;//是否查询全部
	private Integer pageNumber = 1;// 当前页码
	public Integer pageSize = null;// 每页记录数
	private Integer totalCount = 0;// 总记录数
	private Integer pageCount = 0;// 总页数
	private Map<String,Object> searchMap=new HashMap();//多项搜索
	private String orderBy = "";// 排序字段
	private String orderType = "desc";//默认 排序方式
	private List list;// 数据List
	private String hql; //hql 查询 
	private String sql; //sql 查询
	List<PropertyFilter> andFilters=null;
	List<PropertyFilter> orFilters=null;
	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		if (pageNumber < 1) {
			pageNumber = 1;
		}
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		if(pageSize==null||pageSize==0)
			return 10;
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if (getPageSize() < 1) {
			pageSize = 1;
		} else if(getPageSize() > MAX_PAGE_SIZE) {
			pageSize = MAX_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}
	
	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageCount() {
		pageCount = totalCount / getPageSize();
		if (totalCount % getPageSize() > 0) {
			pageCount ++;
		}
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Map<String, Object> getSearchMap() {
		return searchMap;
	}

	public void setSearchMap(Map<String, Object> searchMap) {
		this.searchMap = searchMap;
	}

	public boolean isIs_all() {
		return is_all;
	}

	public void setIs_all(boolean isAll) {
		is_all = isAll;
	}

	public String getHql() {
		return hql;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public List<PropertyFilter> getAndFilters() {
		return andFilters;
	}

	public void setAndFilters(List<PropertyFilter> andFilters) {
		this.andFilters = andFilters;
	}

	public List<PropertyFilter> getOrFilters() {
		return orFilters;
	}

	public void setOrFilters(List<PropertyFilter> orFilters) {
		this.orFilters = orFilters;
	}
	/**
	 * 加入一个过滤条件
	 * @param filter
	 */
	public void addAndFilter(PropertyFilter filter){
		if(getAndFilters()==null){
			andFilters=new ArrayList<PropertyFilter>();
		}
		andFilters.add(filter);
	}
}