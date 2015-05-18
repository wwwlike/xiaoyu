package com.bsi.summer.core.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.bsi.summer.core.dao.PropertyFilter.MatchType;
import com.bsi.summer.core.dao.PropertyFilter.PropertyType;
import com.bsi.summer.core.util.TimeUtils;
@Component
public class CriteriaUtil {
	  private List<PropertyFilter> filters = new ArrayList<PropertyFilter>();   
	  private Set<String> alias = new HashSet<String>(); // 防止重复添加alias用   
	  public boolean hasFilters() {   
	        return filters.size() > 0;   
	    }   
	  
	    /**  
	     * 清理过滤器  
	     * 在具有级联条件的查询中，alias不能重复添加，只允许添加一次  
	     * 在执行完一次count(*)查询后，之前添加的criteria全部失效  
	     * 重新添加criteria的时候，需要清空alias的Set集合  
	     * 以便正确执行createAlias语句  
	     *   
	     */  
	    public void clearFilter() {   
	        alias.clear();   
	    }   
	    
	    public String aliasArray[]={"a1","b1","c1","d1","e1","f1","g1","h1","i1"}; //别名前缀
	    
	    
	    public String createAlias(final Criteria criteria, final String property) {   
	        String[] names = StringUtils.split(property, ".");   
	        String str=null;
	        if (names != null) {   
	        	for(int i=0;i<=names.length-2;i++){
	        		if(str==null){
	        			str=names[i];
	        		}else{
	        			str+="."+names[i];
	        		}
		            if (alias.add(str)) {   
		                criteria.createAlias(str, aliasArray[i]+"_"+names[i],CriteriaSpecification.LEFT_JOIN);   
		            }   
	        	}
	        }
	        return null;
	    }   
	    
	    public String tranStr(String propertyName){
	        String[] names = StringUtils.split(propertyName, ".");  
	        if(names.length>1){
	        return aliasArray[names.length-2]+"_"+names[names.length-2]+"."+names[names.length-1];
	        }else{
	        	return propertyName;
	        }
	    }
	    
	    /**  
	     * 为Criteria对象添加别名  
	     * 最多支持2级级联，例如animal.pet.cat  
	     * @param criteria Criteria对象  
	     * @param property 属性  
	     * @return 别名  
	     * 
	     * 
	     * 
	     */  
//	    private String createAlias(final Criteria criteria, final String property) {   
//	        String[] names = StringUtils.split(property, ".");   
//	        if (names != null && names.length == 2) {   
//	            if (alias.add(names[0])) {   
//	                criteria.createAlias(names[0], names[0],CriteriaSpecification.LEFT_JOIN);   
//	            }   
//	            return names[0] + "." + names[1];   
//	        } else if (names != null && names.length == 3) {   
//	            if (alias.add(names[0])) {   
//	                criteria.createAlias(names[0], names[0],CriteriaSpecification.LEFT_JOIN);   
//	            }   
//	            if (alias.add(names[0] + "." + names[1])) {   
//	                criteria.createAlias(names[0] + "." + names[1], names[1],CriteriaSpecification.LEFT_JOIN);   
//	            }   
//	            return names[1] + "." + names[2];   
//	        } else {   
//	            return property;   
//	        }   
//	    }   
	    
	
	/**
	 * 按属性条件参数创建Criterion,辅助函数.
	 */
	protected Criterion buildCriterion(PropertyFilter filter) {
		String propertyName=filter.getPropertyName(); 
		//propertyName 装换成带别名的
		propertyName=tranStr(propertyName);
		Object propertyValue=filter.getMatchValue();
		MatchType matchType=filter.getMatchType();
		PropertyType propertyType=filter.getPropertyClass();
		
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = null;
	    if (StringUtils.isNotBlank(propertyName)) {   
            if (matchType == MatchType.BETWEEN) {  
            	criterion = Restrictions.between(propertyName,propertyValue, filter.getMatchBetweenValue());  
            } else {   
                switch (matchType) {   
                case NULL:   
                    criterion=Restrictions.isNull(propertyName);   
                    break;   
                case NOTNULL:   
                    criterion=Restrictions.isNotNull(propertyName);  
                    break;   
                case EMPTY:   
                    criterion=Restrictions.isEmpty(propertyName);   
                    break;   
                case NOTEMPTY:   
                    criterion=Restrictions.isNotEmpty(propertyName);   
                    break;   
                }   
                
                if(propertyType!=null&&criterion==null&&propertyValue != null){
                	//按照数据类型产生 qbc条件
                	
                	switch(propertyType){
                	 case D: 
                		 criterion=Restrictions.sqlRestriction("to_char("+propertyName+",'yyyy-MM-dd')='"+TimeUtils.dateToString((Date)propertyValue, "yyyy-MM-dd")+"'");  
                		 break;
                	}
                }
                
                
                if (criterion==null&&propertyValue != null) {   
                    switch (matchType) {   
                    case EQ:   
                        criterion=Restrictions.eq(propertyName, propertyValue);   
                        break;   
                    case LIKE:   
                        criterion=Restrictions.like(propertyName, (String) propertyValue, MatchMode.ANYWHERE);   
                        break;   
                    case LIKESTART:   
                        criterion=Restrictions.like(propertyName, (String) propertyValue, MatchMode.START);   
                        break;   
                    case LIKEND:   
                        criterion=Restrictions.like(propertyName, (String) propertyValue, MatchMode.END);   
                        break;   
                    case LE:   
                        criterion=Restrictions.le(propertyName, propertyValue);   
                        break;   
                    case LT:   
                        criterion=Restrictions.lt(propertyName, propertyValue);   
                        break;   
                    case GE:   
                        criterion=Restrictions.ge(propertyName, propertyValue);   
                        break;   
                    case GT:   
                        criterion=Restrictions.gt(propertyName, propertyValue);   
                        break;   
                    case NE:   
                        criterion=Restrictions.ne(propertyName, propertyValue);   
                        break; 
                    case IN:   
                        criterion=Restrictions.in(propertyName, propertyValue.toString().split(","));   
                        break;   
                    }   
                    
                }   
            }   
        }  
		return criterion;
	}

	
	
	
	/**
	 * 按属性条件列表创建Criterion数组,辅助函数. and
	 */
	public Criteria buildCriterionByPropertyAndFilter(Criteria criteria,final List<PropertyFilter> filters) {
		for (PropertyFilter filter : filters) {
			if (!filter.hasMultiProperties()) { //只有一个属性需要比较的情况.
				String property=createAlias(criteria,filter.getPropertyName()); //=?= 不支持 3级别以上查询
				Criterion criterion = buildCriterion(filter);
				criteria.add(criterion);
			} else {//包含多个属性需要比较的情况,进行or处理.
				Disjunction disjunction = Restrictions.disjunction();
				for (String param : filter.getPropertyNames()) {
					String property=createAlias(criteria,param); 
					Criterion criterion = buildCriterion(filter);
					disjunction.add(criterion);
				}
				criteria.add(disjunction);
			}
		}
		return criteria;
	}
	
	/**
	 * 按属性条件列表创建Criterion数组,辅助函数. OR
	 */
	protected Criteria buildCriterionByPropertyOrFilter(Criteria criteria,final List<PropertyFilter> filters) {
		Disjunction disjunction = Restrictions.disjunction();
		for (PropertyFilter filter : filters) {
				String property=createAlias(criteria,filter.getPropertyName()); 
				Criterion criterion = buildCriterion(filter);
				disjunction.add(criterion);
		}
		criteria.add(disjunction);
		return criteria;
	}
	
	
	/**
	 * 分页设置
	 */
	public Criteria setPaging(Criteria criteria,int pageNumber,int pageSize)
	{
		criteria.setFirstResult((pageNumber - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		return criteria;
	}
	
	public Criteria addOrder(Criteria criteria,String orderBy,String orderType)
	{
		//排序方式
		if (StringUtils.isNotEmpty(orderBy) && orderType != null) {
			String[]orders=orderBy.split(",");
			
			for(String order:orders)
			{
				if(order.contains("."))
				{
					orderType=StringUtils.substringAfter(order, ".");
					order=StringUtils.substringBefore(order, ".");
				}
				
				if (orderType.equals("asc")) {
					criteria.addOrder(Order.asc(order));
				} else {
					criteria.addOrder(Order.desc(order));
				}
			}
		}
		return criteria;
	}
	
	
	
	/**
	 * 复杂关系查询
	 * @param criteria
	 * @param str
	 * @param prefix
	 * @return
	 */
	public ProjectionList createProjectionList(String groupBys[][],String avgs[][],String counts[][],String mins[][],String sums[][],String maxs[][])
	{
		ProjectionList projectList=Projections.projectionList();
		for(String[] groupby:groupBys)
		{
			//
//			if(groupby[0].split("\\.").length>1)
//				projectList.add(Projections.groupProperty(groupby[0].split("\\.")[1]).as(groupby[1]),"medicalhospital");
//				else
			if(groupby.length==2)
				projectList.add(Projections.groupProperty(groupby[0]).as(groupby[1]));
			if(groupby.length==1)
			projectList.add(Projections.groupProperty(groupby[0]));
			
		}
		for(String[] avg:avgs)
		{
			projectList.add(Property.forName(avg[0]).avg().as(avg[1]));
		}
		for(String[] count:counts)
		{
			projectList.add(Property.forName(count[0]).count().as(count[1]));
		}
		for(String[] min:mins)
		{
			projectList.add(Property.forName(min[0]).min().as(min[1]));
		}
		for(String[] sum:sums)
		{
			projectList.add(Projections.sum(sum[0]).as(sum[1]));
		}
		for(String[] max:maxs)
		{
			projectList.add(Property.forName(max[0]).max().as(max[1]));
		}
		
	//	projectList.add(Projections.property("hospital.xzqh").as("xzqh"));//
		return projectList;
	}
	
	
	
	
	
	

}
