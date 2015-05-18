package com.bsi.summer.core.autocode;

import java.io.File;

import javax.servlet.ServletContext;

import com.bsi.summer.core.model.ITable;
import com.bsi.summer.core.util.FreemarkerUtils;


public class Service extends FreemarkerData<SmrFrameAutoModel> {
   
	public Service( ITable table, ServletContext context,SmrFrameAutoModel model) {
		super(table, context,model);
	}
	
	public Service( ITable table,SmrFrameAutoModel model) {
		super(table,model);
	}

	@Override
	public File getTraget() {
		String tragetPath=srcPath+"/"+FreemarkerUtils.package2path(model.getServicePackage())+"/"+model.getServiceName()+".java";
		traget=new File(tragetPath);
		return traget;
	}

	@Override
	public String getXmlPath() {
		return "/summer/autoJava/service";
	}
}
