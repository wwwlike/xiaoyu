package com.bsi.summer.core.util.other.convert;

/**
 * 
 * @author wwwlike
 * @CreateDate 2007-11-9
 */
public class Convert_Long implements Convert {

    public Class getConvertClass() {
        return Long.class;
    }

    public String objToString(Object obj) {
        String value = null;
        try {
            value = ((Long) obj).toString();
        }catch(Exception e){
            //ignore
        }
        return value;
    }

    public Object stringToObj(String value) {
        Long obj = null;
        try {
            obj = new Long(value);
        }catch(Exception e){
            //ignore
        }
        return obj;
    }

}
