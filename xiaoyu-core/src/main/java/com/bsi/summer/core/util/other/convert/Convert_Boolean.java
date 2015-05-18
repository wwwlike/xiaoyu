package com.bsi.summer.core.util.other.convert;

/**
 * 
 * @author wwwlike
 * @CreateDate 2007-11-9
 */
public class Convert_Boolean implements Convert {

    public Class getConvertClass() {
        return Boolean.class;
    }

    public String objToString(Object obj) {
        String value = null;
        if(parseBoolean((Boolean) obj))
            value = "true";
        else
            value = "false";
        return value;
    }

    public Object stringToObj(String value) {
        Boolean obj = null;
        if("Y".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value)
                || "1".equalsIgnoreCase(value))
	        obj = new Boolean(true);
	    else
	        obj = new Boolean(false);
        return obj;
    }

    private static boolean parseBoolean(Boolean bl) {
		boolean b = false;
		if(bl!=null && bl.toString().equalsIgnoreCase("true"))
			b = true;
		return b;
	}
}
