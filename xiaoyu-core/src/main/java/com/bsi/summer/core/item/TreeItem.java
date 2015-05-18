package com.bsi.summer.core.item;


/**
 * @about 实现树型接口
 * @author donglc
 * @树就是自己里面有自己
 */
public interface  TreeItem<T extends Item> {
	
	public T getParentItem();
	
	public void setParentItem(T parentItem);
}
