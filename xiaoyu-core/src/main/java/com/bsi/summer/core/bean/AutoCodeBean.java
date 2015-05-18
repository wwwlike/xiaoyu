package com.bsi.summer.core.bean;

import java.io.File;

/**
 * 代码自动生成Bean
 * @author Administrator
 * @date   2012-3-23
 * @category
 */
public  class AutoCodeBean {
	public File templatePath;// 模板文件路径
	public File traget;// 生成路径&名称
	public String templateFileName;// 模板名

	public File getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(File templatePath) {
		this.templatePath = templatePath;
	}

	public File getTraget() {
		return traget;
	}

	public void setTraget(File traget) {
		this.traget = traget;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

}
