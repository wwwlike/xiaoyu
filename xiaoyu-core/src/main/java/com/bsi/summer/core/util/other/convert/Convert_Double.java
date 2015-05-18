package com.bsi.summer.core.util.other.convert;

import java.text.DecimalFormat;

/**
 * 
 * @author wwwlike
 * @CreateDate 2007-11-9
 */
public class Convert_Double implements Convert {

    public Class getConvertClass() {
        return Double.class;
    }

    public String objToString(Object obj) {
        String value = null;
        try {
            double d = ((Double) obj).doubleValue();
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
        Double obj = null;
        try {
            obj = new Double(value);
        }catch(Exception e){
            //ignore
        }
        return obj;
    }

    private static double round(double num) {
	    return Math.round(num*1000) /1000.0;
	}
}
