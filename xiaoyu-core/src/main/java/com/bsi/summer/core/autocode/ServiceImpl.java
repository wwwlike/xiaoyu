package com.bsi.summer.core.autocode;

import java.io.File;

import javax.servlet.ServletContext;

import com.bsi.summer.core.model.ITable;
import com.bsi.summer.core.util.FreemarkerUtils;


public class ServiceImpl extends FreemarkerData<SmrFrameAutoModel> {
   
	public ServiceImpl( ITable table, ServletContext context,SmrFrameAutoModel model) {
		super(table, context,model);
	}
	
	public ServiceImpl( ITable table,SmrFrameAutoModel model) {
		super(table,model);
	}

	@Override
	public File getTraget() {
		String tragetPath=srcPath+"/"+FreemarkerUtils.package2path(model.getServiceImplPackage())+"/"+model.getServiceImplName()+".java";
		traget=new File(tragetPath);
		return traget;
	}

	@Override
	public String getXmlPath() {
		return "/summer/autoJava/serviceImpl";
	}
	
	public static void main(String[] args) {
	
	}
	

}
