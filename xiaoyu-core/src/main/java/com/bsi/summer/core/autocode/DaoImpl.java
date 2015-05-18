package com.bsi.summer.core.autocode;

import java.io.File;

import javax.servlet.ServletContext;

import com.bsi.summer.core.model.ITable;
import com.bsi.summer.core.util.FreemarkerUtils;


public class DaoImpl extends FreemarkerData<SmrFrameAutoModel> {
   
	public DaoImpl( ITable table, ServletContext context,SmrFrameAutoModel model) {
		super(table, context,model);
	}
	
	public DaoImpl( ITable table,SmrFrameAutoModel model) {
		super(table,model);
	}

	@Override
	public File getTraget() {
		String tragetPath=srcPath+"/"+FreemarkerUtils.package2path(model.getDaoImplPackage())+"/"+model.getDaoImplName()+".java";
		traget=new File(tragetPath);
		return traget;
	}

	@Override
	public String getXmlPath() {
		return "/summer/autoJava/daoImpl";
	}
	
	public static void main(String[] args) {
	
	}
	

}
