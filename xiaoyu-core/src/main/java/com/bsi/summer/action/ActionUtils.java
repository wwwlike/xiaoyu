package com.bsi.summer.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ReflectionUtils;

import com.bsi.summer.core.bean.Pager;
import com.bsi.summer.core.dao.PropertyFilter;
import com.bsi.summer.core.item.Item;
/**
 * summer框架 action item 转换相关静态方法
 * @author Administrator
 *
 */
public class ActionUtils {
	
	/**
	 * item 转办到 propertyFilter里去
	 * @param filters
	 * @param item
	 * @return
	 */
	public static List<PropertyFilter> beanToPropertyFilter(List<PropertyFilter> filters,Item item){
		return beanToPropertyFilter(filters,item,"");
	}
	
	/**
	 * item 转办到 propertyFilter里去
	 * @param filters
	 * @param item
	 * @return
	 */
	public static List<PropertyFilter> beanToPropertyFilter(Item item){
		return beanToPropertyFilter(null,item,"");
	}
	/*
	 * 设置bean里面的参数到pager的andFilter过滤条件中去
	 */
	public static Pager setBeantoPagerFilter(Pager pager,Item item){
		if(pager==null){
			pager=new Pager();
		}
		if(item==null){
			return pager;
		}
		pager.setAndFilters(beanToPropertyFilter(pager.getAndFilters(), item));
		return pager;
	}

	
	public static List<PropertyFilter> beanToPropertyFilter(List<PropertyFilter> filters,Item item,String prefix){
	if(filters==null){filters=new ArrayList<PropertyFilter>();}
		java.lang.reflect.Field[] fields=item.getClass().getFields();
		PropertyFilter filter=null;
		for(java.lang.reflect.Field field:fields){
			Object obj=ReflectionUtils.getField(field, item);
			if(obj!=null&&!"".equals(obj.toString())){
				if(obj instanceof Item){
					beanToPropertyFilter(filters,(Item)obj,prefix+field.getName()+".");
				}else if(!(obj instanceof Collection)){
					filter=new PropertyFilter();
					String [] propertyNames= {prefix+field.getName()};
					filter.setPropertyNames(propertyNames);
					filter.setMatchValue(obj);
					filter.setMatchType(PropertyFilter.getMatchTypeByProType(obj.getClass()));
					filter.setPropertyClass(PropertyFilter.getPropertyClass(obj.getClass()));	
					int index=PropertyFilter.indexOf(filters, filter);
					if(index!=-1){
						filters.remove(index);
					}
					
					filters.add(filter);
				}
			}
		}
		return filters;
	}
	
	
	
	/**
	 * 删除item内子项id为空的实体
	 * @param item
	 * @return
	 */
	public static Item delSubByNUll(Item item){
		java.lang.reflect.Field[] fields=item.getClass().getFields();
		for(java.lang.reflect.Field field:fields){
			Object obj=ReflectionUtils.getField(field, item);
			if(obj instanceof Item&&obj!=null&&!"".equals(obj.toString())){
				Item temp=(Item)obj;
				if(temp.getId()==null||"".equals(temp.getId())){
					ReflectionUtils.setField(field,item,null);
				}
			}
		}
		return item;
	}
	
	/**
	 * 去除map中没有value的键值对
	 * 
	 * @param map
	 * @return
	 */
	protected Map<String, Object> checkMap(Map<String, Object> map) {
		Set<String> key = map.keySet();
		List<String> deleteKey = new ArrayList<String>();
		for (Iterator<String> it = key.iterator(); it.hasNext();) {
			String s = (String) it.next();
			if (StringUtils.isEmpty(map.get(s).toString())
					|| "null".equals(map.get(s).toString())) {
				deleteKey.add(s);
			}
		}
		for (String s : deleteKey) {
			map.remove(s);
		}
		return map;
	}
	

}
