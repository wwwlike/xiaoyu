package com.bsi.summer.core.util.other.convert;

/**
 * 
 * @author wwwlike
 * @CreateDate 2007-11-9
 */
public class Convert_String implements Convert {

    public Class getConvertClass() {
        return String.class;
    }

    public String objToString(Object obj) {
        return (String) obj;
    }

    public Object stringToObj(String value) {
        return value;
    }

}
