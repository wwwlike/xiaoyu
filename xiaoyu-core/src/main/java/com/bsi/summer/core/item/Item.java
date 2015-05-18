package com.bsi.summer.core.item;

import java.io.Serializable;

/**
 * 所有以数据库表直接对应的bean必须实现该接口
 * 继承Cloneable接口 所有对象可克隆 clone
 * @author 董立超
 */
public interface  Item<T extends Serializable> extends Serializable{
	
	public T getId();
	
	public void setId(T id);
	
	public int hashCode() ;

	public boolean equals(Object obj);
    
}