package com.bsi.summer.core.autocode;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;

import com.bsi.summer.core.model.ITable;
import com.bsi.summer.core.model.autocode.IAutoCode;
import com.bsi.summer.core.util.FreemarkerUtils;

@Component
public class SmrAutoCode implements IAutoCode {

	public String createCode(ITable bean)
	{		
			return null;
	}
	SmrFrameAutoModel model=null;
	@Override
	public String createJava(ITable bean) {
		model=SmrFrameAutoModel.getInstance();
		FreemarkerData<SmrFrameAutoModel> data=new Dao(bean,model);
		create(data);
		data=new DaoImpl(bean,model);
		create(data);
		data=new Action(bean,model);
		create(data);
		data=new Service(bean,model);
		create(data);
		data=new ServiceImpl(bean,model);
		create(data);
		return null;
	}

	@Override
	public String createJsp(ITable bean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createCode(ITable bean, ServletContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createJava(ITable bean, ServletContext context) {
		model=SmrFrameAutoModel.getInstance();
		FreemarkerData<SmrFrameAutoModel> data=new Dao(bean, context, model);
		create(data,context);
		data=new DaoImpl(bean,model);
		create(data);
		data=new Service(bean,model);
		create(data);
		data=new ServiceImpl(bean,model);
		create(data);
		data=new Action(bean,model);
		create(data);
		return null;
	}

	@Override
	public String createJsp(ITable bean, ServletContext context) {
		model=SmrFrameAutoModel.getInstance();
		FreemarkerData<SmrFrameAutoModel> data=new ListJsp(bean, context, model);
		create(data);
		return null;
	}
	
	

	public void create(FreemarkerData<SmrFrameAutoModel> data)
	{
		FreemarkerUtils.create(data.getTemplatePathFile(), data.getTraget(), data.getData(), data.getTemplateFileName());
	}
	
	public void create(FreemarkerData<SmrFrameAutoModel> data,ServletContext context)
	{
		FreemarkerUtils.create(context,data.getTemplatePathFile(), data.getTraget(), data.getData(), data.getTemplateFileName());
	}

}
