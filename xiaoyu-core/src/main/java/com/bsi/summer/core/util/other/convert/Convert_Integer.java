package com.bsi.summer.core.util.other.convert;

/**
 * 
 * @author wwwlike
 * @CreateDate 2007-11-9
 */
public class Convert_Integer implements Convert {

    public Class getConvertClass() {
        return Integer.class;
    }

    public String objToString(Object obj) {
        String value = null;
        try {
            value = ((Integer) obj).toString();
        }catch(Exception e){
            //ignore
        }
        return value;
    }

    public Object stringToObj(String value) {
        Integer obj = null;
        try {
            obj = new Integer(value);
        }catch(Exception e){
            //ignore
        }
        return obj;
    }

}
