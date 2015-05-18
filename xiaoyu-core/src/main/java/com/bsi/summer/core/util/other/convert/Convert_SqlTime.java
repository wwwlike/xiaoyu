package com.bsi.summer.core.util.other.convert;

import java.sql.Time;

/**
 * 
 * @author wwwlike
 * @CreateDate 2007-11-9
 */
public class Convert_SqlTime implements Convert {

    public Class getConvertClass() {
        return Time.class;
    }

    public String objToString(Object obj) {
        String value = null;
        try {
            Time date = (Time) obj;
            java.text.DateFormat df = new java.text.SimpleDateFormat("HH:mm:ss");
			value = df.format(date) ;
        }catch(Exception e){
            //ignore
        }
        return value;
    }

    public Object stringToObj(String value) {
        Time date = null;
        try {
            java.text.DateFormat df = new java.text.SimpleDateFormat("HH:mm:ss");
            date = new Time(df.parse(value).getTime());
        }catch(Exception e){
            //ingore
        }
        return date;
    }

}
