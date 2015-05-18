package com.bsi.summer.core.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.views.freemarker.FreemarkerManager;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerUtils {
	
	private static Configuration cfg = null;

	public static Configuration getConfiguration(String templateDir) throws IOException {
		if (null == cfg) {
			cfg = new Configuration();
	        File templateDirFile = new File(templateDir);
	        cfg.setDirectoryForTemplateLoading(templateDirFile);
	        cfg.setLocale(Locale.CHINA);
	        cfg.setDefaultEncoding("UTF-8");
		}
		return cfg;
	}
	
	
	public static Configuration getConfiguration(ServletContext context) throws IOException, TemplateException {
		FreemarkerManager freemarker=new FreemarkerManager();
		if (null == cfg) {
			 cfg = freemarker.getConfiguration(context);
			 cfg.setLocale(Locale.CHINA);
		     cfg.setDefaultEncoding("UTF-8");
		}
		return cfg;
	}
	
	
	public static Configuration getConfiguration(File templateFlt) throws IOException {
		if (null == cfg) {
			cfg = new Configuration();
	        cfg.setDirectoryForTemplateLoading(templateFlt);
	        cfg.setLocale(Locale.CHINA);
	        cfg.setDefaultEncoding("UTF-8");
		}
		return cfg;
	}
	
	
	/**
	 * 根据模板数据生成文件
	 * @param templatePath ftl模板路径
	 * @param traget 生成文件路径
	 * @param data   数据Map<String,Object>集合
	 * @param templateFileName 模板文件名称
	 */
	public  static void create(File templatePath,File traget,Map<String,Object> data,String templateFileName)
	{
		try {
	        Template template = getConfiguration(templatePath).getTemplate(templateFileName);
			File htmlDirectory = traget.getParentFile();
			if (!htmlDirectory.exists()) {
				htmlDirectory.mkdirs();
			}
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(traget), "UTF-8"));
			template.process(data, out);
			out.flush();
			out.close();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 根据模板数据生成文件
	 * @param templatePath ftl模板路径
	 * @param traget 生成文件路径
	 * @param data   数据Map<String,Object>集合
	 * @param templateFileName 模板文件名称
	 */
	public  static void create(ServletContext context,File templatePath,File traget,Map<String,Object> data,String templateFileName)
	{
		try {
			//读classPath路径下的
	        Template template = getConfiguration(context).getTemplate(templateFileName);
			File htmlDirectory = traget.getParentFile();
			if (!htmlDirectory.exists()) {
				htmlDirectory.mkdirs();
			}
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(traget), "UTF-8"));
			template.process(data, out);
			out.flush();
			out.close();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  static String package2path(String packageName) {
		return packageName.replace('.', '/');
	}
	
	public  static String capFirst(String string) {
		String s = String.valueOf(string.charAt(0)).toUpperCase();
		s = s + string.substring(1);
		return s;
	}

	public  static String uncapFirst(String string) {
		String s = String.valueOf(string.charAt(0)).toLowerCase();
		s = s + string.substring(1);
		return s;
	}
}
