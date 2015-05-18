package com.bsi.summer.core.item;



/**
 * 复合主键ID
 * @author Administrator
 * @date   2012-3-29
 * @category
 */

public interface ItemId  extends java.io.Serializable {
	
	public int hashCode() ;

	public boolean equals(Object obj);
}
