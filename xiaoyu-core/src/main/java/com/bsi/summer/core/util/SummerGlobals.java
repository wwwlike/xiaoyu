package com.bsi.summer.core.util;

import java.io.IOException;
import java.util.Properties;


/**
 * @author dlc
 * TODO 
 */
public class SummerGlobals {
    
    private static SummerGlobals _const;
    private static Properties props;
    public static String configFileName = "summer.properties";
    
    static {
        if(_const == null)
            _const = new SummerGlobals();
    }
    
    private SummerGlobals() {
        loadProperties();
    }
    
    public static String getProperty(String key) {
        return props.getProperty(key);
    }
    
    public static void setProperty(String key,String value) {
        props.setProperty(key,value);
    }
    
    public static void loadProperties() {
        try {
			props = PropertiesUtils.loadProperties(configFileName);
		} catch (IOException e) {
			Logger.getInstance().debug("msg");
		}
    }
    
    public static void saveProperties() {
        PropertiesUtils.save(props,configFileName);
    }
    
    public static void main(String[] args) {
        SummerGlobals.setProperty("com.summer.debug","true");
        SummerGlobals.setProperty("com.summer.log.path","/log");
        SummerGlobals.setProperty("com.summer.administrator.user","admin");
        SummerGlobals.setProperty("com.summer.administrator.pwd","");
        SummerGlobals.setProperty("com.summer.upload.allowFileList","gif,jpg,bmp,png,jpeg,bak,xls,sql,txt,rar,zip,doc,pdf,ppt,vsd,jar,rtf");
        SummerGlobals.setProperty("com.summer.upload.maxSize","300");
        SummerGlobals.saveProperties();

    }
}
