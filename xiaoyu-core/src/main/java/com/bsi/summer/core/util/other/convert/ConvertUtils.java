package com.bsi.summer.core.util.other.convert;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author wwwlike
 * @CreateDate 2007-11-9
 */
public class ConvertUtils {

    private static Map map = new HashMap();
    
    static {
        map.put(String.class, new Convert_String());
        map.put(String[].class, new Convert_Array());
        
        map.put(Boolean.class, new Convert_Boolean());
        map.put(boolean.class, new Convert_Boolean());
        
        map.put(java.util.Date.class, new Convert_Date());
        
        map.put(double.class, new Convert_Double());
        map.put(Double.class, new Convert_Double());
        
        map.put(float.class, new Convert_Float());
        map.put(Float.class, new Convert_Float());
        
        map.put(int.class, new Convert_Integer());
        map.put(Integer.class, new Convert_Integer());
        
        map.put(long.class, new Convert_Long());
        map.put(Long.class, new Convert_Long());
        
        map.put(java.sql.Date.class, new Convert_SqlDate());
        map.put(java.sql.Timestamp.class, new Convert_SqlTimestamp());
        map.put(java.sql.Time.class, new Convert_SqlTime());
    }
    
    public static void register(Class clazz,Convert convert) {
        map.put(clazz,convert);
    }
    
    public static String objToString(Object obj) {
        if(obj == null)
            return null;
        String value = null;
        Class clazz = obj.getClass();
        Convert convert = (Convert) map.get(clazz);
        if(convert != null)
            value = convert.objToString(obj);
        else
            value = obj.toString();
        return value;
    }
    
    public static Object stringToObj(Class clazz,String value) {
        if(value == null)
            return null;
        Object obj = null;
        Convert convert = (Convert) map.get(clazz);
        if(convert != null)
            obj = convert.stringToObj(value);
        else
            throw new NullPointerException("Can't find convert for "+clazz.getName());
        return obj;
    }
}
