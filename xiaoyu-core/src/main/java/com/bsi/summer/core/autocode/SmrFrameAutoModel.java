package com.bsi.summer.core.autocode;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bsi.summer.core.model.ITable;
/**
 * summer 框架 代码生成模型
 * dao,service,action 
 * @author Administrator
 *
 */
@Component
public  class SmrFrameAutoModel extends IAutoModel{

	private SmrFrameAutoModel()
	{
		
	}
	private static SmrFrameAutoModel autoModel=null;
	public static SmrFrameAutoModel getInstance()
	{
		if(autoModel==null)
			return new SmrFrameAutoModel();
		else
			return autoModel;
	}
	
	private  ITable bean;
	
	//包路径
	private String servicePackage;
	private String actionPackage;
	private String daoPackage;
	private String daoImplPackage;
	private String serviceImplPackage;
	
	//类名称
	private String daoName;
	private String daoImplName;
	private String actionName;
	private String serviceName;
	private String serviceImplName;
	private String beanName; //bean名称
	private String idName="String";   //id名称
	
	
	
	
	public void transformPath(ITable bean) {
		if(bean!=getBean())
		{
		String pathArray[]=bean.getJavaBean().split("\\.");
		int arraySize=pathArray.length;
		setDaoName(pathArray[arraySize-1]+"Dao");
		setDaoImplName(pathArray[arraySize-1]+"DaoImpl");
		setServiceName(pathArray[arraySize-1]+"Service");
		setServiceImplName(pathArray[arraySize-1]+"ServiceImpl");
		setActionName(pathArray[arraySize-1]+"Action");
		setBeanName(StringUtils.substringAfterLast(bean.getJavaBean(), "."));
		if(bean.getPkBean()!=null&& !bean.getPkBean().equals("String"))
		setIdName(bean.getPkBean());
//		if(bean.getItemId()!=null)
//			setIdName(StringUtils.substringAfterLast(bean.getItemId(), "."));
		String path="";
			for(int i=0;i<arraySize;i++)
			{
			 path+=pathArray[i]+".";
			if(i==arraySize-3)
			{
				setDaoPackage(path+"dao");
				setServicePackage(path+"service");
				setServiceImplPackage(path+"service");
				setDaoImplPackage(path+"dao");
			}
			if(i==arraySize-4)
			{
				setActionPackage(path+"action"+(StringUtils.isNotEmpty(bean.getModelPath())?("."+StringUtils.substringBefore(bean.getModelPath().toLowerCase(), "/")  ):""));
			}
			}
		}
		else
		{
			setBean(bean);
		}
	}

	
	public ITable getBean() {
		return bean;
	}



	public void setBean(ITable bean) {
		this.bean = bean;
	}



	public String getServicePackage() {
		return servicePackage;
	}

	public void setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
	}

	public String getActionPackage() {
		return actionPackage;
	}

	public void setActionPackage(String actionPackage) {
		this.actionPackage = actionPackage;
	}

	public String getDaoPackage() {
		return daoPackage;
	}

	public void setDaoPackage(String daoPackage) {
		this.daoPackage = daoPackage;
	}
	
	public String getDaoName() {
		return daoName;
	}

	public void setDaoName(String daoName) {
		this.daoName = daoName;
	}

	public String getDaoImplName() {
		return daoImplName;
	}

	public void setDaoImplName(String daoImplName) {
		this.daoImplName = daoImplName;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceImplName() {
		return serviceImplName;
	}

	public void setServiceImplName(String serviceImplName) {
		this.serviceImplName = serviceImplName;
	}

	
	public String getDaoImplPackage() {
		return daoImplPackage;
	}

	public void setDaoImplPackage(String daoImplPackage) {
		this.daoImplPackage = daoImplPackage;
	}

	public String getServiceImplPackage() {
		return serviceImplPackage;
	}

	public void setServiceImplPackage(String serviceImplPackage) {
		this.serviceImplPackage = serviceImplPackage;
	}


	public String getBeanName() {
		return beanName;
	}


	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}


	public String getIdName() {
		return idName;
	}


	public void setIdName(String idName) {
		this.idName = idName;
	}
	

}
