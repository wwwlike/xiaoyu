package com.bsi.summer.core.autocode;

import java.io.File;

import javax.servlet.ServletContext;

import com.bsi.summer.core.model.ITable;
import com.bsi.summer.core.util.FreemarkerUtils;
import com.bsi.summer.core.util.StringUtils;

public class ListJsp extends FreemarkerData<SmrFrameAutoModel>{

	

	public ListJsp(ITable table, ServletContext context, SmrFrameAutoModel model) {
		super(table, context, model);
	}

	@Override
	public File getTraget() {
		String tragetPath="WebContent/jsp/"+StringUtils.uncap_first(model.getBeanName())+"11/"+model.getServiceName()+".java";
		traget=new File(tragetPath);
		return traget;
	}

	@Override
	public String getXmlPath() {
		return "/summer/autoJsp/list";
	}

}
