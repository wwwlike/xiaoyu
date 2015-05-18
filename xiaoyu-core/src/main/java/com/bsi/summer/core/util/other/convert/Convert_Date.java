package com.bsi.summer.core.util.other.convert;

import java.util.Date;

/**
 * 
 * @author wwwlike
 * @CreateDate 2007-11-9
 */
public class Convert_Date implements Convert {

    public Class getConvertClass() {
        return Date.class;
    }

    public String objToString(Object obj) {
        String value = null;
        try {
            Date date = (Date) obj;
            java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			value = df.format(date) ;
        }catch(Exception e){
            //ignore
        }
        return value;
    }
    
    public String objToString(Object obj,String format) {
        String value = null;
        try {
            Date date = (Date) obj;
            java.text.DateFormat df = new java.text.SimpleDateFormat(format);
			value = df.format(date) ;
        }catch(Exception e){
            //ignore
        }
        return value;
    }

    public Object stringToObj(String value) {
        Date obj = null;
        try {
        	if(value.length() == 10) {
        		java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		        obj = df.parse(value);
        	} else {
        		java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        		obj = df.parse(value);
        	}
        }catch(Exception e){
            //ingore
        }
        return obj;
    }

    public Object stringToObj(String value,String format) {
        Date date = null;
        try {
            java.text.DateFormat df = new java.text.SimpleDateFormat(format);
            date = df.parse(value);
        }catch(Exception e){
            //ingore
        }
        return date;
    }
}
