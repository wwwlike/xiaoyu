package com.bsi.summer.core.model.autocode;

import javax.servlet.ServletContext;

import com.bsi.summer.core.model.ITable;

/**
 * 代码自动生成接口！要生成本框架代码需要实现该接口(适配器)
 * @author Administrator
 *
 */
public interface  IAutoCode {
	/**
	 * 创建java ,JSP代码
	 * @param bean
	 */
	 public String createCode(ITable bean);
	
	/**
	 * 创建JAVA代码
	 * @param bean
	 * @return
	 */
	 public String createJava(ITable bean);
	/**
	 * 创建JSP代码
	 * @param bean
	 * @return
	 */
	 public String createJsp(ITable bean);
	
	
	 public String createCode(ITable bean,ServletContext context);
	 public String createJava(ITable bean,ServletContext context);
	 public String createJsp(ITable bean,ServletContext context);
}
