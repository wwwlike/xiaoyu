package com.bsi.summer.core.util;

import java.net.URLDecoder;

/**
 * 如果操作系统为Linux,path最前面'/'必须保留
 * @author wwwlike
 * TODO 
 */
public class SysPath {
    
    /**
     * web-inf path
     * @return
     */
    public static String getPath() {
		String temp = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		try {
		    temp = URLDecoder.decode(temp,System.getProperty("file.encoding"));
			temp = temp.substring(0,temp.indexOf("classes")-1);
		}catch(Exception e){
		    e.printStackTrace();
		}
		return temp;
	}
    
    /**
     * absolute path in /WEB-INF/classes/
     * @param file
     * @return
     */
    public static String getRealPath(String file) {
        String temp = Thread.currentThread().getContextClassLoader().getResource(file).getPath();
        try {
		    temp = URLDecoder.decode(temp,System.getProperty("file.encoding"));
	        temp = temp.substring(0,temp.length());
		}catch(Exception e){
		    e.printStackTrace();
		}
		return temp;
    }
    
    /**
     * app home
     * @return
     */
    public static String getHome() {
        String temp = Thread.currentThread().getContextClassLoader().getResource("roly").getPath();
		try {
		    temp = URLDecoder.decode(temp,System.getProperty("file.encoding"));
		    temp = temp.substring(0,temp.indexOf("/WEB-INF"));
		}catch(Exception e){
		    e.printStackTrace();
		}
		return temp;
    }
    
    public static boolean isWindows() {
        boolean isWin = true;
        String separator = System.getProperty("file.separator");
        if("/".equals(separator))
            isWin = false;
        return isWin;
    }
    
    public static void main(String[] args) {
        System.out.println(getRealPath("com/ascrm/sys/formdesign/xml/formdesign.xml"));
        System.out.println(getPath());
    }
}
