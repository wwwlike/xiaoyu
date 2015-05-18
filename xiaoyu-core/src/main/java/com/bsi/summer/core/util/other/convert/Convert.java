package com.bsi.summer.core.util.other.convert;

/**
 * 
 * @author wwwlike
 * @CreateDate 2007-11-9
 */
public interface Convert {

    public Class getConvertClass();
    
    public String objToString(Object obj);
    
    public Object stringToObj(String value);
}
