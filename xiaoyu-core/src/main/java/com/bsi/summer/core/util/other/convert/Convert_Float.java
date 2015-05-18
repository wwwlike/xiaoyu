package com.bsi.summer.core.util.other.convert;

import java.text.DecimalFormat;

/**
 * 
 * @author wwwlike
 * @CreateDate 2007-11-9
 */
public class Convert_Float implements Convert {

    public Class getConvertClass() {
        return Float.class;
    }

    public String objToString(Object obj) {
        String value = null;
        try {
            double d = ((Float) obj).doubleValue();
            d = round(d);
            DecimalFormat f = null;
            if(d%1==0)
                f = new DecimalFormat("0");
            else
                f = new DecimalFormat("0.000");
            value = f.format(d);
        }catch(Exception e){
            //ignore
        }
        return value;
    }

    public Object stringToObj(String value) {
        Float obj = null;
        try {
            obj = new Float(value);
        }catch(Exception e){
            //ignore
        }
        return obj;
    }

    private static double round(double num) {
	    return Math.round(num*1000) /1000.0;
	}
}
