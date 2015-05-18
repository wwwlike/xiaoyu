package com.bsi.summer.core.autocode;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.bsi.summer.core.model.ITable;


/**
 * 代码自动生成 公共的基础数据 创建模板 和Freemarker
 * @author Administrator
 * @date   2012-3-23
 * @category
 */
public abstract   class FreemarkerData<T extends IAutoModel>  {
	
	protected final String classPath=getClass().getClassLoader().getResource("").getPath();
	protected final String XML_PATH="template.xml";
	protected String srcPath="src";
	protected  String xmlPath=null;
	protected String template=null;
	protected File templatePath;// 模板文件路径
	protected File traget;// 生成路径&名称
	protected String templateFileName;// 模板名
	protected ITable table;
	protected Map<String ,Object> data=null;
	protected ServletContext context;
	protected T model;
	

	/**
	 * @param name 模板标签名称
	 * @param table	模块
	 */
	public   FreemarkerData(ITable table,T model){
		this.table=table;
		this.model=model;
		model.transformPath(table);

	}
	
	/**
	 * @param name 模板标签名称
	 * @param table	模块
	 * @param context 上下文
	 */
	public   FreemarkerData(ITable table,ServletContext context,T model){
		this.table=table;
		this.context=context;
		this.model=model;
		model.transformPath(table);
	}
	

	/**
	 * 设置模板参数
	 * @param context
	 */
	public  File getTemplatePathFile(ServletContext context)
	{
		Document document = null;
		try {
			 InputStream in=this.getClass().getResourceAsStream("/template/XML_PATH");//读jar包根目录下的template/XML_PATH文件
			 SAXReader saxReader = new SAXReader();
			 document = saxReader.read(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element element = (Element)document.selectSingleNode(getXmlPath());
		String templateFilePath=element.element("templateFilePath").getTextTrim();
		templatePath = new File(classPath+StringUtils.substringBeforeLast(templateFilePath, "/"));
		templateFileName=StringUtils.substringAfterLast(templateFilePath, "/");
		element = (Element)document.selectSingleNode("/summer");
//		String baseName=element.element("baseName").getTextTrim();
		srcPath=StringUtils.substringBefore(classPath, context.getContextPath())+"/";
//		table.getSrcName();
		return templatePath;
	}
	
	
	/**
	 * 设置模板参数
	 * @param context
	 */
	public  File getTemplatePathFile()
	{
		Document document = null;
		try {
			//String configFilePath = classPath + "\\template\\" + XML_PATH;
			 InputStream in=this.getClass().getResourceAsStream("/template/"+XML_PATH);//读jar包根目录下的template/XML_PATH文件
			 SAXReader saxReader = new SAXReader();
			 document = saxReader.read(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element element = (Element)document.selectSingleNode(getXmlPath());
		String templateFilePath=element.element("templateFilePath").getTextTrim();
		templatePath = new File(classPath+StringUtils.substringBeforeLast(templateFilePath, "/"));
		templateFileName=templateFilePath;
		element = (Element)document.selectSingleNode("/summer");
//		String baseName=element.element("baseName").getTextTrim();
	//	srcPath=StringUtils.substringBefore(classPath, baseName)+baseName+"/"+table.getSrcName();
		srcPath=StringUtils.substringBefore(classPath, "apps")+(table.getSrcName()==null?"src":table.getSrcName());
		//=？= apps jsp文件路径写死了没做到配置。 另外src需要指定文件 
		return templatePath;
	}
	
	/**
	 * 数据
	 * @param table
	 */
	public Map<String,Object> getData() {
		 data=new HashMap<String,Object>();
	     data.put("model", model);
	     data.put("table", table);
		 return data;
	}
	
	/**
	 * 生成目标文件
	 * @param traget
	 */
	public abstract File getTraget();
	
	/**
	 * 模板路径
	 */
	public abstract String getXmlPath() ;
	
	
	public String getTemplateFileName()
	{
		return 	templateFileName;
	}
	/**
	 * 获得javabean的 路径数组
	 * @return
	 */
	public String[] getJavaBeanPathArray()
	{
		return 	table.getJavaBean().split("\\.");
	}
	
	
	public static void main(String[] args) {
		String srcPath=StringUtils.substringBefore("file:/F:/dlc/workspace/himp2/apps/WEB-INF/classes/", "himp2")+"himp2"+"/"+"ccc";
		System.out.println(srcPath);
	}
}
