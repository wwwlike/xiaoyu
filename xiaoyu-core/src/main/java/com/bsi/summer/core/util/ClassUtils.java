package com.bsi.summer.core.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import com.bsi.summer.core.item.Item;

/**
 * 各种Class处理工具类.
 */
public class ClassUtils {
	/**
	 * 获取接口的所有实现类
	 * @param interfaceClass 接口类
	 * @param samePackage    是否为同一包路径下
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public static List<Class<?>> getAllClassesByInterface(Class<?> interfaceClass, boolean samePackage) 
		throws IOException, ClassNotFoundException, IllegalStateException {
		
//		if (!interfaceClass.isInterface()) {
//			throw new IllegalStateException("Class not a interface.");
//		}
		ClassLoader $loader = interfaceClass.getClassLoader();
		/** 获取包名称 */
		String packageName = samePackage ? interfaceClass.getPackage().getName() : "/";
		return findClasses(interfaceClass, $loader, packageName);
	}
	
	
	
	
	
	/**
	 * 获取实现接口的实现类文件
	 * @param interfaceClass
	 * @param loader
	 * @param packageName
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private static List<Class<?>> findClasses(Class<?> interfaceClass, ClassLoader loader, String packageName) 
		throws IOException, ClassNotFoundException {
		
		List<Class<?>> allClasses = new ArrayList<Class<?>>();
		/** 获取包路径 */
		String packagePath = packageName.replace(".", "/");
		if (!packagePath.equals("/")) {
			Enumeration<URL> resources = loader.getResources(packagePath);
			while (resources.hasMoreElements()) {
				URL $url = resources.nextElement();
				allClasses.addAll(findResources(interfaceClass, new File($url.getFile()), packageName));	
			}	
		} else {
			String path = loader.getResource("").getPath();
			allClasses.addAll(findResources(interfaceClass, new File(path), packageName));
		}
		return allClasses;
	}
	
	/**
	 * 获取文件资源信息
	 * @param interfaceClass
	 * @param directory
	 * @param packageName
	 * @return
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	private static List<Class<?>> findResources(Class<?> interfaceClass, File directory, String packageName) 
		throws ClassNotFoundException {
		
		List<Class<?>> $results = new ArrayList<Class<?>>();
		if (!directory.exists()) return Collections.EMPTY_LIST;
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				// 文件夹-->继续遍历
				if (!file.getName().contains(".")) {
					if (!packageName.equals("/")) {
						$results.addAll(findResources(interfaceClass, file, packageName + "." + file.getName()));
					} else {
						$results.addAll(findResources(interfaceClass, file, file.getName()));
					}						
				}				
			} else if (file.getName().endsWith(".class")){
				Class clazz = null;
				if (!packageName.equals("/")) {
					clazz = Class.forName(packageName + "." + file.getName().substring(0, file.getName().length() - 6));
				} else {
					clazz = Class.forName(file.getName().substring(0, file.getName().length() - 6));
				}				
				if (interfaceClass.isAssignableFrom(clazz) && !interfaceClass.equals(clazz)) {
					$results.add(clazz);
				}				
			}
		}
		return $results;
	}
	
	public static void main(String[] args) throws IllegalStateException, IOException, ClassNotFoundException {
		List<Class<?>> list=ClassUtils.getAllClassesByInterface(Item.class,false);
		for(Class clazz:list)
		{
			System.out.println(clazz.getName());
		}
	}

}
