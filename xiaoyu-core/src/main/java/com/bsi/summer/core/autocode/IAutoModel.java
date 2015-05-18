package com.bsi.summer.core.autocode;

import com.bsi.summer.core.model.ITable;

/**
 * 代码自动生成模型
 * @param bean
 */
public abstract class IAutoModel {
	
	/**
	 * 转换bean to 数模模型
	 * @param bean
	 */
	public abstract void transformPath(ITable bean) ;

}
